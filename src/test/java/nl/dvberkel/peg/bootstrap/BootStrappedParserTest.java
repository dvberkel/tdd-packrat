package nl.dvberkel.peg.bootstrap;

import nl.dvberkel.peg.Ast;
import nl.dvberkel.peg.Parser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import static nl.dvberkel.peg.bootstrap.BootStrappedParser.characterClass;
import static nl.dvberkel.peg.bootstrap.BootStrappedParser.definition;
import static nl.dvberkel.peg.bootstrap.BootStrappedParser.grammar;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class BootStrappedParserTest {
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"test/resources/grammars/simple.peg", grammar(definition("a", characterClass()))});
        return data;
    }

    private final String grammarLocation;
    private final Ast expectedAst;

    public BootStrappedParserTest(String grammarLocation, Ast expectedAst) {
        this.grammarLocation = grammarLocation;
        this.expectedAst = expectedAst;
    }

    @Test
    public void shouldParseSimpleGrammar() {
        Parser parser = new BootStrappedParser();

        Ast ast = parser.parse(Paths.get(grammarLocation));

        assertThat(ast, is(expectedAst));

    }
}
