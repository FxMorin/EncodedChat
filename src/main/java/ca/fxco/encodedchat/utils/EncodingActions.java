package ca.fxco.encodedchat.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EncodingActions {

    private final List<EncodingAction> encodingActions;

    public EncodingActions() {
        this(new ArrayList<>());
    }

    public EncodingActions(List<EncodingAction> encodingActions) {
        this.encodingActions = encodingActions;
    }

    public List<EncodingAction> getEncodingActions() {
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
}
