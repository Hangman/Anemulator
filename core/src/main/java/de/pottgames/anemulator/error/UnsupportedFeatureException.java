package de.pottgames.anemulator.error;

public class UnsupportedFeatureException extends RuntimeException {
    private static final long serialVersionUID = 2699500092575924016L;


    public UnsupportedFeatureException(String message) {
        super(message);
    }


    public UnsupportedFeatureException(Throwable t) {
        super(t);
    }


    public UnsupportedFeatureException(String message, Throwable t) {
        super(message, t);
    }

}
