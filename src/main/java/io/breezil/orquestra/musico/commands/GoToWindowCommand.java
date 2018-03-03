package io.breezil.orquestra.musico.commands;

import java.util.Set;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class GoToWindowCommand extends Command {

	public GoToWindowCommand() {
		super();
	}
	
	@Override
	public boolean execute(ExecutionContext context) {
		
		Set<String> handles = context.getDriver().getWindowHandles();
		
		for (String handle : handles) {
			if (handle.equals(context.getDriver().getWindowHandle())) {
				continue;
			}
			
			context.getDriver().switchTo().window(handle);
			break;
		}
		
		
		return true;
	}

}
