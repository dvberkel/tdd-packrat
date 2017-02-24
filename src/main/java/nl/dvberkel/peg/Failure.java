package nl.dvberkel.peg;

public enum Failure implements ParseResult {
    instance;

    @Override
    public boolean isSuccess() {
        return false;
    }
}
