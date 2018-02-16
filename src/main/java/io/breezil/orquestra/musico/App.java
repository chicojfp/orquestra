package io.breezil.orquestra.musico;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.openqa.selenium.firefox.FirefoxDriver;

import com.google.gson.Gson;

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
				step.setInnerScript(newScript);
			}
		});
		
		exec.setDriver(new FirefoxDriver());
		exec.setSearcher(new WebElementSeacher(cliArgs.getObjectDefinitionFile()));

		try {
			CommandRunner.run(exec);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		try {
			String json = new Gson().toJson(exec.getScript());
			
			String fileName = "applied_template.html";

			try {
				BufferedWriter writer = Files.newBufferedWriter(Paths.get("asfasfasd.html"));
				//read file into stream, try-with-resources
				try (Stream<String> stream = Files.lines(Paths.get("template.html"))) {
					
					stream.forEach( (line) -> {
						try {
							writer.newLine();
							if (line.contains("json_result_content")) {
								String newJson = json.replace("\\\"" , "\\\'");
								System.out.println(newJson);
								System.out.println(json);
								line = line.replace("json_result_content", newJson);
							}
							writer.write(line);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			Files.write(Paths.get("output.json"), json.getBytes());
			System.out.println(json);
		} catch (IOException e1) { }
		
		System.out.println("Done!");
	}

	
}
