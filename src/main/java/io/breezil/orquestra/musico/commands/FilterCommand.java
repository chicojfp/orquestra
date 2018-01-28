package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

public class FilterCommand extends Command {
	private WebElementInfo webElementInfo;
	private Command actualCommand;
	
	public FilterCommand(String name, WebElementInfo webElementInfo) {
		super(name);
		this.webElementInfo = webElementInfo;
	}

	public Command getActualCommand() {
		return actualCommand;
	}

	public void setActualCommand(Command actualCommand) {
		this.actualCommand = actualCommand;
	}
	
	@Override
	public boolean execute(WebDriver driver) {
		for (String xpath : this.webElementInfo.getXpaths()) {
			String previusMod = Objects.toString(this.getxPathModification(), "");
			this.actualCommand.setxPathModification(previusMod + String.format(xpath, this.name)); 
			this.actualCommand.execute(driver);
		}

		return false;
	}
	
	

}
