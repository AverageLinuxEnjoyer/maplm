package social_network.domain.exceptions;

/**
 * Exception thrown when a validation error occurs.
 */
public class ValidationException extends Exception {
    private String message;

    /**
     * Constructor for ValidationException.
     * 
     * @param message the error message
     */
    public ValidationException(String message) {
        this.message = message;
    }

    /**
     * Getter for the error message.
     * 
     * @return the error message
     */
    @Override
    public String getMessage() {
        return message;
    }

}
