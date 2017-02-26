package nl.dvberkel.peg;

public interface ParseResult<T> {
    static <S> ParseResult<S> success(S content) {
        return new Success(content);
    }
    
    static <S> ParseResult<S> failure() {
        return Failure.instance;
    }

    boolean isSuccess();
}

