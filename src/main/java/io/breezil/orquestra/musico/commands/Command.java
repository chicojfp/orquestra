package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Command {
	protected String name;
	protected String item;
	private String xPathModification = "";
	
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
	
	protected WebElement findWebElement(WebDriver driver, String xpath) {
		String xpathString = this.getxPathModification() + String.format(xpath, this.name);
		System.out.println("Procurando elemento: " + xpathString);
		WebElement we = null;
		try {
			we = findElement(driver, xpath);
		} catch (NoSuchElementException | TimeoutException nse) {
			try {
				new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath(xpathString)));
				we = findElement(driver, xpath);
			} catch (org.openqa.selenium.TimeoutException te) {
				System.err.println("Não foi possível recuperar o elemento: " + xpathString);
			}
		}
		if (!Objects.isNull(we)) {
			String forElementName = we.getAttribute("for"); 
			if (!Objects.isNull(forElementName)) {
				we = findWebElementById(driver, forElementName);
			}
		}
		return we;
	}

	private WebElement findElement(WebDriver driver, String xpath) {
		return new WebDriverWait(driver, 1).until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(xpath, this.name))));
//		return driver.findElement(By.xpath(String.format(xpath, this.name)));
	}
	
	protected WebElement findWebElementById(WebDriver driver, String id) {
		return driver.findElement(By.id(id));
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
