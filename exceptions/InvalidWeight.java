package exceptions;

public class InvalidWeight extends RuntimeException {
    public InvalidWeight(String message) {
        super(message);
    }
}
