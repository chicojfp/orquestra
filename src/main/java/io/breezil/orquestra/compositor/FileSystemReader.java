package io.breezil.orquestra.compositor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

public class FileSystemReader {

	public Script readTestFile(String fileName) {
		System.out.println("Reading script file: " + fileName);
		Script info = new Script();
		info.setName(fileName);
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
			stream.forEach(e -> {
				if (this.mustParseScriptLin(e)) {
					info.addStep(e);
				}
			});
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally { }
		
		return info;
	}

	private boolean mustParseScriptLin(String script) {
		String scriptLine = Objects.toString(script, "").trim(); 
		return !scriptLine.startsWith("#") && (scriptLine.length() > 0);
	}

}
