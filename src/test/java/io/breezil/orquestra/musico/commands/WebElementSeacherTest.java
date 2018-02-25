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
public class WebElementSeacherTest {
	private WebDriver driver;
	
	@Before
	public void configureWebDriver() {
		driver = Mockito.mock(WebDriver.class);
	}

	@Test
	public void mustLoadAllDefinitions() {
		new WebElementSeacher("./samples/definition.json");
	}
	
	@Test(expected = OrquestraException.class)
	public void mustLoadThrowExceptionIfFileNotFound() {
		new WebElementSeacher("./samples/deeeeeefinition.json");
	}
	
	@Test
	public void mustLoadFindADefinedItem() {
		WebElementInfo we = new WebElementSeacher("./samples/definition.json").findItem("botão");
		
		Assert.assertNotNull("A definição do objeto não foi encontrada.", we);
	}
	
	@Test(expected = ParseException.class)
	public void mustThrowErrorWhenObjectNotFound() {
		WebElementInfo we = new WebElementSeacher("./samples/definition.json").findItem("booootão");
		
		Assert.assertNotNull("A definição do objeto não foi encontrada.", we);
	}
	
	@Test
	public void mustFindADefinedElement() {
		WebElementSeacher searcher = mockSearchElementFromFile();
		mockWebElementSerarchFindElementoReturnValidElement(searcher, 1l);
//		mockWebElementSerarchFindElementoDoTrhow(searcher, 1l);
		
		WebElement we = searcher.findWebElement(driver, "//a");
		
		Assert.assertNotNull("O Objeto não foi encontrado na página na primeira tentativa de localizá-lo.", we);
	}
	
	@Test
	public void mustFindADefinedElementWithSecondLongWaitTimeout() {
		WebElementSeacher searcher = mockSearchElementFromFile();
		mockWebElementSerarchFindElementoReturnValidElement(searcher, 3l);
		mockWebElementSerarchFindElementoDoTrhow(searcher, 1l);
		
		WebElement we = searcher.findWebElement(driver, "//a");
		
		Assert.assertNotNull("O Objeto não foi encontrado na página na SEGUNDA tentativa de localizá-lo.", we);
	}
	
	@Test
	public void mustReturnNullIfWebElementIsNotInPage() {
		WebElementSeacher searcher = mockSearchElementFromFile();
		mockWebElementSerarchFindElementoDoTrhow(searcher, 1l);
		mockWebElementSerarchFindElementoDoTrhow(searcher, 3l);
		
		WebElement we = searcher.findWebElement(driver, "//a");
		
		Assert.assertNull("WebElement não existe na página.", we);
	}
	
	@Test
	public void mustIdentifyWebElementsWithForAttribute() {
		WebElementSeacher searcher = mockSearchElementFromFile();
		WebElement we = mockWebElementSerarchFindElementoReturnValidElement(searcher, 1l);
		String objectID = "objectIdForAttribute";
		Mockito.doReturn(objectID).when(we).getAttribute("for");
		
		we = mockWebElementSerarchFindById(searcher, objectID);
		Mockito.doReturn(objectID).when(we).getAttribute("id");
		
		
		WebElement weID = searcher.findWebElement(driver, "//a");
		
		Assert.assertEquals("WebElement não existe na página.", objectID, weID.getAttribute("id"));
	}

	private WebElementSeacher mockSearchElementFromFile() {
		WebElementSeacher searcher = Mockito.spy(WebElementSeacher.class);
		searcher.setWeInfos(searcher.loadWEInfos("./samples/definition.json"));
		return searcher;
	}

	private WebElement mockWebElementSerarchFindElementoReturnValidElement(WebElementSeacher searcher, long timeout) {
		WebElement webElementMock = Mockito.mock(WebElement.class);
		Mockito.doReturn(webElementMock).when(searcher).findElement(driver, timeout, "//a");
		return webElementMock;
	}
	
	private WebElement mockWebElementSerarchFindById(WebElementSeacher searcher, String weId) {
		WebElement webElementMock = Mockito.mock(WebElement.class);
		Mockito.doReturn(webElementMock).when(searcher).findWebElementById(driver, weId);
		return webElementMock;
	}

	private void mockWebElementSerarchFindElementoDoTrhow(WebElementSeacher searcher, long timeout) {
		Mockito.doThrow(TimeoutException.class).when(searcher).findElement(driver, timeout, "//a");
	}

}
