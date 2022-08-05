package ca.fxco.encodedchat.actions;

import ca.fxco.encodedchat.encodingSets.EncodingSet;

import static ca.fxco.encodedchat.utils.EncodingUtils.NO_ARGS;

public class EncodingAction {

    private EncodingSet encodingSet;
    private ParsedArguments arguments;

    public EncodingAction(EncodingSet encodingSet) {
        this(encodingSet, NO_ARGS);
    }

    public EncodingAction(EncodingSet encodingSet, ParsedArguments arguments) {
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

    public final EncodingSet getEncodingSet() {
        return this.encodingSet;
    }

    public final ParsedArguments getArguments() {
        return this.arguments;
    }

    public final void setEncodingSet(EncodingSet set) {
        this.encodingSet = set;
    }

    public final void setArguments(ParsedArguments parsedArgs) {
        this.arguments = parsedArgs;
    }

    @Override
    public String toString() {
        return this.encodingSet.getId() + "["+this.arguments.size()+"]";
    }
}
