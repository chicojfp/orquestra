package io.breezil.orquestra.musico.rules.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileSystemReader {

	public List<String> readTestFile(String fileName) {
		List<String> commands = new ArrayList<>();
		
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.forEach(e -> {
				commands.add(e);
				System.out.println(e);
			});
			stream.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
		
		return commands;
	}

}
