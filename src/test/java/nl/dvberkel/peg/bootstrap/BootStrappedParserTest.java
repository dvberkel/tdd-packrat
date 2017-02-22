package nl.dvberkel.peg.bootstrap;

import nl.dvberkel.peg.Ast;
import nl.dvberkel.peg.Parser;
import org.junit.Test;

import java.nio.file.Paths;

import static nl.dvberkel.peg.bootstrap.BootStrappedParser.characterClass;
import static nl.dvberkel.peg.bootstrap.BootStrappedParser.definition;
import static nl.dvberkel.peg.bootstrap.BootStrappedParser.grammar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BootStrappedParserTest {
    @Test
    public void shouldParseSimpleGrammar() {
        Parser parser = new BootStrappedParser();

        Ast ast = parser.parse(Paths.get("test/resources/grammars/simple.peg"));

        assertThat(ast, is(grammar(definition("a", characterClass()))));

    }
}
