package io.breezil.orquestra.musico.commands;

import org.openqa.selenium.WebDriver;

public class GoToURLCommand extends Command {
	public String url;

	public GoToURLCommand() {
		super();
	}
	
	@Override
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		driver.get(this.url);
		
//		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"versaoSistema\"]")));
		return true;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
}
