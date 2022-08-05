package ca.fxco.encodedchat.actions;

public class NoArguments implements ParsedArguments {

    @Override
    public boolean validateArguments() {
        return true;
    }

    @Override
    public ParsedArguments parseArguments() {
        return this;
    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public String[] getOriginalInput() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
