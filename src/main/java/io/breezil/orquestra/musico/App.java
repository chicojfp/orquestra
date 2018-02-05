package io.breezil.orquestra.musico;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.breezil.orquestra.CliOptions;
import io.breezil.orquestra.OrquestraCLI;
import io.breezil.orquestra.compositor.FileSystemReader;
import io.breezil.orquestra.compositor.Script;
import io.breezil.orquestra.compositor.ScriptStep;
import io.breezil.orquestra.musico.commands.CommandParser;
import io.breezil.orquestra.musico.commands.WebElementSeacher;

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

		Script script = new FileSystemReader().readTestFile(cliArgs.getScriptFile());

		CommandParser parser = new CommandParser(cliArgs.getGrammarFile());
		parser.parseCommands(script);

		WebDriver driver = new FirefoxDriver();
		WebElementSeacher webSeacher = new WebElementSeacher(cliArgs.getObjectDefinitionFile());
		for (ScriptStep step : script.getSteps()) {
			System.out.println("Executando o script: " + step.getScript());
			boolean success = step.getCommand().execute(driver, webSeacher);
			if (!success) {
				System.out.println("Erro executting script: " + step.getScript());
			}
		}

		System.out.println("Done!");
	}
}
