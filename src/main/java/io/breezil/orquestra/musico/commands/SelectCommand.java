package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectCommand extends Command {
	private String optionValue;
	private WebElementInfo webElementInfo;

	public SelectCommand(WebElementInfo info, String name, String optionValue) {
		super(name);
		this.optionValue = optionValue;
		this.webElementInfo = info;
	}
	
	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		WebElementInfo elInfo = seacher.findItem(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
			WebElement el = findWebElement(driver, xpath);
			if (el != null) {
				Select select = new Select(el);
				select.selectByVisibleText(optionValue);
			}
		}

		return false;
	}

}
