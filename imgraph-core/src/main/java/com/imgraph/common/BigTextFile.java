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
package com.imgraph.common;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

/**
 * @author Aldemar Reynaga
 * This class is used in the batch loading from a text file, the contents of file are loaded on demand to not 
 * overflow the computer memory 
 */
public class BigTextFile implements Iterable<String>{

	private BufferedReader _reader;

	public BigTextFile(String filePath) throws Exception
	{
		_reader = new BufferedReader(new FileReader(filePath));
	}

	public void Close()
	{
		try
		{
			_reader.close();
		}
		catch (Exception ex) {}
	}

	@Override
	public Iterator<String> iterator() {
		return new FileIterator();
	}

	private class FileIterator implements Iterator<String>
	{
		private String _currentLine;

		public boolean hasNext()
		{
			try
			{
				_currentLine = _reader.readLine();
			}
			catch (Exception ex)
			{
				_currentLine = null;
				ex.printStackTrace();
			}

			return _currentLine != null;
		}

		public String next()
		{
			return _currentLine;
		}

		public void remove()
		{
		}
	}

}
