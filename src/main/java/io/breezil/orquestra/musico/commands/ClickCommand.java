package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickCommand extends Command {

	public ClickCommand() {
		super("");
	}

	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		WebElementInfo elInfo = seacher.findItem(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
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
