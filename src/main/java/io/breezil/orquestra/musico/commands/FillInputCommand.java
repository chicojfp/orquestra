package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FillInputCommand extends Command {

	public FillInputCommand() {
		super();
	}

	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		WebElementInfo elInfo = seacher.findItem(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
			try {
				WebElement el = seacher.findWebElement(driver, this.updateXPathFilter(xpath));
				if (!Objects.isNull(el)) {
					el.clear();
					System.out.println(el.getText());
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return false;
	}

}
