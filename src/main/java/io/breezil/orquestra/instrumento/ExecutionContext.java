package io.breezil.orquestra.instrumento;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import io.breezil.orquestra.compositor.Script;
import io.breezil.orquestra.musico.commands.WebElementSeacher;

public class ExecutionContext {
	private Map<String, String> variables;
	private Map<String, Script> scripts;
	private Script mainScript;
	private WebDriver driver;
	private WebElementSeacher searcher;
	private String errorImage;

	public ExecutionContext() {
		this.variables = new HashMap<>();
		this.scripts = new HashMap<>();
	}
	
	public ExecutionContext(Script mainScript) {
		super();
		this.mainScript = mainScript;
	}

	public void addScript(Script script) {
		this.scripts.put(script.getName(), script);
		if (Objects.isNull(this.mainScript)) {
			this.mainScript = script;
		}
	}

	public Script getScriptByName(String name) {
		return this.scripts.get(name);
	}

	public String getVariableByName(String name) {
		return this.variables.get(name);
	}

	public Script getScript() {
		return this.mainScript;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	public WebElementSeacher getSearcher() {
		return searcher;
	}

	public void setSearcher(WebElementSeacher searcher) {
		this.searcher = searcher;
	}

	public void setErrorImage(String file) {
		this.errorImage = file;
	}
	
	

}
