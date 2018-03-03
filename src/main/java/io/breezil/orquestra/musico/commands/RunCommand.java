package io.breezil.orquestra.musico.commands;

import io.breezil.orquestra.instrumento.CommandRunner;
import io.breezil.orquestra.instrumento.ExecutionContext;

public class RunCommand extends ComplexCommand {
	
	@Override
	public boolean execute(ExecutionContext context) {
		
		CommandRunner.run(this.getInnerScript(), context);
		
		return true;
	}
	
	@Override
	public boolean hasDepencies() {
		return true;
	}

}
