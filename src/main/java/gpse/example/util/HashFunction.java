package gpse.example.util;

/**
 * This interface is responsible for designate hash functions.
 *
 * @author Jan Kronsbein & Alexander Heide
 * @since 04-13-2021
 */
public interface HashFunction {
    /**
     * This method calculates and returns a hashcode for a given String.
     *
     * @param input Any String
     * @return hash as a String value
     */
    String computeHash(String input);
}
