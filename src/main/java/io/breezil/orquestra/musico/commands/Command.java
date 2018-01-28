package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Command {
	protected String name;
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
	
	
	public boolean execute(WebDriver driver) {		
		
		return false;
	}
	
	protected WebElement findWebElement(WebDriver driver, String xpath) {
		String xpathString = this.getxPathModification() + String.format(xpath, this.name);
		WebElement we = null;
		try {
			we = findElement(driver, xpath);
		} catch (NoSuchElementException nse) {
			try {
				new WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(By.xpath(xpathString)));
				we = findElement(driver, xpath);
			} catch (org.openqa.selenium.TimeoutException te) {
				System.err.println("Não foi possível recuperar o elemento: " + xpathString);
			}
		}
		return we;
	}

	private WebElement findElement(WebDriver driver, String xpath) {
		return driver.findElement(By.xpath(String.format(xpath, this.name)));
	}

}
