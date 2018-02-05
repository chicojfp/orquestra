package io.breezil.orquestra.compositor;

import java.util.ArrayList;
import java.util.List;

public class Script {
	private String name;
	private List<ScriptStep> scriptSteps;
	
	public Script() {
		this.scriptSteps = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ScriptStep> getSteps() {
		return scriptSteps;
	}

	public void setScriptSteps(List<ScriptStep> scriptStep) {
		this.scriptSteps = scriptStep;
	}
	
	public void addStep(String script) {
		ScriptStep step = new ScriptStep();
		step.setScript(script);
		step.setOrder(this.scriptSteps.size() + 1);
		this.scriptSteps.add(step);
	}

}
