package io.breezil.orquestra.compositor;

import io.breezil.orquestra.musico.commands.CommandParser;

public final class ScriptReader {
	private static ScriptReaderBase reader;
	private static CommandParser parser;

	public static ScriptReaderBase getReader() {
		return ScriptReader.reader;
	}

	public static ScriptReaderBase setReader(ScriptReaderBase reader) {
		ScriptReader.reader = reader;
		return ScriptReader.reader;
	}

}
