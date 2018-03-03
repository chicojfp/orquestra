package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class FilterCommand extends ComplexCommand {
	private String order;
	
	public FilterCommand() {
		super();
	}
	
	@Override
	public String getxPathModification() {
		return xPathModification;
	}
	
	@Override
	public String updateXPathFilter(String xpath) {
		String filter = "";
		if (!Objects.isNull(order)) {
			filter += order;
		}
		if (!Objects.isNull(value)) {
			if (filter.length() > 0) {
				filter += " and ";
			}
			filter += "contains(.,\""+ value + "\")";
		}
		return super.updateXPathFilter(String.format(xpath, filter));
	}
	
	@Override
	public boolean execute(ExecutionContext context) {
		WebElementDefinition elInfo = context.getSearcher().findObjectDefinition(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
//			String previusMod = Objects.toString(this.getxPathModification(), "");
			this.getActualCommand().setxPathModification(updateXPathFilter(xpath)); 
			boolean success = this.getActualCommand().execute(context);
			if (success) return success;
		}

		return false;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
}
