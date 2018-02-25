package io.breezil.orquestra.musico.commands;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {
	protected WebDriver driver;
	protected WebElementSeacher seacher;
	
	@Before
	public void configureEnviroment() {
		this.driver = Mockito.mock(FirefoxDriver.class);
		this.seacher = Mockito.mock(WebElementSeacher.class);
	}
	
	protected String captureElementXPath() {
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(this.seacher).findWebElement(Matchers.any(), captor.capture());

		String value = captor.getValue();
		return value;
	}

	protected WebElement configureWebSearcher(String xpath, String computedXPathValue) {
		WebElementInfo webInfo = new WebElementInfo();
		webInfo.setXpaths(new String[] { xpath });
		Mockito.when(this.seacher.findItem(Matchers.anyString())).thenReturn(webInfo);
		WebElement we = Mockito.mock(WebElement.class);
		Mockito.when(this.seacher.findWebElement(this.driver, computedXPathValue)).thenReturn(we);
		
		return we;
	}

}
