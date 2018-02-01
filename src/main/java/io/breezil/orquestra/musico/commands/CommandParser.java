package io.breezil.orquestra.musico.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
		List<String> cmds = new ArrayList<>();
		cmds.add(commandScript); //Arrays.asList(commandScript.split(","));
		Collections.reverse(cmds);
		for (String cmd : cmds) {
			Command innerCmd = buildCommand(cmd.trim());
			if (command != null) {
				((FilterCommand) innerCmd).setActualCommand(command);
			} else {
				command = innerCmd;
			}
		}
		
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
			e.printStackTrace();
		}
		return command;
	}

}
