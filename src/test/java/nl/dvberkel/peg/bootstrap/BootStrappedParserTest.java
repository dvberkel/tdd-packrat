package nl.dvberkel.peg.bootstrap;

import nl.dvberkel.peg.Ast;
import nl.dvberkel.peg.Parser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

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
        data.add(new Object[]{"src/test/resources/grammars/simple-a.peg", grammar(definition("a", characterClass()))});
        data.add(new Object[]{"src/test/resources/grammars/simple-b.peg", grammar(definition("b", characterClass()))});
        return data;
    }

    private final String grammarPath;
    private final Ast expectedAst;

    public BootStrappedParserTest(String grammarPath, Ast expectedAst) {
        this.grammarPath = grammarPath;
        this.expectedAst = expectedAst;
    }

    @Test
    public void shouldParseSimpleGrammar() {
        Parser parser = new BootStrappedParser();

        Ast ast = parser.parse(grammarPath);

        assertThat(ast, is(expectedAst));

    }
}
