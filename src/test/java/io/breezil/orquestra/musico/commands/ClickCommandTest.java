package io.breezil.orquestra.musico.commands;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import io.breezil.orquestra.instrumento.ExecutionContext;

@RunWith(MockitoJUnitRunner.class)
public class ClickCommandTest extends BaseTest {

	@Test
	public void mustSendKeysIfHasOnKeyAttribute() {
		String attributeName = "onkeyup";
		runClickCommand(attributeName);
	}

	@Test
	public void mustClickByDefalt() {
		String attributeName = null;
		runClickCommand(attributeName);
	}

	private void runClickCommand(String attributeName) {
		String expectedValue = "//button[contains(.,\"Login\")]";
		configureWebSearcher("//button[contains(.,\"%s\")]", expectedValue, attributeName);

		ClickCommand cmd = new ClickCommand();
		cmd.setName("Login");
		cmd.execute(new ExecutionContext(this.driver, this.seacher));

		List<String> value = capture_findWebElement_List_String();
		Assert.assertEquals("Não retornou apenas um objeto", 1, value.size());
		Assert.assertEquals("O botão foi não identificado corretamente", value.get(0),
				"//button[contains(.,\"Login\")]");
	}

	protected void configureWebSearcher(String xpath, String expectedValue, String attName) {
		WebElement we = super.configureWebSearcher(xpath, expectedValue);

		if (!Objects.isNull(attName)) {
			Mockito.when(we.getAttribute(attName)).thenReturn("doAction()");
		}
	}

	private WebElement mockWebElementSerarchFindById(WebElementFinder searcher, String weId) {
		WebElement webElementMock = Mockito.mock(WebElement.class);
		Mockito.doReturn(webElementMock).when(searcher).findWebElementById(driver, weId);
		return webElementMock;
	}

}
