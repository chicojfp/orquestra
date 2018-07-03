package io.breezil.orquestra.critico;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.google.gson.Gson;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class HtmlReporter implements OrquestraBaseReporter {

	@Override
	public void export(ExecutionContext exec) {
		try {
			String json = new Gson().toJson(exec.getScript());
			
			String fileName = "applied_template.html";

			try {
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName));
				//read file into stream, try-with-resources
				try (Stream<String> stream = Files.lines(Paths.get("resources/templates/template.html"))) {
					
					stream.forEach( (line) -> {
						try {
							writer.newLine();
							if (line.contains("json_result_content")) {
								String newJson = json.replace("\\\"" , "\\\'");
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
		} catch (IOException e1) { }		
	}

}
