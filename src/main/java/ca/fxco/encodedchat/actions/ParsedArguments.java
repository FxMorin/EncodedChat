package ca.fxco.encodedchat.actions;

public interface ParsedArguments {

    boolean validateArguments();

    ParsedArguments parseArguments();

    Object get(int index);

    String[] getOriginalInput();

    int size();
}
