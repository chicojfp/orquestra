package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class WaitCommand extends Command {
	private String time;
	private String propIsDisplayed;
	private String propIsEnabled;
	private String not;

	public WaitCommand() {
		super();
	}

	@Override
	public boolean execute(ExecutionContext context) {
		if (this.getTime() != null) {
			new WebDriverWait(context.getDriver(), Integer.parseInt(this.getTime()));
		} else {
			new WebDriverWait(context.getDriver(), 5).until(defineWaitCondition());
		}

		return true;
	}

	private ExpectedCondition<?> defineWaitCondition() {
		switch (this.getCondition()) {
		case "visible":
			return ExpectedConditions.visibilityOfElementLocated(By.id("modalStatusDiv"));
		case "invisible":
			return ExpectedConditions.invisibilityOfElementLocated(By.id("modalStatusDiv"));
		case "clickable":
			return ExpectedConditions.elementToBeClickable(By.id("modalStatusDiv"));

		default:
			break;
		}

		return ExpectedConditions.invisibilityOfElementLocated(By.id("modalStatusDiv"));
	}

	private String getCondition() {
		if (this.getPropIsDisplayed() != null) {
			if (this.not != null)
				return "invisible";
			return "visible";
		}
		return "clickable";
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public String getNot() {
		return not;
	}

	public void setNot(String not) {
		this.not = not;
	}

}
