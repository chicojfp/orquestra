package io.breezil.orquestra.musico;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

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

		FileSystemReader reader = new FileSystemReader();
		List<String> scriptCommand = reader.readTestFile("TC-001.test");

		CommandParser parser = new CommandParser("Simple-Math.bnf");
		List<Command> commands = parser.parseCommands(scriptCommand);

		WebDriver driver = new FirefoxDriver();
		WebElementSeacher webSeacher = new WebElementSeacher("element_types.json");
		for (Command command : commands) {
			command.execute(driver, webSeacher);
		}

		System.out.println("Done!");
	}
}
