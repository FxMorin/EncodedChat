package ca.fxco.encodedchat.encodingSets;

/**
 * An interface for encoding sets
 */

public interface EncodingSet {

    /**
     * The ID that this set uses. Should be unique
     */
    String getId();

    /**
     * If this encoding set can automatically detect if an encoding is within a message.
     *
     * @return {@code true} if message should be decoded on {@link #hasEncoding(String)} returning {@code true}
     */
    default boolean canAutomaticallyDetect() {
        return true;
    }

    /**
     * If this encoding set can be tried again on the results, used if you encode the message multiple times
     *
     * @return true if hasEncoding() won't pickup the results of decode()
     */
    default boolean canUseMultiLevel() {
        return false;
    }

    /**
     * Checks if a message matches the encoding rules.
     *
     * @return {@code true} if message has the encoding
     */
    boolean hasEncoding(String msg);

    /**
     * Checks if a message can be encoded using this encoding set
     *
     * @return {@code true} if message can be encoded
     */
    boolean canEncode(String msg);

    /**
     * Decodes a string that matches the encoding rules.
     *
     * @return the decoded string
     */
    String decode(String msg);

    /**
     * Encodes a string that can be encoded
     *
     * @return the encoded string
     */
    String encode(String msg);
}
