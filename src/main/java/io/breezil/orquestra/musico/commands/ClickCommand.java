package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class ClickCommand extends Command {

	public ClickCommand() {
		super();
	}
	
	public static boolean success = false;

//	@Override
//	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
//		WebElementInfo elInfo = seacher.findItem(this.getItem());
//		Arrays.asList(elInfo.getXpaths()).stream().parallel().forEach((xpath)-> { 
//			
//			WebElement el =  null;
//			el = seacher.findWebElement(driver, this.updateXPathFilter(xpath));
//			if (el != null) {
//				if (!Objects.isNull(el.getAttribute("onkeyup"))) {
//					el.sendKeys(Keys.ENTER);
//				} else {
//					el.click();
//				}
//				ClickCommand.success |= true;
//			}
//			
//		});
////		for (String xpath : elInfo.getXpaths()) {
////		}
//		if (!ClickCommand.success) {
//		  throw new ExecutionException(String.format("Não foi possível recuperar o %s \"%s\"", this.item, this.name));
//		}
//		return ClickCommand.success;
//	}
	@Override
	protected int doExecute(WebElement el) {
		if (!Objects.isNull(el.getAttribute("onkeyup"))) {
			el.sendKeys(Keys.ENTER);
		} else {
			el.click();
		}
		return 1;
	}

}
