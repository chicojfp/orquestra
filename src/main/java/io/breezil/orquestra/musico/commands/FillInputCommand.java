package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebElement;

public class FillInputCommand extends Command {

	public FillInputCommand() {
		super();
	}

//	@Override
//	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
//		WebElementInfo elInfo = seacher.findItem(this.getItem());
//		for (String xpath : elInfo.getXpaths()) {
//			try {
//				WebElement el = seacher.findWebElement(driver, this.updateXPathFilter(xpath));
//				if (!Objects.isNull(el)) {
//					el.clear();
//					el.sendKeys(this.getValue());
//					return true;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		throw new ExecutionException(String.format("Não foi possível recuperar o %s \"%s\"", this.item, this.name));
//	}
	
	@Override
	protected int doExecute(WebElement el) {
		el.clear();
		el.sendKeys(this.getValue());
		return 1;
	}

}
