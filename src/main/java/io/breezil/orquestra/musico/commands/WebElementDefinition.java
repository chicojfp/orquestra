package io.breezil.orquestra.musico.commands;

import java.io.Serializable;

public class WebElementDefinition implements Serializable {
	private static final long serialVersionUID = -5860195197317250364L;
	
	private String name;
	private String[] xpaths;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getXpaths() {
		return xpaths;
	}
	public void setXpaths(String[] xpaths) {
		this.xpaths = xpaths;
	} 

}
