package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickCommand extends Command {
	WebElementInfo webElementInfo;

	public ClickCommand(WebElementInfo webElementInfo, String name) {
		super(name);
		this.webElementInfo = webElementInfo;
	}

	@Override
	public boolean execute(WebDriver driver) {
		for (String xpath : this.webElementInfo.getXpaths()) {
			WebElement el = findWebElement(driver, xpath);
			if (el != null) {
				el.click();
				System.out.println("Click");
				return true;
			}
		}

		return false;
	}

}
