package io.breezil.orquestra.musico.commands;

import java.util.Objects;

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
				mapText2Select(select);
				select.selectByVisibleText(this.value);
				return true;
			}
		}

		return false;
	}

	private String mapText2Select(Select select) {
		if (!Objects.isNull(this.getPartial())) {
			mapPartial2FullText(select);
		}
		return this.getValue();
	}

	private void mapPartial2FullText(Select select) {
		select.getOptions().forEach(opt -> {
			if (opt.getText().contains(this.getValue())) {
				this.setValue(opt.getText());
			}
		});
	}

}
