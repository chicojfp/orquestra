package io.breezil.orquestra.musico.commands;

import java.util.Objects;

import org.openqa.selenium.WebDriver;

public class FilterCommand extends Command {
	private Command actualCommand;
	private String order;
	
	public FilterCommand() {
		super();
	}

	public Command getActualCommand() {
		return actualCommand;
	}

	public void setActualCommand(Command actualCommand) {
		this.actualCommand = actualCommand;
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
	public boolean execute(WebDriver driver, WebElementSeacher seacher) {
		WebElementInfo elInfo = seacher.findItem(this.getItem());
		for (String xpath : elInfo.getXpaths()) {
//			String previusMod = Objects.toString(this.getxPathModification(), "");
			this.actualCommand.setxPathModification(updateXPathFilter(xpath)); 
			boolean success = this.actualCommand.execute(driver, seacher);
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
