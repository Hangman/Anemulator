package de.pottgames.anemulator.error;

public class AccessViolationException extends RuntimeException {
    private static final long serialVersionUID = 5041303216099257467L;


    public AccessViolationException(String message) {
        super(message);
    }


    public AccessViolationException(Throwable t) {
        super(t);
    }


    public AccessViolationException(String message, Throwable t) {
        super(message, t);
    }

}
