package io.breezil.orquestra.musico.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import io.breezil.orquestra.exception.OrquestraException;
import io.breezil.orquestra.exception.ParseException;

public class WebElementSeacher {
	private List<WebElementInfo> weInfos;
	private final int FIRST_TIMEOUT = 1;
	private final int SECOND_TIMEOUT = 3;
	
	public WebElementSeacher(String fileName) {
		this.weInfos = this.loadWEInfos(fileName);
	}
	
	public WebElementSeacher() {
	}
	
	@SuppressWarnings("serial")
	public List<WebElementInfo> loadWEInfos(String fileName) {
		List<WebElementInfo> data = new ArrayList<>();
		try {
			JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));  //new FileReader(fileName));
			Type gsonType = new TypeToken<List<WebElementInfo>>(){}.getType();
			data = new Gson().fromJson(reader, gsonType);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new OrquestraException("Não foi possível carregar o arquivo de definições.");
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
		throw new ParseException(String.format("Não há definição para elemento do tipo: '%s'", name));
	}
	
	protected WebElement findWebElement(WebDriver driver, String xpath) {
		System.out.println("Procurando elemento: " + xpath);
//		String xpathString = xpath;
		WebElement we = null;
		try {
			we = findElement(driver, FIRST_TIMEOUT, xpath);
		} catch (NoSuchElementException | TimeoutException nse) {
			try {
//				new WebDriverWait(driver, SECOND_TIMEOUT).until(ExpectedConditions.elementToBeClickable(By.xpath(xpathString)));
				we = findElement(driver, SECOND_TIMEOUT, xpath);
			} catch (org.openqa.selenium.TimeoutException te) {
				System.err.println("Não foi possível recuperar o elemento: " + xpath);
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
	
	protected WebElement findElement(WebDriver driver, long timeout, String xpath) {
		return new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
	}

	public List<WebElementInfo> getWeInfos() {
		return weInfos;
	}

	public void setWeInfos(List<WebElementInfo> weInfos) {
		this.weInfos = weInfos;
	}
	
	

}
