package ca.fxco.encodedchat.utils;

import java.util.*;
import java.util.stream.Collectors;

public class EncodingActions {

    private final LinkedList<EncodingAction> encodingActions;

    public EncodingActions() {
        this(new LinkedList<>());
    }

    public EncodingActions(LinkedList<EncodingAction> encodingActions) {
        this.encodingActions = encodingActions;
    }

    public LinkedList<EncodingAction> getEncodingActions() {
        return this.encodingActions;
    }

    public void clearActions() {
        this.encodingActions.clear();
    }

    public void replaceActions(Collection<EncodingAction> encodingActions) {
        clearActions();
        this.encodingActions.addAll(encodingActions);
    }

    public void replaceActions(EncodingActions encodingActions) {
        replaceActions(encodingActions.getEncodingActions());
    }

    public String runEncode(String message) {
        for (EncodingAction action : this.encodingActions)
            if (action.canEncode(message))
                message = action.encode(message);
        return message;
    }

    public String runDecode(String message) {
        for (EncodingAction action : this.encodingActions)
            if (action.hasEncoding(message))
                message = action.decode(message);
        return message;
    }

    public void add(EncodingAction encodingAction) {
        this.encodingActions.add(encodingAction);
    }

    public void remove(EncodingAction encodingAction) {
        this.encodingActions.remove(encodingAction);
    }

    public Set<String> getEncodingSetNames() {
        return this.encodingActions.stream().map((action) -> action.getEncodingSet().getId())
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        StringBuilder actionsStr = new StringBuilder();
        int size = this.encodingActions.size();
        for (int i = 0; i < size; i++)
            actionsStr.append(this.encodingActions).append(i == size - 1 ? "" : ", ");
        return "Actions: "+actionsStr;
    }
}
