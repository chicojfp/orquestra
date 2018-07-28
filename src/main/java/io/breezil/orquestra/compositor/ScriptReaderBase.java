package io.breezil.orquestra.compositor;

import java.util.Objects;

public abstract class ScriptReaderBase {

	public abstract Script readScript(String scriptID);

	protected boolean mustParseScriptLin(String script) {
		String scriptLine = Objects.toString(script, "").trim();
		return !scriptLine.startsWith("#"); // && (scriptLine.length() > 0);
	}

}
