package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebElement;

import io.breezil.orquestra.musico.VariableBag;

public class CopyCommand extends Command {
	private String variableName;

	@Override
	protected int doExecute(WebElement el) {
		if (!Objects.isNull(el)) {
			VariableBag.getInstance().addVariable(this.value, el.getText());
			return 1;
		}
		return 0;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

}
