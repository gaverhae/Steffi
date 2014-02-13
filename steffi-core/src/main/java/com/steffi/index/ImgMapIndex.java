/*******************************************************************************
 * Copyright (c) 2014 EURA NOVA.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Aldemar Reynaga - initial API and implementation
 *     Salim Jouili - initial API and implementation
 ******************************************************************************/
package com.steffi.index;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.IteratorUtils;
import org.infinispan.Cache;
import org.infinispan.lifecycle.ComponentStatus;

import com.steffi.model.Cell;
import com.steffi.model.SteffiEdge;
import com.steffi.model.SteffiVertex;
import com.steffi.storage.CacheContainer;
import com.steffi.storage.CellTransactionThread;
import com.steffi.storage.IndexOperation;
import com.steffi.storage.IndexOperation.IndexOperationItem;

/**
 * @author Aldemar Reynaga
 * Implementation of the ImgIndex using an Infinispan Cache as a storage, the entries of the index are
 * stored as key/values where the key is an IndexKeyValue instance and the value is a set of Cell ids
 * or EdgeIndexEntry instances  
 * @param <T> The class of the Imgraph cells to be indexed
 */
public class ImgMapIndex<T extends Cell> implements ImgIndex<T> {

	/**
	 * 
	 */
	//private static final long serialVersionUID = -7157672464559662812L;
	private final String name;
	private final Class<T> indexClass;
	
	private Cache<IndexKeyValue, ConcurrentHashMap<Object, Boolean>> map;
	
	
	
	public ImgMapIndex(String name, Class<T> indexClass, boolean createCache) {
		this.name = name;
		this.indexClass = indexClass;
		
		if (createCache)
			this.map = CacheContainer.createIndexCache(name, indexClass);
	}
	
	private Cache<IndexKeyValue, ConcurrentHashMap<Object, Boolean>> getMap() {
		if (map == null) {
			map = CacheContainer.getCache(name);
			if (!map.getStatus().equals(ComponentStatus.RUNNING))
				map.start();
		}
		
		return map;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Class<T> getEntityType() {
		return indexClass;
	}

	@Override
	public ImgIndexHits<T> get(String key, Object value) {
		IndexKeyValue indexKeyValue = new IndexKeyValue(key, value);
		ConcurrentHashMap<Object, Boolean> idElements =  getMap().get(indexKeyValue);
		
		if (idElements == null || idElements.isEmpty()) 
			return new ImgMapIndexHits<T>(IteratorUtils.emptyIterator(), 0, indexClass);
		else 			
			return new ImgMapIndexHits<T>(idElements.keySet().iterator(), idElements.size(), indexClass);
		
	}

	public void commitChanges(IndexOperation<T> operations) {
		
		if (operations.getNewKeyValues() != null) {
			for (IndexOperationItem opItem : operations.getNewKeyValues()) {
				IndexKeyValue indexKeyValue = new IndexKeyValue(opItem.getKey(), opItem.getValue());
				ConcurrentHashMap<Object, Boolean> curElements = getMap().get(indexKeyValue); 
				
				if (curElements == null) 
					curElements = new ConcurrentHashMap<Object, Boolean>();
				
				if (!opItem.getObject().getClass().equals(this.indexClass) && 
						!opItem.getObject().getClass().getSuperclass().equals(this.indexClass))
					throw new RuntimeException("Index was created for " + indexClass.getSimpleName());
				
				Cell cellElement = indexClass.cast(opItem.getObject());
				
				if (cellElement instanceof SteffiEdge) {
					SteffiEdge edge = (SteffiEdge)cellElement;
					
					curElements.put(new EdgeIndexEntry(edge.getSourceCellId(), edge.getId()), true);
				} else {
					curElements.put(cellElement.getId(), true);	
				}
				
				getMap().put(indexKeyValue, curElements);
			}
		}
		
		if (operations.getRemovedKeyValues() != null) {
			for (IndexOperationItem opItem : operations.getRemovedKeyValues()) {
				ConcurrentHashMap<Object, Boolean> curValues =  getMap().get(new IndexKeyValue(opItem.getKey(), opItem.getValue()));
				
				if (curValues != null) {
					
					Cell cellElement = indexClass.cast(opItem.getObject());
					
					if (cellElement instanceof SteffiVertex) {
						curValues.remove(cellElement.getId());
					} else {
						SteffiEdge edge = (SteffiEdge)cellElement;
						curValues.remove(new EdgeIndexEntry(edge.getSourceCellId(), edge.getId()));
					}
					
				}
			}
		}
		
	}
	
	
	@Override
	public void put(String key, Object value, T element) {
		
		CellTransactionThread.get().putKeyValueIndex(name, key, value, element);
		
	}

	@Override
	public void remove(T element, String key, Object value) {
		CellTransactionThread.get().removeKeyValueIndex(name, key, value, element);
		
	}

	@Override
	public long count(String key, Object value) {
		ConcurrentHashMap<Object, Boolean> curValues =  getMap().get(new IndexKeyValue(key, value));
		if (curValues != null)
			return curValues.size();
		return 0;
	}

	@Override
	public boolean hasElementForKeyValue(T element, String key, Object value) {
		ConcurrentHashMap<Object, Boolean> curValues =  getMap().get(new IndexKeyValue(key, value));
		if (curValues != null) {
			Cell cellElement = indexClass.cast(element);
			
			if (cellElement instanceof SteffiVertex) {
				return curValues.contains(cellElement.getId());
			} else {
				SteffiEdge edge = (SteffiEdge)cellElement;
				return curValues.contains(new EdgeIndexEntry(edge.getSourceCellId(), edge.getId()));
			}
		}
		
		return false;
	}
	
}
