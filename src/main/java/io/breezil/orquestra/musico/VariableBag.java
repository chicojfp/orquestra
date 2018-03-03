package io.breezil.orquestra.musico;

import java.util.HashMap;
import java.util.Objects;

public class VariableBag {
	
	private static VariableBag instance;
	
	public static VariableBag getInstance() {
		if (Objects.isNull(VariableBag.instance)) {
			VariableBag.instance = new VariableBag();
		}
		return VariableBag.instance;
	}
	
	private java.util.Map<String, String> maps;
	
	private VariableBag() {
		this.maps = new HashMap<>();
	}

	public void addVariable(String name, String value) {
		this.maps.put(name, value);
	}
}
