package org.kwt;

public class InvalidStyleException extends RuntimeException {
    private static final long serialVersionUID = -8543895842607438654L;

    private int invalidStyle;
    
    public InvalidStyleException(String message, int invalidStyle) {
        super(message);
        this.invalidStyle = invalidStyle;
    }
    
    public int getInvalidStyle() {
        return invalidStyle;
    }

    public String getMessage() {
        return super.getMessage() + " [Invalid Style: " + invalidStyle + "]"; 
    }
}
