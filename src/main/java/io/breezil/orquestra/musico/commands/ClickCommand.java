package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class ClickCommand extends Command {

	public ClickCommand() {
		super();
	}

	public static boolean success = false;

	@Override
	protected WebElement doSearch(ExecutionContext context, WebElementDefinition elInfo) {
		WebElement el = super.doSearch(context, elInfo);

//		WaitCommand.waitClickable(el);

		return el;
	}

	@Override
	protected int doExecute(WebElement el) {

		System.out.println("           >>>> Efetuando click no elemento de id: " + el.getAttribute("id"));

		if (!Objects.isNull(el.getAttribute("onkeyup"))) {
			el.sendKeys(Keys.ENTER);
		} else {
			el.click();
		}
		return 1;
	}

}
