package io.breezil.orquestra.musico.commands;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.breezil.orquestra.exception.ExecutionException;
import io.breezil.orquestra.instrumento.ExecutionContext;

@RunWith(MockitoJUnitRunner.class)
public class CommandTest extends BaseTest {

	@Override
	@Before
	public void configureEnviroment() {
		this.driver = Mockito.mock(FirefoxDriver.class);
		this.seacher = Mockito.mock(WebElementFinder.class);
	}

	@Test
	public void mustReplaceElementName() {
		String expectedValue = "//button[contains(.,\"Login\")]";
		configureWebSearcher("//button[contains(.,\"%s\")]", expectedValue);

		ClickCommand cmd = new ClickCommand();
		cmd.setName("Login");
		cmd.setItem("Button");
		cmd.setValue("XXX");
		cmd.execute(new ExecutionContext(this.driver, this.seacher));

		List<String> values = capture_findWebElement_List_String();

		Assert.assertEquals("Não foi retornado apenas um elemento na pesquisa.", 1, values.size());
		Assert.assertEquals("O botão foi não identificado corretamente", expectedValue, values.get(0));
	}

	@Test
	public void mustAddSerachPathIfDefined() {
		String tableName = "//table[.==\"Table name\"]";
		String expectedValue = "//button[contains(.,\"Login\")]";
		configureWebSearcher("//button[contains(.,\"%s\")]", tableName + expectedValue);

		ClickCommand cmd = new ClickCommand();
		cmd.setxPathModification(tableName);
		cmd.setName("Login");
		cmd.execute(new ExecutionContext(this.driver, this.seacher));

		List<String> values = capture_findWebElement_List_String();

		Assert.assertEquals("Não foi retornado apenas um elemento na pesquisa.", 1, values.size());
		Assert.assertEquals("O botão foi não identificado corretamente", tableName + expectedValue, values.get(0));
	}

	@Test(expected = ExecutionException.class)
	public void mustThrowErroIfNoElementFound() {
		configureWebSearcher("//button[contains(.,\"%s\")]", null);

		ClickCommand cmd = new ClickCommand();
		cmd.execute(new ExecutionContext(this.driver, this.seacher));

	}

}
