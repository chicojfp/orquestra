package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

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
		cmd.execute(this.driver, this.seacher);

		String value = captureElementXPath();
		Assert.assertEquals("O botão foi não identificado corretamente", value, "//button[contains(.,\"Login\")]");
	}
	
	protected void configureWebSearcher(String xpath, String expectedValue, String attName) {
		WebElement we = super.configureWebSearcher(xpath, expectedValue);
		
		if (!Objects.isNull(attName)) {
			Mockito.when(we.getAttribute(attName)).thenReturn("doAction()");
		}
	}
	

	

}
