package ca.fxco.encodedchat.actions;

public class NoArguments implements ParsedArguments {

    @Override
    public boolean validateArguments(String[] args) {
        return args.length == 0;
    }

    @Override
    public ParsedArguments parseArguments(String[] args) {
        return this;
    }

    @Override
    public Object get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
