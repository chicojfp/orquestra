package io.breezil.orquestra.musico.commands;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.breezil.orquestra.instrumento.ExecutionContext;

@RunWith(MockitoJUnitRunner.class)
public class FilterCommandTest extends BaseTest {

	@Test
	public void mustUpdateActualCommandXPathWithOrder() {
		FilterCommand filter = new FilterCommand();
		filter.setOrder("1");
		SelectCommand select = configureDefaultFilter(filter, "//table//tr[1]");
		filter.execute(new ExecutionContext(this.driver, this.seacher));

		Assert.assertEquals("O XPath não foi modificado corretamente.", select.getxPathModification(),
				"//table//tr[1]");
	}

	@Test
	public void mustUpdateActualCommandXPathWithValue() {
		String expectedValue = "//table//tr[contains(.,\"123ABC\")]";
		FilterCommand filter = new FilterCommand();
		filter.setPartialValue("123ABC");
		SelectCommand select = configureDefaultFilter(filter, expectedValue);
		filter.execute(new ExecutionContext(this.driver, this.seacher));

		Assert.assertEquals("O XPath não foi modificado corretamente.", select.getxPathModification(), expectedValue);
	}

	@Test
	public void mustUpdateActualCommandXPathWithValueAndOrder() {
		String expectedValue = "//table//tr[3 and contains(.,\"123ABC\")]";
		FilterCommand filter = new FilterCommand();
		filter.setPartialValue("123ABC");
		filter.setOrder("3");
		SelectCommand select = configureDefaultFilter(filter, expectedValue);
		filter.execute(new ExecutionContext(this.driver, this.seacher));

		Assert.assertEquals("O XPath não foi modificado corretamente.", select.getxPathModification(), expectedValue);
	}

	private SelectCommand configureDefaultFilter(FilterCommand filter, String expectedFilter) {
		SelectCommand select = new SelectCommand();
		select.setName("profile");
		filter.setActualCommand(select);

		configureWebSearcher("//table//tr[%s]", expectedFilter);

		return select;
	}

}
