package ca.fxco.encodedchat.encodingSets;

import ca.fxco.encodedchat.actions.ParsedArguments;
import org.jetbrains.annotations.Nullable;

public class ReplaceEncodingSet implements EncodingSet {

    @Override
    public String getId() {
        return "Replace";
    }

    @Override
    public boolean canAutomaticallyDetect() {
        return false;
    }

    @Override
    public boolean hasEncoding(String msg, ParsedArguments parsedArgs) {
        return true;
    }

    @Override
    public boolean canEncode(String msg, ParsedArguments parsedArgs) {
        return true;
    }

    @Override
    public String decode(String msg, ParsedArguments parsedArgs) {
        if (parsedArgs.size() > 0) {
            String[] values = parsedArgs.getOriginalInput();
            for (int i = 0; i + 1 < values.length; i += 2)
                msg = msg.replaceAll(values[i], values[i+1]);
        }
        return msg;
    }

    @Override
    public String encode(String msg, ParsedArguments parsedArgs) {
        if (parsedArgs.size() > 0) {
            String[] values = parsedArgs.getOriginalInput();
            for (int i = values.length - 2; i >= 0; i -= 2)
                msg = msg.replaceAll(values[i+1], values[i]);
        }
        return msg;
    }

    @Override
    public ParsedArguments createArguments(@Nullable String[] originalArgs) {
        return new ReplaceArguments(originalArgs);
    }

    static class ReplaceArguments implements ParsedArguments {

        // This ParsedArguments is simply a holder, since the values we need to store are strings

        @Nullable
        private final String[] originalArgs; // Used to save and recreate arguments

        public ReplaceArguments(@Nullable String[] args) {
            this.originalArgs = args;
        }

        @Override
        public boolean validateArguments() {
            return originalArgs.length % 2 == 0; // Should be even
        }

        @Override
        public ParsedArguments parseArguments() {
            return this;
        }

        @Override
        public Object get(int index) {
            return originalArgs[index];
        }

        @Override
        public String[] getOriginalInput() {
            return originalArgs;
        }

        @Override
        public int size() {
            return originalArgs.length;
        }
    }
}
