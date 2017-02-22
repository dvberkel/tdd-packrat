package nl.dvberkel.peg.bootstrap;

import nl.dvberkel.peg.Ast;
import nl.dvberkel.peg.Parser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class BootStrappedParser implements Parser {
    public static Ast grammar(Definition first, Definition... rest) {
        Collection<Definition> definitions = new ArrayList<>();
        definitions.add(first);
        definitions.addAll(Arrays.asList(rest));
        return new Grammar(definitions);
    }

    public static Definition definition(String identifier, CharacterClass characterClass) {
        return new Definition(identifier, characterClass);
    }

    public static CharacterClass characterClass() {
        return new CharacterClass();
    }

    @Override
    public Ast parse(Path grammarPath) {
        return grammar(definition("a", characterClass()));
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
    private final CharacterClass characterClass;

    public Definition(String identifier, CharacterClass characterClass) {
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

class CharacterClass {

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