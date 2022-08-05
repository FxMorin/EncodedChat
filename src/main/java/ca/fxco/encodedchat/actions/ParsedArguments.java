package ca.fxco.encodedchat.actions;

import org.jetbrains.annotations.Nullable;

public interface ParsedArguments {

    boolean validateArguments(@Nullable String[] args);

    ParsedArguments parseArguments(String[] args);

    Object get(int index);

    int size();
}
