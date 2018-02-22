package io.breezil.orquestra.compositor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileSystemReader extends ScriptReaderBase {
	private String basePath;
	
	public FileSystemReader(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public Script readScript(String scriptID) {
		Path filePath = Paths.get(this.basePath, scriptID);
		System.out.println("Reading script file: " + filePath.toString());
		Script info = new Script();
		info.setName(scriptID);
		
		try (Stream<String> stream = Files.lines(filePath, StandardCharsets.UTF_8)) {
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

}
