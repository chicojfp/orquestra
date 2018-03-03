package io.breezil.orquestra.musico.commands;

import io.breezil.orquestra.compositor.Script;

public class ComplexCommand extends Command {
	private Command actualCommand;
	private Script innerScript;

	public Command getActualCommand() {
		return actualCommand;
	}

	public void setActualCommand(Command actualCommand) {
		this.actualCommand = actualCommand;
	}

	public void setInnerScript(Script innerScript) {
		this.innerScript = innerScript;
	}

	public Script getInnerScript() {
		return this.innerScript;
	}

}
