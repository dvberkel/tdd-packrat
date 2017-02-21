package nl.dvberkel.peg.bootstrap;

import nl.dvberkel.peg.Ast;
import nl.dvberkel.peg.Parser;

import java.nio.file.Path;

public class BootStrappedParser implements Parser {
    public static Ast grammar() {
        return new Grammar();
    }

    @Override
    public Ast parse(Path grammarPath) {
        return grammar();
    }
}

class Grammar implements Ast {
    @Override
    public int hashCode() {
        return 37;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Grammar)) {
            return false;
        }
        return true;
    }
}