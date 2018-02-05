package io.breezil.orquestra.musico.commands;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class WebElementSeacher {
	private List<WebElementInfo> weInfos;
	private final int FIRST_TIMEOUT = 1;
	private final int SECOND_TIMEOUT = 1;
	
	public WebElementSeacher(String fileName) {
		this.weInfos = this.loadWEInfos(fileName);
	}
	
	@SuppressWarnings("serial")
	private List<WebElementInfo> loadWEInfos(String fileName) {
		List<WebElementInfo> data = new ArrayList<>();
		try {
			JsonReader reader = new JsonReader(new FileReader(fileName));
			Type gsonType = new TypeToken<List<WebElementInfo>>(){}.getType();
			data = new Gson().fromJson(reader, gsonType);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}

	public WebElementInfo findItem(String name) {
		String nameToSearch = name.trim();
		for (WebElementInfo webElementInfo : weInfos) {
			if (webElementInfo.getName().equals(nameToSearch)) {
				return webElementInfo;
			}
		}
		return null;
	}
	
	protected WebElement findWebElement(WebDriver driver, String xpath) {
		String xpathString = xpath;
		WebElement we = null;
		try {
			we = findElement(driver, xpath);
		} catch (NoSuchElementException | TimeoutException nse) {
			try {
				new WebDriverWait(driver, SECOND_TIMEOUT).until(ExpectedConditions.elementToBeClickable(By.xpath(xpathString)));
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
	
	protected WebElement findWebElementById(WebDriver driver, String id) {
		return driver.findElement(By.id(id));
	}
	
	private WebElement findElement(WebDriver driver, String xpath) {
		return new WebDriverWait(driver, FIRST_TIMEOUT).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

}
