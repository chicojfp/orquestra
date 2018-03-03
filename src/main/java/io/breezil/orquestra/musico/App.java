package io.breezil.orquestra.musico;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.breezil.orquestra.CliOptions;
import io.breezil.orquestra.OrquestraCLI;
import io.breezil.orquestra.compositor.FileSystemReader;
import io.breezil.orquestra.compositor.Script;
import io.breezil.orquestra.compositor.ScriptReader;
import io.breezil.orquestra.critico.HtmlReporter;
import io.breezil.orquestra.critico.OrquestraReporter;
import io.breezil.orquestra.instrumento.CommandRunner;
import io.breezil.orquestra.instrumento.ExecutionContext;
import io.breezil.orquestra.musico.commands.CommandParser;
import io.breezil.orquestra.musico.commands.WebElementFinder;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		
		CliOptions cliArgs = OrquestraCLI.parseArguments(args);

		if (!cliArgs.hasValidArgs()) {
			System.out.println("------------------------------------------------------------------------------------------ ");
			System.out.println("Usage: ");
			System.out.println("   java -Dwebdriver.gecko.driver=./lib/geckodriver                                         ");
			System.out.println("        -jar orquestra.jar --gf file_name.txt --sf script.tst --df object_definition.txt   ");
			System.out.println("------------------------------------------------------------------------------------------ ");
		}

		Script script = ScriptReader.setReader(new FileSystemReader("")).readScript(cliArgs.getScriptFile());
		new CommandParser(cliArgs.getGrammarFile()).parseCommands(script);
		
		
		// config
	      ChromeOptions options = new ChromeOptions();
//	      options.addArguments("headless");
//	      options.addArguments("window-size=1200x600");
//	      WebDriver driver = new ChromeDriver(options);
	      WebDriver driver = new FirefoxDriver();
		
		ExecutionContext exec = new ExecutionContext(script);
		exec.setDriver(driver);
		exec.setSearcher(new WebElementFinder(cliArgs.getObjectDefinitionFile()).setSearcherContext(driver));

		
		// executor
		try {
			CommandRunner.run(exec);
		} catch (Exception e) {
			System.err.println("====================================================================");
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.err.println("====================================================================");
		}

		
		OrquestraReporter.saveExecutionReport(new HtmlReporter(), exec);
		
		System.out.println("Done!");
	}

	

	
}
