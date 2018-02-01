/*
  Copyright 2014-2016 Sylvain Hallé
  Laboratoire d'informatique formelle
  Université du Québec à Chicoutimi, Canada

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package ca.uqac.lif.util;

/**
 * Empty exception object.
 * @author Sylvain Hallé
 */
public class EmptyException extends Exception
{
	/**
	 * The message carried by this exception
	 */
	protected final String m_message;

	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = -8755602580215137730L;

	/**
	 * Creates a new empty exception
	 * @param message The message to associate this exception with
	 */
	public EmptyException(String message)
	{
		super();
		m_message = message;
	}
	
	/**
	 * Creates a new empty exception
	 * @param t A throwable object that is the cause of this exception
	 */
	public EmptyException(Throwable t)
	{
		super(t);
		m_message = "";
	}

	@Override
	public String toString()
	{
		return m_message;
	}
}
