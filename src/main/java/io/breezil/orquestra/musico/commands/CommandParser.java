package io.breezil.orquestra.musico.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

import ca.uqac.lif.bullwinkle.BnfParser;
import ca.uqac.lif.bullwinkle.BnfParser.InvalidGrammarException;
import ca.uqac.lif.bullwinkle.BnfParser.ParseException;
import ca.uqac.lif.bullwinkle.ParseNode;
import io.breezil.orquestra.compositor.Script;
import io.breezil.orquestra.compositor.ScriptStep;

public class CommandParser {
	private BnfParser parser;

	public CommandParser(String grammarFile) {
		try {
			parser = new BnfParser(new FileInputStream(grammarFile));
		} catch (InvalidGrammarException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public Script parseCommands(Script info) {
		info.getSteps().forEach(c -> this.parseCommand(c));
		return info;
	}
	
	private Command parseCommand(ScriptStep step) {
		Command command = null;
		List<String> cmds = new ArrayList<>();
		cmds.add(step.getScript()); //Arrays.asList(commandScript.split(","));
		Collections.reverse(cmds);
		for (String cmd : cmds) {
			Command innerCmd = buildCommand(cmd.trim());
			if (command != null) {
				((FilterCommand) innerCmd).setActualCommand(command);
			} else {
				command = innerCmd;
			}
		}
		step.setCommand(command);
		
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
				throw new RuntimeErrorException(null, String.format("A linha '%s' não é válida para o script.", commandScript));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return command;
	}

}
