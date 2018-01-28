package io.breezil.orquestra.musico.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CommandParser {
	private List<WebElementInfo> webElementInfos;

	public CommandParser(List<WebElementInfo> webElementInfos) {
		this.webElementInfos = webElementInfos;
	}
	
	
	public List<Command> parseCommands(List<String> commands) {
		List<Command> command = new ArrayList<>();
		
		commands.forEach(c -> command.add(this.parseCommand(c)));
		
		return command;
	}
	
	private WebElementInfo findWebElement(String name) {
		for (WebElementInfo webElementInfo : webElementInfos) {
			if (webElementInfo.getName().equals(name)) {
				return webElementInfo;
			}
		}
		return null;
	}


	private Command parseCommand(String commandScript) {
		Command command = new Command("");
		if (commandScript.startsWith("Acesse")) {
			
			command = new GoToURLCommand(commandScript.replace("Acesse ", ""));
			
		} else if (commandScript.startsWith("Clique")) {
			String script = commandScript.substring(10);
			String itemName = script.substring(0, script.indexOf("\"") - 1);
			
			WebElementInfo info = findWebElement(itemName);
			String name = commandScript.substring(commandScript.indexOf("\"") + 1, commandScript.lastIndexOf("\""));
			command = new ClickCommand(info, name);
			
		} else if (commandScript.startsWith("Preencha")) {
			
			String padraoValor = " com valor ";
			WebElementInfo info = findWebElement("campo");
			String name = commandScript.substring(commandScript.indexOf("\""), commandScript.lastIndexOf(padraoValor)).replace("\"", "");
			String value = commandScript.substring(commandScript.lastIndexOf(padraoValor) + padraoValor.length()).replace("\"", "");
			command = new FillInputCommand(info, name, value);
			
		} else if (commandScript.startsWith("Na")) {
			int comaIndex = commandScript.indexOf(",");
			comaIndex = comaIndex > 0 ? comaIndex : commandScript.length();
			command = parseFilterComand(commandScript.substring(0, comaIndex));
			Iterator<String> nextCommands = Arrays.asList(commandScript.split(",")).iterator();
			nextCommands.next();
			
			Command currentCommand = command;
			while(nextCommands.hasNext()) {
				String commandName = nextCommands.next().trim();
				Command nextCommand = this.parseCommand(commandName);
				if (currentCommand instanceof FilterCommand) {
					((FilterCommand) currentCommand).setActualCommand(nextCommand);
				}
				currentCommand = nextCommand;
			}
			
		} else if (commandScript.startsWith("Ir ao popup")) {
			
			command = new SwitchWindowCommand(null);
			
		} else if (commandScript.startsWith("Selecione")) {
			
			WebElementInfo info = findWebElement("combo");
			String optionValue = this.findQuotedParameter(commandScript, 1);
			String name = this.findQuotedParameter(commandScript, 2);
			command = new SelectCommand(info, name, optionValue);
			
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
		WebElementInfo info = findWebElement(elType);
		return new FilterCommand(name, info);
	}

}
