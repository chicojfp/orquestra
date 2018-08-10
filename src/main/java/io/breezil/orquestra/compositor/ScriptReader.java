package io.breezil.orquestra.compositor;

public final class ScriptReader {
	private static ScriptReaderBase reader;

	public static ScriptReaderBase getReader() {
		return ScriptReader.reader;
	}

	public static ScriptReaderBase setReader(ScriptReaderBase reader) {
		ScriptReader.reader = reader;
		return ScriptReader.reader;
	}

}
