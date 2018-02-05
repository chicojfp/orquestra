package io.breezil.orquestra.instrumento;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import io.breezil.orquestra.compositor.Script;
import io.breezil.orquestra.compositor.ScriptStep;

public class CommandRunner {
	
	public static void run(Script script, ExecutionContext context) {
		for (ScriptStep step : script.getSteps()) {
			System.out.println("Executando o script: " + step.getScript());
			boolean success = step.getCommand().execute(context.getDriver(), context.getSearcher());
			if (step.hasDepencies()) {
				Script innerScript = context.getScriptByName(step.getCommand().getName());
				CommandRunner.run(innerScript, context);
			}
			if (!success) {
				String errorMsg = "Error running script: " + step.getScript();
				System.out.println(errorMsg);
				saveScreenShot(context);
				context.getDriver().close();
				throw new RuntimeException(errorMsg);
			}
		}
	}

	private static void saveScreenShot(ExecutionContext context) {
		WebDriver augmentedDriver = new Augmenter().augment(context.getDriver()); 
		File source = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
		String path = "./target/screenshots/" + source.getName();
		try {
			FileUtils.copyFile(source, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void run(ExecutionContext context) {
		CommandRunner.run(context.getScript(), context);
	}

}
