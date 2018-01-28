package io.breezil.orquestra.musico;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.breezil.orquestra.musico.commands.Command;
import io.breezil.orquestra.musico.commands.CommandParser;
import io.breezil.orquestra.musico.commands.WebElementInfo;
import io.breezil.orquestra.musico.commands.WebElementSeacher;
import io.breezil.orquestra.musico.rules.reader.FileSystemReader;

/**
 * Hello world!
 * 
 */
public class App 
{
    public static void main( String[] args )
    {
        WebDriver driver = new FirefoxDriver();
        WebElementSeacher webSeacher = new WebElementSeacher();
        List<WebElementInfo> webElementInfos = webSeacher.loadWEInfos("element_types.json");
        
        FileSystemReader reader = new FileSystemReader();
        List<String> scriptCommand = reader.readTestFile("TC-001.test");
        
        CommandParser parser = new CommandParser(webElementInfos);
        List<Command> commands = parser.parseCommands(scriptCommand);
        
        for (Command command : commands) {
			command.execute(driver);
		}
        
    		System.out.println("Done!");
    }
}
