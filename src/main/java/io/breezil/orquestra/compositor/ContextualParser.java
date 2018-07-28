package io.breezil.orquestra.compositor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.breezil.orquestra.instrumento.ExecutionContext;
import io.breezil.orquestra.musico.commands.CommandParser;

public class ContextualParser {

	public List<Script> parse(Script script) {
		ExecutionContext.getInstance().addScript(script);
		List<Script> scripts = new ArrayList<>();
		scripts.add(script);

		Iterator<ScriptStep> steps = script.getSteps().iterator();

		CommandParser cp = ExecutionContext.getInstance().getParser();
		while (steps.hasNext()) {
			ScriptStep step = steps.next();
			String token = cp.parseStep(step);

			if (token.contains("Def")) {
				System.out.println("Registrando definição do método: " + step.getScript());

				Script s = parseMethod(steps, step);
				ExecutionContext.getInstance().addScript(s);
				scripts.add(s);
			} else if (step.getScript().length() == 0) {
				steps.remove();
			}
		}

		return scripts;
	}

	private Script parseMethod(Iterator<ScriptStep> steps, ScriptStep step) {
		steps.remove();
		Script inner = new Script();
		inner.setName(step.getScript().substring(0, step.getScript().length() - 1));
		step = steps.next();
		while (!isBlank(step)) {
			inner.addStep(step.getScript());
			// System.out.println(step.getScript());
			steps.remove();
			step = steps.next();
		}
		steps.remove();

		return inner;
	}

	private boolean isBlank(ScriptStep step) {
		return step.getScript().trim().length() == 0 || step.getScript().equals("\t");
	}

}
