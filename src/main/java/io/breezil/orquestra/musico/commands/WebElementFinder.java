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
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import io.breezil.orquestra.exception.OrquestraException;
import io.breezil.orquestra.exception.ParseException;

public class WebElementFinder {
	private List<WebElementDefinition> weInfos;
	private SearchContext searcher;
	private final int FIRST_TIMEOUT = 1;
	private final int SECOND_TIMEOUT = 3;
	private String searchRestriction;
	
	private WebElement we = null;
	
	public WebElementFinder(String fileName) {
		this.weInfos = this.loadWEInfos(fileName);
	}
	
	public WebElementFinder() {
	}
	
	@SuppressWarnings("serial")
	public List<WebElementDefinition> loadWEInfos(String fileName) {
		List<WebElementDefinition> data = new ArrayList<>();
		try {
			JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));  //new FileReader(fileName));
			Type gsonType = new TypeToken<List<WebElementDefinition>>(){}.getType();
			data = new Gson().fromJson(reader, gsonType);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new OrquestraException("Não foi possível carregar o arquivo de definições.");
		}
		return data;
	}

	public WebElementDefinition findObjectDefinition(String name) {
		String nameToSearch = name.trim();
		for (WebElementDefinition webElementInfo : weInfos) {
			if (webElementInfo.getName().equals(nameToSearch)) {
				return webElementInfo;
			}
		}
		throw new ParseException(String.format("Não há definição para elemento do tipo: '%s'", name));
	}
	
	protected WebElement findWebElement(String xpath) {
		xpath = Strings.nullToEmpty(this.searchRestriction) + xpath;
//		WebElement we = null;
		try {
			we = findElement(searcher, FIRST_TIMEOUT, xpath);
		} catch (NoSuchElementException | TimeoutException nse) {
			try {
				we = findElement(searcher, SECOND_TIMEOUT, xpath);
			} catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException te) {
				System.err.println("Não foi possível recuperar o elemento: " + xpath);
			}
		}
		if (!Objects.isNull(we)) {
			String forElementName = we.getAttribute("for"); 
			if (!Objects.isNull(forElementName)) {
				we = findWebElementById(searcher, forElementName);
			}
		}
		return we;
	}
	
	protected List<WebElement> findWebElements(String xpath) {
		xpath = Strings.nullToEmpty(this.searchRestriction) + xpath;
		System.out.println("Procurando elementos: " + xpath);
		List<WebElement> wes = null;
		try {
			wes = findElements(searcher, FIRST_TIMEOUT, xpath);
		} catch (NoSuchElementException | TimeoutException nse) {
			try {
				wes = findElements(searcher, SECOND_TIMEOUT, xpath);
			} catch (org.openqa.selenium.TimeoutException te) {
				System.err.println("Não foi possível recuperar o elemento: " + xpath);
			}
		}
		if (!Objects.isNull(wes)) {
			for (WebElement we : wes) {
				String forElementName = we.getAttribute("for"); 
				if (!Objects.isNull(forElementName)) {
					wes.remove(we);
					wes.add(findWebElementById(searcher, forElementName));
				}
			}
		}
		return wes;
	}
	
	protected WebElement findWebElementById(SearchContext driver, String id) {
		return driver.findElement(By.id(id));
	}
	
	protected WebElement findElement(SearchContext driver, long timeout, String xpath) {
//		try {
//			Thread.sleep(timeout * 10);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		if (driver instanceof WebDriver) {
			return new WebDriverWait((WebDriver)driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		}
		return driver.findElement(By.xpath(xpath));
	}
	
	protected List<WebElement> findElements(SearchContext driver, long timeout, String xpath) {
		if (driver instanceof WebDriver) {
			new WebDriverWait((WebDriver)driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		}
		return driver.findElements(By.xpath(xpath));
	}

	public List<WebElementDefinition> getWeInfos() {
		return weInfos;
	}

	public void setWeInfos(List<WebElementDefinition> weInfos) {
		this.weInfos = weInfos;
	}

	public WebElementFinder setSearcherContext(SearchContext searcher) {
		this.searcher = searcher;
		return this;
	}
	
	public SearchContext getSearchContext() {
		return this.searcher;
	}

	public void setSearchRestriction(String restriction) {
		this.searchRestriction = restriction;
	}

	public void clearElement() {
		this.we = null;		
	}
	
	

}
