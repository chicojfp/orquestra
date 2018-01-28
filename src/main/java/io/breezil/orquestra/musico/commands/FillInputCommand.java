package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FillInputCommand extends Command {
	private String value;
	private WebElementInfo webElementInfo;

	public FillInputCommand(WebElementInfo webElementInfo, String name, String value) {
		super(name);
		this.value = value;
		this.webElementInfo = webElementInfo;
	}

	@Override
	public boolean execute(WebDriver driver) {
		for (String xpath : this.webElementInfo.getXpaths()) {
			try {
				WebElement el = findWebElement(driver, xpath);
				el.sendKeys(this.value);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

}
