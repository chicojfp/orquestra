package io.breezil.orquestra.musico.commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ca.uqac.lif.bullwinkle.ParseNode;

public class CommandBuilder {
	private Command building;
	
	public CommandBuilder build(String commandName) {
		building = buildConcreteClass(commandName);
		
		return this;
	}
	
	public Command fillParameters(ParseNode node) {
		recursiveFillProperties(this.building, node);
		
		return this.building;
	}

	private String recursiveFillProperties(Command building, ParseNode node) {
		if (node == null) return null;
		if (node.getValue() == null) {
			return node.getToken();
		}
		if (node.getToken().equals("<exp>")) {
			node = node.getChildren().get(0);
			Command cmd = new CommandBuilder().build(node.getToken().substring(1, node.getToken().length() -1)).fillParameters(node);
			((FilterCommand) building).setActualCommand(cmd);
			return null;
		}
		for (ParseNode child : node.getChildren()) {
			String value = recursiveFillProperties(building, child);
			String propertyName = node.getToken().substring(1, node.getToken().length() - 1);
			definePropertieValue(building, propertyName, value);
		}
		return null;
	}

	private void definePropertieValue(Command cmd, String name, String value) {
		try {
//			cmd.getClass().getField(name).set(cmd, value);
			Method m = cmd.getClass().getMethod("set" + name.toUpperCase().charAt(0) + name.substring(1), String.class);
			m.invoke(cmd, value);
		} catch (IllegalArgumentException | NoSuchMethodException | SecurityException | 
				IllegalAccessException | InvocationTargetException e) {
//			e.printStackTrace();
		}
		
	}

	private Command buildConcreteClass(String commandName) {
		Command cmd = null;
		try {
			Class<?> clazz = Class.forName(CommandBuilder.class.getPackage().getName() + "." + commandName);
			Constructor<?> ctor = clazz.getConstructor();
			cmd = (Command) ctor.newInstance(new Object[] { });
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException |
				ClassNotFoundException e) {
			e.printStackTrace();
		}
		return cmd;
	}

}
