package nl.dvberkel.peg.bootstrap;

import nl.dvberkel.peg.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static nl.dvberkel.peg.ParseResult.success;
import static nl.dvberkel.peg.ParseResult.unpack;

public class BootStrappedParser implements Parser {
    public static Ast grammar(Definition first, Definition... rest) {
        Collection<Definition> definitions = new ArrayList<>();
        definitions.add(first);
        definitions.addAll(Arrays.asList(rest));
        return new Grammar(definitions);
    }

    public static Definition definition(String identifier, Expression expression) {
        return new Definition(identifier, expression);
    }

    public static CharacterClass characterClass() {
        return new CharacterClass();
    }

    @Override
    public ParseResult<Ast> parse(String grammarPath) {
        Tokenizer tokenizer;
        try {
            tokenizer = new Tokenizer(grammarPath);
        } catch (FileNotFoundException e) {
            return Failure.instance;
        }
        return parseGrammer(tokenizer);
    }

    private ParseResult<Ast> parseGrammer(Tokenizer tokenizer) {
        ParseResult<Collection<Definition>> result = parseDefinitions(tokenizer);
        if (result.isSuccess()) {
            return success(new Grammar(unpack(result)));
        } else {
            return Failure.instance;
        }
    }

    private ParseResult<Collection<Definition>> parseDefinitions(Tokenizer tokenizer) {
        Collection<Definition> definitions = new ArrayList<>();
        ParseResult<Definition> result = parseDefinition(tokenizer);
        if (result.isSuccess()) {
            do {
                definitions.add(unpack(result));
                result = parseDefinition(tokenizer);
            } while (result.isSuccess());
            return success(definitions);
        } else {
            return Failure.instance;
        }
    }

    private ParseResult<Definition> parseDefinition(Tokenizer tokenizer) {
        ParseResult<String> identifierResult = parseIdentifier(tokenizer);
        if (identifierResult.isSuccess()) {
            String identifier = unpack(identifierResult);
            ParseResult<String> arrowResult = parseRightArrow(tokenizer);
            if (arrowResult.isSuccess()) {
                ParseResult<Expression> expressionResult = parseExpression(tokenizer);
                if (expressionResult.isSuccess()) {
                    Expression expression = unpack(expressionResult);
                    return success(definition(identifier, expression));
                } else {
                    return Failure.instance;
                }
            } else {
                return Failure.instance;
            }
        } else {
            return Failure.instance;
        }
    }

    private ParseResult<String> parseIdentifier(Tokenizer tokenizer) {
        StringBuilder builder = new StringBuilder();
        String character = tokenizer.peek();
        if (character.matches("[a-zA-Z_]")) {
            do {
                builder.append(tokenizer.read());
                character = tokenizer.peek();
            } while (character.matches("[a-zA-Z_0-9]"));
            return success(builder.toString());
        } else {
            return Failure.instance;
        }
    }

    private ParseResult<String> parseRightArrow(Tokenizer tokenizer) {
        return success("<-");
    }

    private ParseResult<Expression> parseExpression(Tokenizer tokenizer) {
        return success(characterClass());
    }
}

class Tokenizer {
    private static final int BUFFER_FILL_AMOUNT = 10;
    private final Reader reader;
    private final List<String> buffer;
    private int index;

    public Tokenizer(String grammarPath) throws FileNotFoundException {
        this.reader = new FileReader(new File(grammarPath));
        buffer = new ArrayList<String>();
        index = 0;

    }

    public String read() {
        if (bufferDepleted()) {
            fillBuffer();
        }
        return buffer.get(index++);
    }

    public String peek() {
        if (bufferDepleted()) {
            fillBuffer();
        }
        return buffer.get(index);
    }

    private boolean bufferDepleted() {
        return index >= buffer.size();
    }

    private void fillBuffer() {
        int character;
        for (int charactersRead = 0; charactersRead < BUFFER_FILL_AMOUNT; charactersRead++)
        try {
            character = reader.read();
            if (character != -1) {
                buffer.add(String.valueOf((char) character));
            } else {
                break;
            }
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}

class Grammar implements Ast {
    private final Collection<Definition> definitions;

    public Grammar(Collection<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Grammar grammar = (Grammar) o;

        return definitions.equals(grammar.definitions);
    }

    @Override
    public int hashCode() {
        return definitions.hashCode();
    }
}

class Definition {
    private final String identifier;
    private final Expression characterClass;

    public Definition(String identifier, Expression characterClass) {
        this.identifier = identifier;
        this.characterClass = characterClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Definition that = (Definition) o;

        if (!identifier.equals(that.identifier)) return false;
        return characterClass.equals(that.characterClass);
    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + characterClass.hashCode();
        return result;
    }
}

interface Expression {}

class CharacterClass implements Expression {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return 37;
    }
}