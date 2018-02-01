package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

public class Command {
	protected String name;
	protected String item;
	protected String xPathModification = "";
	
	public String getxPathModification() {
		return xPathModification;
	}


	public void setxPathModification(String xPathModification) {
		this.xPathModification = xPathModification;
	}


	public Command(String name) {
		this.name = name;
	}
	
	
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		return false;
	}
	
	public String updateXPathFilter(String xpath) {
		if (!Objects.isNull(name)) {
			return String.format(xpath, this.name);
		}
		return xpath;
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
	
}
