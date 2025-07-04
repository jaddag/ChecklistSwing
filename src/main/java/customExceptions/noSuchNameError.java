package customExceptions;

public class noSuchNameError extends RuntimeException {
    public noSuchNameError() {
        super("No such name found in Database!");
    }
}
