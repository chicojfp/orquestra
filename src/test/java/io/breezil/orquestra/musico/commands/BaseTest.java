package io.breezil.orquestra.musico.commands;

import java.util.Arrays;
import java.util.List;

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

	protected String BASE_DIR = "./resources/samples/";
	protected String GRAMMAR_FILE = BASE_DIR + "web-grammar.bnf";
	protected String DEFINITION_FILE = BASE_DIR + "definition.json";
	protected String TEST_FILE = BASE_DIR + "TC-001.test";

	@Before
	public void configureEnviroment() {
		this.driver = Mockito.mock(FirefoxDriver.class);
		this.seacher = Mockito.mock(WebElementFinder.class);
	}

	protected String capture_findWebElement_String() {
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		Mockito.verify(this.seacher).findWebElement(captor.capture());

		String value = captor.getValue();
		return value;
	}

	protected List<String> capture_findWebElement_List_String() {
		Class<List<String>> listClass = (Class) List.class;
		ArgumentCaptor<List<String>> argument = ArgumentCaptor.forClass(listClass);
		Mockito.verify(this.seacher).findWebElement(argument.capture());

		List<String> value = argument.getValue();
		return value;
	}

	protected WebElement configureWebSearcher(String xpath, String computedXPathValue) {
		WebElementDefinition webInfo = new WebElementDefinition();
		webInfo.setXpaths(new String[] { xpath });
		Mockito.when(this.seacher.findObjectDefinition(Matchers.anyString())).thenReturn(webInfo);
		WebElement we = Mockito.mock(WebElement.class);
		Mockito.when(this.seacher.findWebElement(computedXPathValue)).thenReturn(we);
		Mockito.when(this.seacher.findWebElement(Arrays.asList(new String[] { computedXPathValue }))).thenReturn(we);

		return we;
	}

}
