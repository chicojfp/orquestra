package io.breezil.orquestra.musico.commands;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.breezil.orquestra.exception.OrquestraException;
import io.breezil.orquestra.exception.ParseException;

@RunWith(MockitoJUnitRunner.class)
public class WebElementFinderTest {
	private static final String DEFINITION_FILE = "./resources/samples/definition.json";
	private WebDriver driver;

	@Before
	public void configureWebDriver() {
		driver = Mockito.mock(WebDriver.class);
	}

	@Test
	public void mustLoadAllDefinitions() {
		new WebElementFinder(DEFINITION_FILE);
	}

	@Test(expected = OrquestraException.class)
	public void mustLoadThrowExceptionIfFileNotFound() {
		new WebElementFinder("./samples/deeeeeefinition.json");
	}

	@Test
	public void mustLoadFindADefinedItem() {
		WebElementDefinition we = new WebElementFinder(DEFINITION_FILE).findObjectDefinition("botão");

		Assert.assertNotNull("A definição do objeto não foi encontrada.", we);
	}

	@Test(expected = ParseException.class)
	public void mustThrowErrorWhenObjectNotFound() {
		WebElementDefinition we = new WebElementFinder(DEFINITION_FILE).findObjectDefinition("booootão");

		Assert.assertNotNull("A definição do objeto não foi encontrada.", we);
	}

	@Test
	public void mustFindADefinedElement() {
		WebElementFinder searcher = mockSearchElementFromFile();
		mockWebElementSerarchFindElementoReturnValidElement(searcher, 1l);
		// mockWebElementSerarchFindElementoDoTrhow(searcher, 1l);

		WebElement we = searcher.findWebElement("//a");

		Assert.assertNotNull("O Objeto não foi encontrado na página na primeira tentativa de localizá-lo.", we);
	}

	@Test
	public void mustReturnNullIfWebElementIsNotInPage() {
		WebElementFinder searcher = mockSearchElementFromFile();
		mockWebElementSerarchFindElementoDoTrhow(searcher, 1l);
		mockWebElementSerarchFindElementoDoTrhow(searcher, 3l);

		WebElement we = searcher.findWebElement("//a");

		Assert.assertNull("WebElement não existe na página.", we);
	}

	@Test
	public void mustIdentifyWebElementsWithForAttribute() {
		WebElementFinder searcher = mockSearchElementFromFile();
		WebElement we = mockWebElementSerarchFindElementoReturnValidElement(searcher, 1l);
		String objectID = "objectIdForAttribute";
		Mockito.doReturn(objectID).when(we).getAttribute("for");

		we = mockWebElementSerarchFindById(searcher, objectID);
		Mockito.doReturn(objectID).when(we).getAttribute("id");

		WebElement weID = searcher.findWebElement("//a");

		Assert.assertEquals("WebElement não existe na página.", objectID, weID.getAttribute("id"));
	}

	private WebElementFinder mockSearchElementFromFile() {
		WebElementFinder searcher = Mockito.spy(WebElementFinder.class);
		searcher.setWeInfos(searcher.loadWEInfos(DEFINITION_FILE));
		searcher.setSearcherContext(this.driver);
		return searcher;
	}

	private WebElement mockWebElementSerarchFindElementoReturnValidElement(WebElementFinder searcher, long timeout) {
		WebElement webElementMock = Mockito.mock(WebElement.class);
		Mockito.doReturn(webElementMock).when(searcher).findElement(driver, timeout, "//a");
		return webElementMock;
	}

	private WebElement mockWebElementSerarchFindById(WebElementFinder searcher, String weId) {
		WebElement webElementMock = Mockito.mock(WebElement.class);
		Mockito.doReturn(webElementMock).when(searcher).findWebElementById(driver, weId);
		return webElementMock;
	}

	private void mockWebElementSerarchFindElementoDoTrhow(WebElementFinder searcher, long timeout) {
		Mockito.doThrow(TimeoutException.class).when(searcher).findElement(driver, timeout, "//a");
	}

}
