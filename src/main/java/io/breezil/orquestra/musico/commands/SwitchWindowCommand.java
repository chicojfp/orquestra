package io.breezil.orquestra.musico.commands;

import java.util.Set;

import org.openqa.selenium.WebDriver;

public class SwitchWindowCommand extends Command {

	public SwitchWindowCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean execute(WebDriver driver) {
		
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
