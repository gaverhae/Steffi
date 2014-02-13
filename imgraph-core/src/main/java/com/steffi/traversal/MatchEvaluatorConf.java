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
package com.steffi.traversal;

import java.io.Serializable;
import java.util.Map;


/**
 * @author Aldemar Reynaga
 * Defines the conditions of the searched vertices based on one of the following parameters: vertex id,
 * name of the vertex, vertex properties 
 */
public class MatchEvaluatorConf implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5168286286351116857L;
	private Long cellId;
	private String namePattern;
	private Map<String, String> attributePatterns;
	private Evaluation evaluation;
	
	public MatchEvaluatorConf() {
		
	}
	
	
	public MatchEvaluatorConf(Long cellId, String namePattern,
			Map<String, String> attributePatterns, Evaluation evaluation) {
		super();
		this.cellId = cellId;
		this.namePattern = namePattern;
		this.attributePatterns = attributePatterns;
		this.evaluation = evaluation;
	}
	public Evaluation getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	public Map<String, String> getAttributePatterns() {
		return attributePatterns;
	}
	public void setAttributePatterns(Map<String, String> attributePatterns) {
		this.attributePatterns = attributePatterns;
	}
	
	public String getNamePattern() {
		return namePattern;
	}
	public void setNamePattern(String namePattern) {
		this.namePattern = namePattern;
	}
	public Long getCellId() {
		return cellId;
	}
	public void setCellId(Long cellId) {
		this.cellId = cellId;
	}
	

	
	@Override
	public String toString() {
		String string = "";
		
		string += ("(cellId=" + cellId + ", namePattern=" + namePattern + ", evaluation=" + evaluation + "\n" +
					"attributePatterns=" + attributePatterns + ")");
		
		return string;
	}
}
