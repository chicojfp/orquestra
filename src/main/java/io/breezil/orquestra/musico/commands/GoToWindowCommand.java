package io.breezil.orquestra.musico.commands;

import java.util.Set;

import org.openqa.selenium.WebDriver;

public class GoToWindowCommand extends Command {

	public GoToWindowCommand() {
		super("");
	}
	
	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		
		Set<String> handles = driver.getWindowHandles();
		
		for (String handle : handles) {
			if (handle.equals(driver.getWindowHandle())) {
				continue;
			}
			
			driver.switchTo().window(handle);
			break;
		}
		
		
		return true;
	}

}
