package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class SelectCommand extends Command {

	public SelectCommand() {
		super();
	}
	
	@Override
	public boolean execute(ExecutionContext context) {
		WebElementDefinition elInfo = context.getSearcher().findObjectDefinition(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
			WebElement el = context.getSearcher().findWebElement(this.updateXPathFilter(xpath));
			if (el != null) {
				Select select = new Select(el);
				select.selectByVisibleText(this.value);
				return true;
			}
		}

		return false;
	}

}
