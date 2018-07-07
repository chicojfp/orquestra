package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import io.breezil.orquestra.instrumento.ExecutionContext;

public class FilterCommand extends ComplexCommand {
	private String order;
	private String partialValue;
	
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
		filter = appendIfNotNull(filter, order, order);
		filter = appendIfNotNull(filter, value, value);
		filter = appendIfNotNull(filter, partialValue, "contains(.,\""+ partialValue + "\")");
		return super.updateXPathFilter(String.format(xpath, filter));
	}

	private String appendIfNotNull(String filter, String valueToCheck, String valueToAppend) {
		if (!Objects.isNull(valueToCheck)) {
			if (filter.length() > 0) {
				filter += " and ";
			}
			filter += valueToAppend;
		}
		return filter;
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

	public String getPartialValue() {
		return partialValue;
	}

	public void setPartialValue(String partialValue) {
		this.partialValue = partialValue;
	}
	
}
