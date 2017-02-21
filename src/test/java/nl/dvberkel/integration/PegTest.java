package nl.dvberkel.integration;

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

interface Parser {

    public static Parser from(String grammarLocation) {
        Path path = Paths.get(grammarLocation);
        return from(path);
    }

    public static Parser from(Path grammarPath) {
        Parser bootStrapped = new BootStrappedParser();
        Ast ast = bootStrapped.parse(grammarPath);
        Transformer<Ast, Parser> createParser = new CreateParser();
        return createParser.transform(ast);
    }

    Ast parse(Path grammarPath);
}

interface Ast {

}

class BootStrappedParser implements Parser {

    @Override
    public Ast parse(Path grammarPath) {
        return null;
    }
}

interface Transformer<Input, Output> {
    Output transform(Input input);
}

class CreateParser implements Transformer<Ast, Parser> {

    @Override
    public Parser transform(Ast ast) {
        return new PegParser();
    }
}

class PegParser implements Parser {

    @Override
    public Ast parse(Path grammarPath) {
        return null;
    }
}