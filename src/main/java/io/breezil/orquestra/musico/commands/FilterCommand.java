package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

public class FilterCommand extends Command {
	private Command actualCommand;
	
	public FilterCommand() {
		super("");
	}

	public Command getActualCommand() {
		return actualCommand;
	}

	public void setActualCommand(Command actualCommand) {
		this.actualCommand = actualCommand;
	}
	
	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		WebElementInfo elInfo = seacher.findItem(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
			String previusMod = Objects.toString(this.getxPathModification(), "");
			this.actualCommand.setxPathModification(previusMod + String.format(xpath, this.name)); 
			this.actualCommand.execute(driver, seacher);
		}

		return false;
	}
	
	

}
