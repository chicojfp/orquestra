package io.breezil.orquestra.critico;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class OrquestraReporter {
	
	public static void saveExecutionReport(OrquestraBaseReporter reporter, ExecutionContext exec) {
		reporter.export(exec);
	}

}
