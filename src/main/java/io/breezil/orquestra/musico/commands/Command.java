package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

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
		return false;
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

}
