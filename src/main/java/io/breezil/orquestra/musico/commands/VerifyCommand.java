package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebElement;

import com.google.common.base.Strings;

import io.breezil.orquestra.exception.ExecutionException;
import io.breezil.orquestra.instrumento.ExecutionContext;

public class VerifyCommand extends Command {
	private String action;
	private String propIsDisplayed;
	private String propIsEnabled;
	private String containsText;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public boolean execute(ExecutionContext context) {
		return super.execute(context);
	}

	@Override
	protected int doExecute(WebElement el) {

		if (!Objects.isNull(this.propIsDisplayed) && !el.isDisplayed()) {
			throwDefaultException("O %s \"%s\" não é \"%s\"");
		} else if (!Objects.isNull(this.propIsEnabled) && !el.isEnabled()) {
			throwDefaultException("O %s \"%s\" não é \"%s\"");
		} else if (!Objects.isNull(this.value)) {
			String text = el.getText();
			if (Strings.isNullOrEmpty(text)) {
				text = el.getAttribute("value");
			}
			if (!text.equals(this.value)) {
				throwDefaultException("O %s \"%s\" não contém \"%s\"");
			}
		}

		System.out.println("Campo " + this.name + " validado com sucesso!");

		return 1;
	}

	private void throwDefaultException(String msg) {
		throw new ExecutionException(String.format(msg, this.item, this.name, this.value));
	}

	public String getPropIsDisplayed() {
		return propIsDisplayed;
	}

	public void setPropIsDisplayed(String propIsDisplayed) {
		this.propIsDisplayed = propIsDisplayed;
	}

	public String getPropIsEnabled() {
		return propIsEnabled;
	}

	public void setPropIsEnabled(String propIsEnabled) {
		this.propIsEnabled = propIsEnabled;
	}

}
