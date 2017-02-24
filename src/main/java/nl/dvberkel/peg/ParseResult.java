package nl.dvberkel.peg;

public interface ParseResult<T> {
    static <S> ParseResult<S> success(S content) {
        return new Success(content);
    }
    
    static ParseResult<Void> failure() {
        return Failure.instance;
    }

    boolean isSuccess();
}

