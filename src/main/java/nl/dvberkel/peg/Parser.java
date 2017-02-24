package nl.dvberkel.peg;

import nl.dvberkel.peg.bootstrap.BootStrappedParser;

public interface Parser<T extends Ast> {

    static <S extends Ast> Parser<S> from(String grammarPath) {
        Parser bootStrapped = new BootStrappedParser();
        ParseResult<S> result = bootStrapped.parse(grammarPath);
        if (result.isSuccess()) {
            S ast = ((Success<S>) result).unpack();
            Transformer<Ast, Parser> createParser = new CreateParser();
            return createParser.transform(ast);
        } else {
            throw new IllegalStateException("unable to parse");
        }
    }

    ParseResult<? extends Ast> parse(String grammarPath);
}

class CreateParser implements Transformer<Ast, Parser> {

    @Override
    public Parser transform(Ast ast) {
        return new PegParser();
    }
}

class PegParser implements Parser<Ast> {

    @Override
    public ParseResult<Ast> parse(String grammarPath) {
        return null;
    }
}