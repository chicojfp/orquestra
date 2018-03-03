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
	protected WebElementFinder seacher;
	
	@Before
	public void configureEnviroment() {
		this.driver = Mockito.mock(FirefoxDriver.class);
		this.seacher = Mockito.mock(WebElementFinder.class);
	}
	
	protected String captureElementXPath() {
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(this.seacher).findWebElement(captor.capture());

		String value = captor.getValue();
		return value;
	}

	protected WebElement configureWebSearcher(String xpath, String computedXPathValue) {
		WebElementDefinition webInfo = new WebElementDefinition();
		webInfo.setXpaths(new String[] { xpath });
		Mockito.when(this.seacher.findObjectDefinition(Matchers.anyString())).thenReturn(webInfo);
		WebElement we = Mockito.mock(WebElement.class);
		Mockito.when(this.seacher.findWebElement(computedXPathValue)).thenReturn(we);
		
		return we;
	}

}
