package io.breezil.orquestra.instrumento;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import io.breezil.orquestra.compositor.Script;
import io.breezil.orquestra.musico.commands.SetVarCommand;
import io.breezil.orquestra.musico.commands.WebElementFinder;

public class ExecutionContext {
	private Map<String, SetVarCommand> variables;
	private Map<String, Script> scripts;
	private Script mainScript;
	private WebDriver driver;
	private WebElementFinder searcher;
	private String errorImage;

	public ExecutionContext() {
		this.variables = new HashMap<>();
		this.scripts = new HashMap<>();
		instance = this;
	}
	
	public ExecutionContext(Script mainScript) {
		this();
		this.mainScript = mainScript;
		instance = this;
	}

	public ExecutionContext(WebDriver driver, WebElementFinder seacher) {
		this();
		this.driver = driver;
		this.searcher = seacher;
		instance = this;
	}
	
	protected static ExecutionContext instance;
	public static ExecutionContext getInstance() {
		return instance;
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

	public SetVarCommand getVariableByName(String name) {
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

	public WebElementFinder getSearcher() {
		return searcher;
	}

	public void setSearcher(WebElementFinder searcher) {
		this.searcher = searcher;
	}

	public void setErrorImage(String file) {
		this.errorImage = file;
	}

	public String getErrorImage() {
		return errorImage;
	}
	
	public SetVarCommand setVariableByName(String name, SetVarCommand value) {
		return this.variables.put(name, value);
	}
	
}
