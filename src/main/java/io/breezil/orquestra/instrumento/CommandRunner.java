package io.breezil.orquestra.instrumento;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import io.breezil.orquestra.compositor.Script;
import io.breezil.orquestra.compositor.ScriptStep;
import io.breezil.orquestra.exception.ExecutionException;

public class CommandRunner {

	public static void run(Script script, ExecutionContext context) {
		for (ScriptStep step : script.getSteps()) {
			System.out.println("Executando o passo: " + step.getScript());
			try {
				boolean success = step.getCommand().execute(context);
				step.setSuccessExecution(success);
			} catch (ExecutionException ee) {
				String file = saveScreenShot(context);
				context.setErrorImage(file);
				try {
					context.getDriver().close();
				} catch (Exception e) {
				}
				throw ee;
			}
		}
	}

	private static String saveScreenShot(ExecutionContext context) {
		try {
			WebDriver augmentedDriver = new Augmenter().augment(context.getDriver());
			File source = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			String path = "./" + source.getName();
			FileUtils.copyFile(source, new File(path));
			return path;
		} catch (Exception e) {
		}
		return "";
	}

	public static void run(ExecutionContext context) {
		CommandRunner.run(context.getScript(), context);
	}

}
