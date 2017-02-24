package nl.dvberkel.peg.bootstrap;

import nl.dvberkel.peg.Ast;
import nl.dvberkel.peg.Failure;
import nl.dvberkel.peg.ParseResult;
import nl.dvberkel.peg.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static nl.dvberkel.peg.ParseResult.success;

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
        return success(parseGrammer(tokenizer));
    }

    private Grammar parseGrammer(Tokenizer tokenizer) {
        Collection<Definition> definitions = parseDefinitions(tokenizer);
        return new Grammar(definitions);
    }

    private Collection<Definition> parseDefinitions(Tokenizer tokenizer) {
        Collection<Definition> definitions = new ArrayList<>();
        Definition definition = parseDefinition(tokenizer);
        do {
            if (definition == null) {
                throw new IllegalStateException();
            }
            definitions.add(definition);
            definition = parseDefinition(tokenizer);
        } while (false);
        return definitions;
    }

    private Definition parseDefinition(Tokenizer tokenizer) {
        String identifier = parseIdentifier(tokenizer);
        parseRightArrow();
        Expression expression = parseExpression(tokenizer);
        return definition(identifier, expression);
    }

    private String parseIdentifier(Tokenizer tokenizer) {
        return tokenizer.readIdentifier();
    }

    private void parseRightArrow() {
        /* do nothing */
    }

    private Expression parseExpression(Tokenizer tokenizer) {
        return characterClass();
    }
}

class Tokenizer {
    private final Reader reader;

    public Tokenizer(String grammarPath) throws FileNotFoundException {
        this.reader = new FileReader(new File(grammarPath));

    }

    public String readIdentifier() {
        return read();
    }

    private String read() {
        int character;
        try {
            character = reader.read();
            if (character != -1) {
                return String.valueOf((char)character);
            } else {
                throw new IllegalStateException();
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