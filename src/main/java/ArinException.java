/**
 * Represents a custom exception for the Arin chatbot.
 */
class ArinException extends Exception {
    /**
     * Constructs an ArinException with the given message.
     *
     * @param message The error message.
     */
    public ArinException(String message) {
        super(message);
    }
}