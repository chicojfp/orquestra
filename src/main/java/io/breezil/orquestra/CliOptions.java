package io.breezil.orquestra;

import java.util.Objects;

public class CliOptions {
	private String grammarFile;
	private String scriptFile;
	private String objectDefinitionFile;

	public boolean hasValidArgs() {
		return !Objects.isNull(this.grammarFile) && !Objects.isNull(this.scriptFile);
	}

	public String getGrammarFile() {
		return grammarFile;
	}

	public void setGrammarFile(String grammarFile) {
		this.grammarFile = grammarFile;
	}

	public String getScriptFile() {
		return scriptFile;
	}

	public void setScriptFile(String scriptFile) {
		this.scriptFile = scriptFile;
	}

	public String getObjectDefinitionFile() {
		return objectDefinitionFile;
	}

	public void setObjectDefinitionFile(String objectDefinitionFile) {
		this.objectDefinitionFile = objectDefinitionFile;
	}

}
