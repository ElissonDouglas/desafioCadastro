package exceptions;

public class InvalidAge extends RuntimeException {
    public InvalidAge(String message) {
        super(message);
    }
}
