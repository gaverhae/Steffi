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
package com.imgraph.traversal;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Aldemar Reynaga
 * Types of actions after evaluating a vertex
 */
public enum Evaluation {
	INCLUDE_AND_CONTINUE(1),
	INCLUDE_AND_STOP(2),
	EXCLUDE_AND_CONTINUE(3),
	EXCLUDE_AND_STOP(4);
	
	private static final Map<Byte, Evaluation> codeMap;
	static {
		codeMap = new HashMap<Byte, Evaluation>();
		codeMap.put((byte) 1, INCLUDE_AND_CONTINUE);
		codeMap.put((byte) 2, INCLUDE_AND_STOP);
		codeMap.put((byte) 3, EXCLUDE_AND_CONTINUE);
		codeMap.put((byte) 4, EXCLUDE_AND_STOP);
	}
	
	private byte code;
	
	public byte getCode() {
		return code;
	}
	
	Evaluation(int code) {
		this.code = (byte) code;
	}
	
	public static Evaluation getValue(byte code) {
		return codeMap.get(code);
	}
	
}
