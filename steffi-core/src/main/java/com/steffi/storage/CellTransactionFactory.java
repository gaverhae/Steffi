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
package com.steffi.storage;

/**
 * @author Aldemar Reynaga
 * Starts a new transaction using a thread-local storage mechanism
 */
public class CellTransactionFactory {
	
	public static CellTransaction beginTransaction() {
		CellTransaction cellTransaction = new CellTransaction();
		
		CellTransactionThread.set(cellTransaction);
		
		return cellTransaction;
	}

	
}
