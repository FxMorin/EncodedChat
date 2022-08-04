package ca.fxco.encodedchat.utils;

import ca.fxco.encodedchat.encodingSets.EncodingSet;

public class EncodingAction {

    private final EncodingSet encodingSet;
    private final String[] arguments;

    public EncodingAction(EncodingSet encodingSet) {
        this(encodingSet, EncodingUtils.EMPTY_ARGS);
    }

    public EncodingAction(EncodingSet encodingSet, String[] arguments) {
        this.encodingSet = encodingSet;
        this.arguments = arguments;
    }

    public boolean hasEncoding(String msg) {
        return this.encodingSet.hasEncoding(msg, this.arguments);
    }

    public boolean canEncode(String msg) {
        return this.encodingSet.canEncode(msg, this.arguments);
    }

    public String decode(String msg) {
        return this.encodingSet.decode(msg, this.arguments);
    }

    public String encode(String msg) {
        return this.encodingSet.encode(msg, this.arguments);
    }

    protected final EncodingSet getEncodingSet() {
        return this.encodingSet;
    }

    protected final String[] getArguments() {
        return this.arguments;
    }

    @Override
    public String toString() {
        return this.encodingSet.getId() + "["+this.arguments.length+"]";
    }
}
