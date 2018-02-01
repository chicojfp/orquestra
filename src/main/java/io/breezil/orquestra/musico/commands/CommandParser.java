package io.breezil.orquestra.musico.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.bullwinkle.BnfParser.InvalidGrammarException;
import ca.uqac.lif.bullwinkle.BnfParser.ParseException;
import ca.uqac.lif.bullwinkle.ParseNode;

public class CommandParser {
	private BnfParser parser;

	public CommandParser(String grammarFile) {
		try {
			parser = new BnfParser(new FileInputStream(grammarFile));
		} catch (InvalidGrammarException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<Command> parseCommands(List<String> commands) {
		List<Command> command = new ArrayList<>();
		
		commands.forEach(c -> command.add(this.parseCommand(c)));
		
		return command;
	}
	
	private Command parseCommand(String commandScript) {
		Command command = null;
		List<String> cmds = Arrays.asList(commandScript.split(","));
		Collections.reverse(cmds);
		for (String cmd : cmds) {
			Command innerCmd = buildCommand(cmd.trim());
			if (command != null) {
				((FilterCommand) innerCmd).setActualCommand(command);
			} else {
				command = innerCmd;
			}
		}
		
		
//		if (commandScript.startsWith("Acesse")) {
//			
//			command = new GoToURLCommand(commandScript.replace("Acesse ", ""));
//			
//		} else if (commandScript.startsWith("Clique")) {
//			String script = commandScript.substring(10);
//			String itemName = script.substring(0, script.indexOf("\"") - 1);
//			
//			WebElementInfo info = findWebElement(itemName);
//			String name = commandScript.substring(commandScript.indexOf("\"") + 1, commandScript.lastIndexOf("\""));
//			command = new ClickCommand(info, name);
//			
//		} else if (commandScript.startsWith("Preencha")) {
//			
//			String padraoValor = " com valor ";
//			WebElementInfo info = findWebElement("campo");
//			String name = commandScript.substring(commandScript.indexOf("\""), commandScript.lastIndexOf(padraoValor)).replace("\"", "");
//			String value = commandScript.substring(commandScript.lastIndexOf(padraoValor) + padraoValor.length()).replace("\"", "");
//			command = new FillInputCommand(info, name, value);
//			
//		} else if (commandScript.startsWith("Na")) {
//			int comaIndex = commandScript.indexOf(",");
//			comaIndex = comaIndex > 0 ? comaIndex : commandScript.length();
//			command = parseFilterComand(commandScript.substring(0, comaIndex));
//			Iterator<String> nextCommands = Arrays.asList(commandScript.split(",")).iterator();
//			nextCommands.next();
//			
//			Command currentCommand = command;
//			while(nextCommands.hasNext()) {
//				String commandName = nextCommands.next().trim();
//				Command nextCommand = this.parseCommand(commandName);
//				if (currentCommand instanceof FilterCommand) {
//					((FilterCommand) currentCommand).setActualCommand(nextCommand);
//				}
//				currentCommand = nextCommand;
//			}
//			
//		} else if (commandScript.startsWith("Ir ao popup")) {
//			
//			command = new SwitchWindowCommand(null);
//			
//		} else if (commandScript.startsWith("Selecione")) {
//			
//			WebElementInfo info = findWebElement("combo");
//			String optionValue = this.findQuotedParameter(commandScript, 1);
//			String name = this.findQuotedParameter(commandScript, 2);
//			command = new SelectCommand(info, name, optionValue);
//			
//		}
		
		return command;
	}


	private Command buildCommand(String commandScript) {
		Command command = null;
		try {
			ParseNode node = this.parser.parse(commandScript);
			if (node != null) {
				node = node.getChildren().get(0);
				command = new CommandBuilder().build(node.getToken().substring(1, node.getToken().length() -1)).fillParameters(node);
			}
			if (node == null) {
				System.out.println("==================================================" + commandScript);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return command;
	}
	
	private String findQuotedParameter(String scriptlet, int order) {
		String quotedParam = "";
		int startIndex = scriptlet.indexOf("\"") + 1;
		for (int i = 1; i < order; i++) {
			startIndex = scriptlet.indexOf("\"", startIndex) + 1;
			startIndex = scriptlet.indexOf("\"", startIndex) + 1;
		}
		if (startIndex > 0) {
			int endIndex = scriptlet.indexOf("\"", startIndex);
			quotedParam = scriptlet.substring(startIndex, endIndex);
		}
		
		return quotedParam;
	}


	private FilterCommand parseFilterComand(String commandScript) {
		String script = commandScript.substring(3);
		int indexSpace = script.indexOf(" ");
		indexSpace = indexSpace > 0 ? indexSpace : script.length();
		String elType = script.substring(0, indexSpace).trim();
		String name = this.findQuotedParameter(commandScript, 1);
//		WebElementInfo info = findWebElement(elType);
		return new FilterCommand();
	}

}
