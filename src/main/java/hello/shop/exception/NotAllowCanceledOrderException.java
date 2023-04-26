package hello.shop.exception;

public class NotAllowCanceledOrderException extends RuntimeException {
    public NotAllowCanceledOrderException() {}
    public NotAllowCanceledOrderException(String message) {
        super(message);
    }
    public NotAllowCanceledOrderException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotAllowCanceledOrderException(Throwable cause) {
        super(cause);
    }
}
