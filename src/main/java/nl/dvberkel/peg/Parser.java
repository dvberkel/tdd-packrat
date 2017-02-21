package nl.dvberkel.peg;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface Parser {

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


class BootStrappedParser implements Parser {

    @Override
    public Ast parse(Path grammarPath) {
        return null;
    }
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