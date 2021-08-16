package mylib.date.exception;

public class DateExceptions {
    public static class DateException extends RuntimeException {
        public DateException(String message) {
            super(message);
        }
    }

    public static class DateParseException extends DateException {
        public DateParseException(String message) {
            super(message);
        }
    }

    public static class DateFormatException extends DateException {
        public DateFormatException(String message) {
            super(message);
        }
    }
}
