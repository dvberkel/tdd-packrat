package nl.dvberkel.integration;

import nl.dvberkel.peg.Ast;
import nl.dvberkel.peg.Parser;
import nl.dvberkel.peg.Transformer;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class PegTest {
    @Test
    public void shouldParseFromAGrammer() {
        Parser parser = Parser.from("test/resources/grammars/PegTest.peg");

        Ast ast = parser.parse(Paths.get("test/resources/input/PegTest.txt"));

        assertThat(ast, is(nullValue()));
    }
}