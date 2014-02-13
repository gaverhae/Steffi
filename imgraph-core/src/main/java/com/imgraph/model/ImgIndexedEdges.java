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
package com.imgraph.model;

import java.util.List;



/**
 * @author Aldemar Reynaga
 * Defines the required functionalities of the model of containing a group of indexed edges that will be stored inside a vertex 
 */
public interface ImgIndexedEdges {

	public abstract void addEdge(ImgEdge edge);

	public abstract List<ImgEdge> getEdgesByTypeName(EdgeType edgeType,
			String name);

	public abstract List<ImgEdge> getEdgesByType(EdgeType edgeType);

	public abstract List<ImgEdge> getEdgesByName(String name);

	public abstract List<ImgEdge> getAllEdges();

	public abstract void clear();

	public abstract void remove(ImgEdge edge);

	public abstract boolean isEmpty();

	public abstract boolean hasMoreThanOneEdge();

	public abstract void trimToSize();

	
	

}
