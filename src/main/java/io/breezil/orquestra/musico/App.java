package io.breezil.orquestra.musico;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.breezil.orquestra.CliOptions;
import io.breezil.orquestra.OrquestraCLI;
import io.breezil.orquestra.compositor.FileSystemReader;
import io.breezil.orquestra.musico.commands.Command;
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
			System.out.println("     java -jar orquestra.jar --gf file_name.txt --sf script.tst --df object_definition.txt ");
			System.out.println("------------------------------------------------------------------------------------------ ");
		}

		FileSystemReader reader = new FileSystemReader();
		List<String> scriptCommand = reader.readTestFile(cliArgs.getScriptFile());

		CommandParser parser = new CommandParser(cliArgs.getGrammarFile());
		List<Command> commands = parser.parseCommands(scriptCommand);

		WebDriver driver = new FirefoxDriver();
		WebElementSeacher webSeacher = new WebElementSeacher(cliArgs.getObjectDefinitionFile());
		for (Command command : commands) {
			command.execute(driver, webSeacher);
		}

		System.out.println("Done!");
	}
}
