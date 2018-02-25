package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.breezil.orquestra.exception.ExecutionException;

public class VerifyCommand extends Command {
	private String action;
	private String propIsDisplayed;
	private String propIsEnabled;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		return super.execute(driver, seacher);
	}

	@Override
	protected int doExecute(WebElement el) {
		
		if (!Objects.isNull(this.propIsDisplayed) && !el.isDisplayed()) {
			throw new ExecutionException(String.format("O %s \"%s\" não é \"%s\"", this.item, this.name, this.action));
		} else if (!Objects.isNull(this.propIsEnabled) && !el.isEnabled()) {
			throw new ExecutionException(String.format("O %s \"%s\" não é \"%s\"", this.item, this.name, this.action));
		}
		
		return 1;
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
