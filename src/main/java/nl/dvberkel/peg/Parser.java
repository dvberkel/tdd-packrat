package nl.dvberkel.peg;

import java.nio.file.Path;

public interface Parser {

    static Parser from(String grammarPath) {
        Parser bootStrapped = new BootStrappedParser();
        Ast ast = bootStrapped.parse(grammarPath);
        Transformer<Ast, Parser> createParser = new CreateParser();
        return createParser.transform(ast);
    }

    Ast parse(String grammarPath);
}


class BootStrappedParser implements Parser {

    @Override
    public Ast parse(String grammarPath) {
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
    public Ast parse(String grammarPath) {
        return null;
    }
}