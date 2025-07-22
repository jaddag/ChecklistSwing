package customExceptions;

public class noSuchItemError extends RuntimeException {
    public noSuchItemError() {
        super("No such item found in Database!");
    }
}
