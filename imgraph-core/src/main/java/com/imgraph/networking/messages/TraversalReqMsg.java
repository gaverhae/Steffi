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
package com.imgraph.networking.messages;

import java.util.List;

import com.imgraph.traversal.EdgeTraversalConf;
import com.imgraph.traversal.MatchEvaluatorConf;


public class TraversalReqMsg extends Message{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2468639634115685631L;
	
	private long vertexId;
	//private ImgGraph graph;
	private int maxHops;
	private MatchEvaluatorConf matchConf;
	private List<EdgeTraversalConf> traversalConfs;
	private String nodeIp;
	private int managerIndex;
	
	
	public TraversalReqMsg() {
		super(MessageType.TRAVERSAL_REQ);
	}
	
	public long getVertexId() {
		return vertexId;
	}
	public void setVertexId(long vertexId) {
		this.vertexId = vertexId;
	}
	public int getMaxHops() {
		return maxHops;
	}
	public void setMaxHops(int maxHops) {
		this.maxHops = maxHops;
	}
	public MatchEvaluatorConf getMatchConf() {
		return matchConf;
	}
	public void setMatchConf(MatchEvaluatorConf matchConf) {
		this.matchConf = matchConf;
	}

	public List<EdgeTraversalConf> getTraversalConfs() {
		return traversalConfs;
	}

	public void setTraversalConfs(List<EdgeTraversalConf> traversalConfs) {
		this.traversalConfs = traversalConfs;
	}
	
	@Override
	public String toString() {
		return super.toString() + "VID:" + vertexId;
	}

	public String getNodeIp() {
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}


	public int getManagerIndex() {
		return managerIndex;
	}

	public void setManagerIndex(int managerIndex) {
		this.managerIndex = managerIndex;
	}
}
