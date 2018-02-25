package io.breezil.orquestra.musico.commands;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.breezil.orquestra.exception.ExecutionException;

@RunWith(MockitoJUnitRunner.class)
public class CommandTest extends BaseTest {

	@Override
	@Before
	public void configureEnviroment() {
		this.driver = Mockito.mock(FirefoxDriver.class);
		this.seacher = Mockito.mock(WebElementSeacher.class);
	}

	@Test
	public void mustReplaceElementName() {
		String expectedValue = "//button[contains(.,\"Login\")]";
		configureWebSearcher("//button[contains(.,\"%s\")]", expectedValue);

		ClickCommand cmd = new ClickCommand();
		cmd.setName("Login");
		cmd.setItem("Button");
		cmd.setValue("XXX");
		cmd.execute(this.driver, this.seacher);

		Assert.assertEquals("O botão foi não identificado corretamente", expectedValue, captureElementXPath());
	}

	@Test
	public void mustAddSerachPathIfDefined() {
		String tableName = "//table[.==\"Table name\"]";
		String expectedValue = "//button[contains(.,\"Login\")]";
		configureWebSearcher("//button[contains(.,\"%s\")]", tableName + expectedValue);

		ClickCommand cmd = new ClickCommand();
		cmd.setxPathModification(tableName);
		cmd.setName("Login");
		cmd.execute(this.driver, this.seacher);

		Assert.assertEquals("O botão foi não identificado corretamente", tableName + expectedValue,
				captureElementXPath());
	}

	@Test(expected = ExecutionException.class)
	public void mustThrowErroIfNoElementFound() {
		configureWebSearcher("//button[contains(.,\"%s\")]", null);

		ClickCommand cmd = new ClickCommand();
		cmd.execute(this.driver, this.seacher);

	}

}
