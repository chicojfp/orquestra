package io.breezil.orquestra.compositor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.breezil.orquestra.instrumento.ExecutionContext;
import io.breezil.orquestra.musico.commands.CommandParser;

public class ContextualParser {
	private CommandParser cmdParser;

	public ContextualParser(CommandParser cp) {
		this.cmdParser = cp;
	}

	public ContextualParser() {
		this.cmdParser = new CommandParser();
	}

	private static final String SCRIPT_DEFINITION_ENDING = "Def>";

	public List<Script> parse(Script script) {
		List<Script> scripts = doContextualParser(script);
		this.cmdParser.parseCommands(script);
		return scripts;
	}

	private List<Script> doContextualParser(Script script) {
		ExecutionContext.getInstance().addScript(script);
		List<Script> scripts = new ArrayList<>();
		scripts.add(script);

		Iterator<ScriptStep> steps = script.getSteps().iterator();

//		CommandParser cp = ExecutionContext.getInstance().getParser();
		while (steps.hasNext()) {
			ScriptStep step = steps.next();
			String token = cmdParser.parseStep(step);

			if (isMethodDefinition(token)) {
				createMethodDefinition(scripts, steps, step);
			} else if (isBlankInstruction(step)) {
				steps.remove();
			}
		}

		return scripts;
	}

	private boolean isBlankInstruction(ScriptStep step) {
		return step.getScript().length() == 0;
	}

	private boolean isMethodDefinition(String token) {
		return token.contains(SCRIPT_DEFINITION_ENDING);
	}

	private void createMethodDefinition(List<Script> scripts, Iterator<ScriptStep> steps, ScriptStep step) {
		System.out.println("Registrando definição do método: " + step.getScript());

		Script s = parseMethod(step, steps);
		ExecutionContext.getInstance().addScript(s);
		scripts.add(s);
	}

	private Script parseMethod(ScriptStep step, Iterator<ScriptStep> steps) {
		steps.remove(); // Removes script definition
		Script methodScript = new Script();
		methodScript.setName(step.getScript().substring(0, step.getScript().length() - 1));
		step = steps.next(); // get first method instruction
		while (!isBlank(step)) {
			methodScript.addStep(step.getScript());
			steps.remove(); // removes current instruction
			try {
				step = steps.next(); // gets next instruction
			} catch (java.util.NoSuchElementException nse) {
				System.err.println("[ERRO] Não existe linha final após definição do método acima: " + step.getScript());
			}
		}
		steps.remove();

		return methodScript;
	}

	private boolean isBlank(ScriptStep step) {
		return step.getScript().trim().length() == 0 || step.getScript().equals("\t");
	}

}
