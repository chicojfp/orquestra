package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.breezil.orquestra.exception.ExecutionException;

public class Command {
	protected String name;
	protected String item;
	protected String value;
	protected String xPathModification = "";

	public Command() {
	}

	public String getxPathModification() {
		return xPathModification;
	}

	public void setxPathModification(String xPathModification) {
		this.xPathModification = xPathModification;
	}

	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		WebElementInfo elInfo = seacher.findItem(this.getItem());
		
		for (String xpath : elInfo.getXpaths()) {
			WebElement el =  null;
			el = seacher.findWebElement(driver, this.updateXPathFilter(xpath));
			if (el != null) {
				return doExecute(el) > 0;
			}
		}
		
		
		
//		Arrays.asList(elInfo.getXpaths()).stream().parallel().forEach((xpath)-> {
//			WebElement el =  null;
//			el = seacher.findWebElement(driver, this.updateXPathFilter(xpath));
//			if (el != null) {
//				doExecute(el);
//			}
//		});
		
		
//		boolean isOk = Arrays.asList(elInfo.getXpaths()).parallelStream().anyMatch(p -> {
//			WebElement el =  null;
//			el = seacher.findWebElement(driver, this.updateXPathFilter(p));
//			if (el != null) {
//				return doExecute(el) > 0;
//			}
//			return false;
//		});
		
		
//		int value = Arrays.asList(elInfo.getXpaths()).parallelStream().mapToInt(p -> {
//			WebElement el =  null;
//			el = seacher.findWebElement(driver, this.updateXPathFilter(p));
//			if (el != null) {
//				return doExecute(el);
//			}
//			return 0;
//		}).sum();
		
		throw new ExecutionException(String.format("Não foi possível recuperar o %s \"%s\"", this.item, this.name));
		
	}

	protected int doExecute(WebElement el) {
		return 10;
	}

	public String updateXPathFilter(String xpath) {
		String newXPath = xpath;
		if (!Objects.isNull(name)) {
			newXPath = String.format(newXPath, this.name);
		}
		return Objects.toString(this.xPathModification, "") + newXPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean hasDepencies() {
		return false;
	}

}
