package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebDriver;

public class GoToURLCommand extends Command {
	private String url;

	public GoToURLCommand(String url) {
		super("");
		this.url = url;
	}
	
	@Override
	public boolean execute(WebDriver driver) {
		driver.get(this.url);
		
//		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"versaoSistema\"]")));
		return true;
	}

}
