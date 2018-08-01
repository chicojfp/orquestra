package io.breezil.orquestra.musico.commands;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class DialogCommand extends Command {

	@Override
	public boolean execute(ExecutionContext context) {
		org.openqa.selenium.Alert alert = context.getDriver().switchTo().alert();
		alert.accept();
		return true;
	}

}
