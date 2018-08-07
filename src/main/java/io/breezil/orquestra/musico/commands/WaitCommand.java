package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.breezil.orquestra.exception.ExecutionException;
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
		this.setName(this.getItem());
		if (this.getTime() != null) {
			new WebDriverWait(ExecutionContext.getInstance().getDriver(), Integer.parseInt(this.getTime()));
		} else {
			try {
				super.execute(context);
			} catch (ExecutionException ee) {
//				ee.printStackTrace();
			}
		}
		return true;
	}

	@Override
	protected WebElement doSearch(ExecutionContext context, WebElementDefinition elInfo) {
		WebElement el = context.getSearcher().findWebElement(this.processElementDefinition(elInfo), 1);
		return el;
	}

	@Override
	protected int doExecute(WebElement el) {
		new WebDriverWait(ExecutionContext.getInstance().getDriver(), 10).until(defineWaitCondition(el));
		return 1;
	}

	private ExpectedCondition<?> defineWaitCondition(WebElement el) {
		switch (this.getCondition()) {
		case "visible":
			return ExpectedConditions.visibilityOf(el);
		case "invisible":
			return ExpectedConditions.invisibilityOf(el);
		case "clickable":
			return ExpectedConditions.elementToBeClickable(el);

		default:
			break;
		}

		return ExpectedConditions.invisibilityOf(el);
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

	public static void waitClickable(WebElement el) {
		new WaitCommand().doExecute(el);
	}

	public static void waitModal() {
		WaitCommand wait = new WaitCommand();
		wait.setItem("loading");
		wait.setPropIsDisplayed("modal");
		wait.setNot("not");
		wait.execute(ExecutionContext.getInstance());
	}

}
