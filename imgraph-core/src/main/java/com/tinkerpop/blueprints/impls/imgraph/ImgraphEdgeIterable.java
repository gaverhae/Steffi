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
package com.tinkerpop.blueprints.impls.imgraph;

import java.util.Iterator;

import com.imgraph.index.ImgIndexHits;
import com.imgraph.model.ImgEdge;
import com.tinkerpop.blueprints.CloseableIterable;
import com.tinkerpop.blueprints.Edge;


/**
 * @author Aldemar Reynaga
 * Implementation of the Blueprints interface used for indexes results
 * @param <T> An implementation of the Edge interface
 */
public class ImgraphEdgeIterable<T extends Edge> implements CloseableIterable<ImgraphEdge> {

	private ImgIndexHits<ImgEdge> rawIndexHits; 
	
	public ImgraphEdgeIterable(ImgIndexHits<ImgEdge> rawIndexHits) {
		this.rawIndexHits = rawIndexHits;
	}
	
	@Override
	public Iterator<ImgraphEdge> iterator() {
		return new Iterator<ImgraphEdge>() {
			
			@Override
			public void remove() {
				rawIndexHits.remove();
			}
			
			@Override
			public ImgraphEdge next() {
				return new ImgraphEdge(rawIndexHits.next(), ImgraphGraph.getInstance());
			}
			
			@Override
			public boolean hasNext() {
				return rawIndexHits.hasNext();
			}
		};
	}

	@Override
	public void close() {
		rawIndexHits.close();
	}

}
