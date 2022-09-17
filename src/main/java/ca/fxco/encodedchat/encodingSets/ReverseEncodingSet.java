package ca.fxco.encodedchat.encodingSets;

import ca.fxco.encodedchat.actions.ParsedArguments;

public class ReverseEncodingSet implements EncodingSet {

    @Override
    public String getId() {
        return "Reverse";
    }

    @Override
    public boolean canAutomaticallyDetect() {
        return false;
    }

    @Override
    public String decode(String msg, ParsedArguments parsedArgs) {
        return new StringBuilder(msg).reverse().toString();
    }

    @Override
    public String encode(String msg, ParsedArguments parsedArgs) {
        return new StringBuilder(msg).reverse().toString();
    }
}
