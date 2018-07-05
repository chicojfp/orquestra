package io.breezil.orquestra.musico.commands;

import java.util.HashMap;
import java.util.Objects;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class SetVarCommand extends Command {
	
	private HashMap<String, String> map;

	public SetVarCommand() {
		super();
		this.map = new HashMap<>();
	}
	
	@Override
	public boolean execute(ExecutionContext context) {
		 context.setVariableByName(this.getItem(), this);
		 System.out.println(this.getValue());
		 return true;
	}
	
	private String key;
	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public void setValue(String value) {
		if (!Objects.isNull(this.key)) {
			this.map.put(this.key, value);
			this.key = null;
		} else {
			super.setValue(value);
		}
	}

	public String getKeyValue(String key) {
		return this.map.get(key);
	}

}
