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
package ca.uqac.lif.bullwinkle;

/**
 * Represents a non-terminal token in a grammar rule
 * @author Sylvain Hallé
 */
public class NumberTerminalToken extends TerminalToken
{
	/**
	 * Dummy UID
	 */
	private static final long serialVersionUID = -5149336827915614205L;

	/**
	 * Creates a new non terminal token
	 * @param label The token's label
	 */
	public NumberTerminalToken(String label)
	{
		super(label);
	}

	@Override
	public boolean matches(final Token tok)
	{
		if (tok == null)
		{
			return false;
		}
		String val = tok.getName();
		try
		{
			// Try to parse token into a number
			Float.parseFloat(val);
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		return true;
	}
}
