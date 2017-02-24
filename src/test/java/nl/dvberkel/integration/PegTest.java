package nl.dvberkel.integration;

import nl.dvberkel.peg.Ast;
import nl.dvberkel.peg.ParseResult;
import nl.dvberkel.peg.Parser;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class PegTest {
    @Ignore
    @Test
    public void shouldParseFromAGrammer() {
        Parser parser = Parser.from("src/test/resources/grammars/PegTest.peg");

        ParseResult<Ast> result = parser.parse("src/test/resources/input/PegTest.txt");

        assertThat(result, is(not(nullValue())));
        assertThat(result.isSuccess(), is(false));
    }
}