package nl.dvberkel.peg;

public interface ParseResult<T> {
    static <S> ParseResult<S> success(S content) {
        return new Success(content);
    }
    
    static <S> ParseResult<S> failure() {
        return Failure.instance;
    }

    static <S> S unpack(ParseResult<S> result) {
        if (result.isSuccess()) {
            return ((Success<S>) result).unpack();
        } else {
            throw new IllegalStateException("can not unpack a failed parse result");
        }
    }


    boolean isSuccess();
}

