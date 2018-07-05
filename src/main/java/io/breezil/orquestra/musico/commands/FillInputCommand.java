package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class FillInputCommand extends Command {
	private String sendKey;
	private String variavel;
	private String key;

	public FillInputCommand() {
		super();
	}

	// @Override
	// public boolean execute(WebDriver driver, WebElementSeacher seacher) {
	// WebElementInfo elInfo = seacher.findItem(this.getItem());
	// for (String xpath : elInfo.getXpaths()) {
	// try {
	// WebElement el = seacher.findWebElement(driver,
	// this.updateXPathFilter(xpath));
	// if (!Objects.isNull(el)) {
	// el.clear();
	// el.sendKeys(this.getValue());
	// return true;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// throw new ExecutionException(String.format("Não foi possível recuperar o %s
	// \"%s\"", this.item, this.name));
	// }

	@Override
	protected int doExecute(WebElement el) {
		if (!Objects.isNull(this.variavel)) {
			el.clear();
			String keys[] = this.parseVariable(this.variavel);
			SetVarCommand var = ExecutionContext.getInstance().getVariableByName(keys[0]);
			String value = var.getValue();
			if (keys.length > 1) {
				value = var.getKeyValue(keys[1]);
			} else {
				value = var.getKeyValue(key);
			}
			
			el.sendKeys(value);
		} else if (Objects.isNull(this.sendKey)) {
			el.clear();
			el.sendKeys(this.getValue());
		} else {
			el.sendKeys(Keys.ENTER);
		}
		return 1;
	}
	
	private String[] parseVariable(String variable) {
		return variable.split("\\.");
	}

	public String getSendKey() {
		return sendKey;
	}

	public void setSendKey(String sendKey) {
		this.sendKey = sendKey;
	}

	public String getVariavel() {
		return variavel;
	}

	public void setVariavel(String variavel) {
		this.variavel = variavel;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
