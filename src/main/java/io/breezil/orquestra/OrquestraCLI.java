package io.breezil.orquestra;

public class OrquestraCLI {

	public static CliOptions parseArguments(String args[]) {
		CliOptions option = new CliOptions();
		for (int i = 0; i < args.length; i = i + 2) {
			switch (args[i]) {
			case "--gf":
				option.setGrammarFile(args[i + 1]);
			case "--sf":
				option.setScriptFile(args[i + 1]);
			case "--df":
				option.setObjectDefinitionFile(args[i + 1]);
			}
		}

		return option;
	}

}
