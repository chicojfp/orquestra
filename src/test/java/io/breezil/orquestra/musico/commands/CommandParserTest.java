package io.breezil.orquestra.musico.commands;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import io.breezil.orquestra.compositor.Script;
import io.breezil.orquestra.compositor.ScriptStep;

@RunWith(MockitoJUnitRunner.class)
public class CommandParserTest {
	private CommandParser parser;

	@Before
	public void prepareParseEnviroment() {
		this.parser = new CommandParser("./samples/web-grammar.bnf");
	}

	@Test
	public void mustParseGoCommand() {
		String url = "http://www.google.com";
		Script script = new Script();
		script.addStep("Acesse " + url);
		script = parser.parseCommands(script);

		ScriptStep step = script.getSteps().get(0);

		GoToURLCommand go = (GoToURLCommand) step.getCommand();
		Assert.assertNotNull("O comando não foi criado", go);

		Assert.assertEquals("O URL não definida corretamente", url, go.getUrl());
	}

	@Test
	public void mustParseClickCommand() {
		String name = "Login";
		String item = "botão";
		Script script = new Script();
		script.addStep(String.format("Clique no %s \"%s\" ", item, name));
		script = parser.parseCommands(script);

		ScriptStep step = script.getSteps().get(0);

		Command cmd = step.getCommand();
		Assert.assertNotNull("O comando não foi criado", cmd);

		validateClickCommand(name, item, cmd);
	}

	private void validateClickCommand(String name, String item, Command cmd) {
		Assert.assertEquals("A descrição do item não foi configurado corretamente.", name, cmd.getName());
		Assert.assertEquals("O tipo de item não foi definido.", item, cmd.getItem());
	}

	@Test
	public void mustComplexCommandFilterAndClickCommand() {
		String name = "Login";
		String item = "botão";
		Script script = new Script();
		script.addStep(String.format("Na tabela, Na linha 1, Clique no %s \"%s\" ", item, name));
		script = parser.parseCommands(script);

		ScriptStep step = script.getSteps().get(0);

		FilterCommand cmd = (FilterCommand) step.getCommand();
		FilterCommand innerFilterCmd = (FilterCommand) cmd.getActualCommand();
		Command actualCmd = innerFilterCmd.getActualCommand();

		Assert.assertNotNull("O comando não foi criado", cmd);
		Assert.assertNotNull("O comando interno não foi criado", innerFilterCmd);
		Assert.assertNotNull("O segundo comando interno não foi criado", actualCmd);

		Assert.assertEquals("O primeiro filtro não foi aplicado corretamente.", "tabela", cmd.getItem());
		Assert.assertEquals("O segundo filtro não foi aplicado corretamente.", "linha", innerFilterCmd.getItem());
		validateClickCommand(name, item, actualCmd);
	}

}
