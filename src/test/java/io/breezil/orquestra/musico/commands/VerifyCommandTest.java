package io.breezil.orquestra.musico.commands;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.WebElement;

import io.breezil.orquestra.exception.ExecutionException;

@RunWith(MockitoJUnitRunner.class)
public class VerifyCommandTest extends BaseTest {
	
	@Test
	public void mustCheckIfADisplayedElementIsDisplayed() {
		String xpath = "//button";
		WebElement we = configureWebSearcher(xpath, xpath);
		Mockito.doReturn(true).when(we).isDisplayed();
		VerifyCommand cmd = createVerifyCommand();
		cmd.setPropIsDisplayed("displayed");
		
		boolean isOk = cmd.execute(this.driver, this.seacher);
		
		Assert.assertTrue("Um elemento não visível foi detectado como visível", isOk);
	}
	
	@Test
	public void mustCheckIfAEnabledElementIsEnabled() {
		String xpath = "//button";
		WebElement we = configureWebSearcher(xpath, xpath);
		Mockito.doReturn(true).when(we).isEnabled();
		VerifyCommand cmd = createVerifyCommand();
		cmd.setPropIsEnabled("enabled");
		
		boolean isOk = cmd.execute(this.driver, this.seacher);
		
	}
	
	@Test(expected = ExecutionException.class)
	public void mustThrowErrorIfVerifyADisableElementAsEnabled() {
		String xpath = "//button";
		WebElement we = configureWebSearcher(xpath, xpath);
		Mockito.doReturn(false).when(we).isEnabled();
		VerifyCommand cmd = createVerifyCommand();
		cmd.setPropIsEnabled("enabled");
		
		boolean isOk = cmd.execute(this.driver, this.seacher);
		
		Assert.assertTrue("O elemento desabilitado foi detectado como habilitado.", isOk);
	}
	
	@Test(expected = ExecutionException.class)
	public void mustThrowErrorIfVerifyAHiddenElementAsDisplayed() {
		String xpath = "//button";
		WebElement we = configureWebSearcher(xpath, xpath);
		Mockito.doReturn(false).when(we).isDisplayed();
		VerifyCommand cmd = createVerifyCommand();
		cmd.setPropIsDisplayed("displayed");
		
		boolean isOk = cmd.execute(this.driver, this.seacher);
		
		Assert.assertTrue("O elemento invisível foi detectado como visível.", isOk);
	}

	private VerifyCommand createVerifyCommand() {
		VerifyCommand cmd = new VerifyCommand();
		cmd.setItem("botão");
		cmd.setName("Login");
		return cmd;
	}

}
