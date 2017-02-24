package nl.dvberkel.peg;

public class Success<T> implements ParseResult<T> {
    private final T content;

    public Success(T content) {
        this.content = content;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    public T unpack() {
        return content;
    }
}
