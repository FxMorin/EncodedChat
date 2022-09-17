package ca.fxco.encodedchat.encodingSets;

import ca.fxco.encodedchat.actions.ParsedArguments;
import org.jetbrains.annotations.Nullable;

import static ca.fxco.encodedchat.utils.EncodingUtils.NO_ARGS;

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
     * Creates a ParsedArguments for the EncodingSet
     *
     * @return the Parsed Argument using the arguments provided
     */
    default ParsedArguments createArguments(@Nullable String[] originalArgs) {
        return NO_ARGS;
    }

    /**
     * Checks if a message matches the encoding rules.
     *
     * @return {@code true} if message has the encoding
     */
    default boolean hasEncoding(String msg, ParsedArguments parsedArgs) {
        return true;
    }

    /**
     * Checks if a message can be encoded using this encoding set
     *
     * @return {@code true} if message can be encoded
     */
    default boolean canEncode(String msg, ParsedArguments parsedArgs) {
        return true;
    }

    /**
     * Decodes a string that matches the encoding rules.
     *
     * @return the decoded string
     */
    String decode(String msg, ParsedArguments parsedArgs);

    /**
     * Encodes a string that can be encoded
     *
     * @return the encoded string
     */
    String encode(String msg, ParsedArguments parsedArgs);
}
