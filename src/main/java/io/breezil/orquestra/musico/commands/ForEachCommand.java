package io.breezil.orquestra.musico.commands;

import java.util.List;
import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.breezil.orquestra.exception.ExecutionException;
import io.breezil.orquestra.instrumento.CommandRunner;
import io.breezil.orquestra.instrumento.ExecutionContext;

public class ForEachCommand extends ComplexCommand {

	protected int doExecute(WebElement el, WebDriver driver, WebElementFinder seacher) {
		if (!Objects.isNull(el)) {
			this.getActualCommand().doExecute(el);
			return 1;
		}
		return 0;
	}

	@Override
	public String updateXPathFilter(String xpath) {
		return String.format(xpath, "");
	}

	@Override
	public boolean execute(ExecutionContext context) {
		WebElementDefinition elInfo = context.getSearcher().findObjectDefinition(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
			String searchRestriction = this.updateXPathFilter(xpath);
			List<WebElement> els = context.getSearcher().findWebElements(searchRestriction);
			for (int order = 1; order <= els.size(); order++) {
				String restrictionPath = String.format("(%s)[%s]/parent::*", searchRestriction, order);
				context.getSearcher().setSearchRestriction(restrictionPath);
				CommandRunner.run(this.getInnerScript(), context);
			}
			context.getSearcher().setSearchRestriction(null);
			return true;
		}
		throw new ExecutionException(String.format("Não foi possível recuperar o %s \"%s\"", this.item, this.name));
	}

	@Override
	public boolean hasDepencies() {
		return true;
	}

}
