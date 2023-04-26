package hello.shop.exception;

public class DuplicateMemberLoginIdException extends RuntimeException {
    public DuplicateMemberLoginIdException() {}
    public DuplicateMemberLoginIdException(String message) {
        super(message);
    }
    public DuplicateMemberLoginIdException(String message, Throwable cause) {
        super(message, cause);
    }
    public DuplicateMemberLoginIdException(Throwable cause) {
        super(cause);
    }
}
