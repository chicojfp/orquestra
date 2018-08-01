package io.breezil.orquestra.musico.commands;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import io.breezil.orquestra.exception.ExecutionException;
import io.breezil.orquestra.instrumento.ExecutionContext;

public class Command {
	protected String name;
	protected String item;
	protected String value;
	protected String xPathModification = "";
	private String partial;

	public String getPartial() {
		return partial;
	}

	public void setPartial(String partial) {
		this.partial = partial;
	}

	public Command() {
	}

	public String getxPathModification() {
		return xPathModification;
	}

	public void setxPathModification(String xPathModification) {
		this.xPathModification = xPathModification;
	}

	protected List<String> processElementDefinition(WebElementDefinition weDef) {
		return Arrays.asList(weDef.getXpaths()).stream().map(x -> this.updateXPathFilter(x))
				.collect(Collectors.toList());
	}

	public boolean execute(ExecutionContext context) {
		if (!(this instanceof WaitCommand)) {
			WaitCommand wait = new WaitCommand();
			wait.setItem("loading");
			wait.setPropIsDisplayed("modal");
			wait.setNot("not");
			wait.execute(context);
		}
		WebElementDefinition elInfo = getDefinitions(context);
		WebElement el = doSearch(context, elInfo);

		if (el != null && doExecute(el) > 0) {
			return true;
		}
		throw new ExecutionException(String.format("Não foi possível recuperar o %s \"%s\"", this.item, this.name));
	}

	private WebElementDefinition getDefinitions(ExecutionContext context) {
		return context.getSearcher().findObjectDefinition(this.getItem());
	}

	protected WebElement doSearch(ExecutionContext context, WebElementDefinition elInfo) {
		WebElement el = context.getSearcher().findWebElement(this.processElementDefinition(elInfo));
		return el;
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
