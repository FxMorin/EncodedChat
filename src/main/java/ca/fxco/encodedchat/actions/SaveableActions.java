package ca.fxco.encodedchat.actions;

import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.encodingSets.EncodingSet;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// All this for YAML to stop hating me

public class SaveableActions {

    public static LinkedHashMap<String, String[]> toSaveableActions(EncodingActions encodingActions) {
        LinkedHashMap<String,String[]> saveableActions = new LinkedHashMap<>();
        for (EncodingAction action : encodingActions.getEncodingActions())
            saveableActions.put(action.getEncodingSet().getId(), action.getArguments().getOriginalInput());
        return saveableActions.size() > 0 ? saveableActions : null;
    }

    public static EncodingActions toEncodingActions(LinkedHashMap<String, List<String>> saveableActions) {
        LinkedList<EncodingAction> encodingActions = new LinkedList<>();
        for (Map.Entry<String, List<String>> saveableAction : saveableActions.entrySet()) {
            EncodingSet set = EncodedChat.ENCODING_SETS.get(saveableAction.getKey());
            if (set != null) {
                List<String> args = saveableAction.getValue();
                if (args != null && args.size() > 0) {
                    ParsedArguments parsedArguments = set.createArguments(args.toArray(String[]::new));
                    if (parsedArguments.validateArguments()) {
                        encodingActions.add(new EncodingAction(set, parsedArguments.parseArguments()));
                    }
                } else {
                    encodingActions.add(new EncodingAction(set));
                }
            }
        }
        return encodingActions.size() > 0 ? new EncodingActions(encodingActions) : null;
    }
}
