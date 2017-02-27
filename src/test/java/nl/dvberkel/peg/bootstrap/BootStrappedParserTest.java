package nl.dvberkel.peg.bootstrap;

import nl.dvberkel.peg.Ast;
import nl.dvberkel.peg.ParseResult;
import nl.dvberkel.peg.Parser;
import nl.dvberkel.peg.Success;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collection;

import static nl.dvberkel.peg.bootstrap.BootStrappedParser.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class BootStrappedParserTest {
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        data.add(new Object[]{"src/test/resources/grammars/a-empty-characterclass.peg", grammar(definition("a", characterClass()))});
        data.add(new Object[]{"src/test/resources/grammars/b-empty-characterclass.peg", grammar(definition("b", characterClass()))});
        data.add(new Object[]{"src/test/resources/grammars/ab-empty-characterclass.peg", grammar(definition("ab", characterClass()))});
        data.add(new Object[]{"src/test/resources/grammars/two-rules-empty-characterclass.peg", grammar(
                definition("a", characterClass()),
                definition("b", characterClass())
        )});
        data.add(new Object[]{"src/test/resources/grammars/a-empty-literal.peg", grammar(definition("a", literal()))});
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

        ParseResult<Ast> result = parser.parse(grammarPath);

        assertTrue(result.isSuccess());
        assertThat(((Success<Ast>)result).unpack(), is(expectedAst));

    }
}
