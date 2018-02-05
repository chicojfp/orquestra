package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitCommand extends Command {
	private String time;

	public WaitCommand() {
		super();
	}
	
	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		new WebDriverWait(driver, Integer.parseInt(this.getTime()));
		return true;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	

}
