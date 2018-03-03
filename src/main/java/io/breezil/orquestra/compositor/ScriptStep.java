package io.breezil.orquestra.compositor;

import io.breezil.orquestra.musico.commands.Command;

public class ScriptStep {
	private String script;
	private int order;
	private Command commmand;
	private boolean success = false;
	private Script innerScript;

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setCommand(Command command) {
		this.commmand = command;
	}

	public Command getCommand() {
		return this.commmand;
	}

	public boolean hasDepencies() {
		return this.script.startsWith("Execute");
	}

	public void setSuccessExecution(boolean success) {
		this.success  = success;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setInnerScript(Script newScript) {
		this.innerScript = newScript;
	}

	public Script getInnerScript() {
		return this.innerScript;
	}

}
