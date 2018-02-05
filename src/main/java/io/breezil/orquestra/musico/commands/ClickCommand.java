package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickCommand extends Command {

	public ClickCommand() {
		super();
	}

	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		WebElementInfo elInfo = seacher.findItem(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
			WebElement el = seacher.findWebElement(driver, this.updateXPathFilter(xpath));
			if (el != null) {
				if (!Objects.isNull(el.getAttribute("onkeyup"))) {
					el.sendKeys(Keys.ENTER);
				} else {
					el.click();
				}
				return true;
			}
		}

		return false;
	}

}
