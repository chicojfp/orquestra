package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.support.ui.WebDriverWait;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class WaitCommand extends Command {
	private String time;

	public WaitCommand() {
		super();
	}
	
	@Override
	public boolean execute(ExecutionContext context) {
		new WebDriverWait(context.getDriver(), Integer.parseInt(this.getTime()));
		return true;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	

}
