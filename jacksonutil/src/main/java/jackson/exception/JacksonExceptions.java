package mylib.jackson.exception;

public class JacksonExceptions {
    public static class JacksonException extends RuntimeException {
        public JacksonException(String message) {
            super(message);
        }
    }
}
