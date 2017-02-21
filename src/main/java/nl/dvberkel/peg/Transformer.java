package nl.dvberkel.peg;

public interface Transformer<Input, Output> {
    Output transform(Input input);
}
