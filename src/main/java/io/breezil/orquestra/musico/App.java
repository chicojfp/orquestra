package io.breezil.orquestra.musico;

import org.openqa.selenium.firefox.FirefoxDriver;

import io.breezil.orquestra.CliOptions;
import io.breezil.orquestra.OrquestraCLI;
import io.breezil.orquestra.compositor.FileSystemReader;
import io.breezil.orquestra.compositor.Script;
import io.breezil.orquestra.instrumento.CommandRunner;
import io.breezil.orquestra.instrumento.ExecutionContext;
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
		new CommandParser(cliArgs.getGrammarFile()).parseCommands(script);
		
		ExecutionContext exec = new ExecutionContext();
		exec.addScript(script);

		script.getSteps().forEach(step -> {
			if (step.hasDepencies()) {
				Script newScript = new FileSystemReader().readTestFile(step.getCommand().getName());
				new CommandParser(cliArgs.getGrammarFile()).parseCommands(newScript);
				exec.addScript(newScript);
			}
		});
		
		exec.setDriver(new FirefoxDriver());
		exec.setSearcher(new WebElementSeacher(cliArgs.getObjectDefinitionFile()));
		CommandRunner.run(exec);

		System.out.println("Done!");
	}

	
}
