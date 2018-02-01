package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FillInputCommand extends Command {
	private String value;

	public FillInputCommand() {
		super("");
	}

	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		WebElementInfo elInfo = seacher.findItem(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
