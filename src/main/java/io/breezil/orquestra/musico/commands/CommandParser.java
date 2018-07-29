package io.breezil.orquestra.musico.commands;

import java.io.BufferedInputStream;
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
import io.breezil.orquestra.compositor.ScriptReader;
import io.breezil.orquestra.compositor.ScriptStep;
import io.breezil.orquestra.instrumento.ExecutionContext;

public class CommandParser {
	private BnfParser parser;

	public CommandParser(String grammarFile) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(grammarFile));
			parser = new BnfParser(in);
		} catch (InvalidGrammarException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public CommandParser(BnfParser parser) {
		this.parser = parser;
	}

	public Script parseCommands(Script info) {
		info.getSteps().forEach(c -> this.parseCommand(c));
		return info;
	}

	public Command parseCommand(ScriptStep step) {
		// System.out.println(" " + step.getScript());
		Command command = null;
		List<String> cmds = new ArrayList<>();
		cmds.add(step.getScript()); // Arrays.asList(commandScript.split(","));
		Collections.reverse(cmds);
		for (String cmd : cmds) {
			Command innerCmd = buildCommand(cmd.trim());
			if (command != null) {
				((FilterCommand) innerCmd).setActualCommand(command);
			} else {
				command = innerCmd;
			}
			if (command.hasDepencies()) {
				Script newScript = ScriptReader.getReader().readScript(command.getName());
				new CommandParser(this.parser).parseCommands(newScript);
				((ComplexCommand) command).setInnerScript(newScript);
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
				command = new CommandBuilder().build(node.getToken().substring(1, node.getToken().length() - 1))
						.fillParameters(node);
			}

			if (command == null) {
				Script s = ExecutionContext.getInstance().getScriptByName(commandScript);
				if (s != null) {
					ComplexCommand cmd = new ScriptCommand();
					cmd.setName(commandScript);
					new CommandParser(this.parser).parseCommands(s);
					cmd.setInnerScript(s);
					command = cmd;
				}
			}

			if (command == null) {
				throw new RuntimeErrorException(null,
						String.format("A linha '%s' não é válida para o script.", commandScript));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return command;
	}

	public String parseStep(ScriptStep step) {
		ParseNode node;
		try {
			node = this.parser.parse(step.getScript());
			if (node != null) {
				return node.getChildren().get(0).getToken();
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
