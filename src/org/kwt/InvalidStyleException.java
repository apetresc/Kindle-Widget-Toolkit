package org.kwt;

/**
 * An exception thrown by styleable KWT components which indicates that they do not support
 * the provided style. This may occur either because an incorrect style from another class was
 * provided, or because that particular style applies to some other part of the component.
 *
 */
public class InvalidStyleException extends RuntimeException {
    private static final long serialVersionUID = -8543895842607438654L;

    private int invalidStyle;
    
    /**
     * Constructs a new exception with the specified detail message and style.
     * @param message the detail message
     * @param invalidStyle the style which could not be applied
     */
    public InvalidStyleException(String message, int invalidStyle) {
        super(message);
        this.invalidStyle = invalidStyle;
    }
    
    /**
     * Returns the style which could not be applied.
     * @return the invalid style
     */
    public int getInvalidStyle() {
        return invalidStyle;
    }

    /**
     * Returns a detail message specifying why the style could not be applied.
     * @return the detail message
     */
    public String getMessage() {
        return super.getMessage() + " [Invalid Style: " + invalidStyle + "]"; 
    }
}
