package ca.fxco.encodedchat.encodingSets;

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
    boolean canEncode(String msg);
    String decode(String msg);
    String encode(String msg);
}
