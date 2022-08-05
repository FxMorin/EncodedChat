package ca.fxco.encodedchat.config;

import ca.fxco.encodedchat.actions.EncodingActions;
import ca.fxco.encodedchat.actions.SaveableActions;

import java.util.*;

public class PlayerActions {

    public HashMap<UUID, EncodingActions> PLAYER_ENCODING_ACTIONS = new HashMap<>();

    public HashMap<String, LinkedHashMap<String,String[]>> getSaveableActions() {
        HashMap<String, LinkedHashMap<String,String[]>> saveableActions = new HashMap<>();
        for (Map.Entry<UUID, EncodingActions> playerActions : PLAYER_ENCODING_ACTIONS.entrySet()) {
            LinkedHashMap<String, String[]> saveable = SaveableActions.toSaveableActions(playerActions.getValue());
            if (saveable != null)
                saveableActions.put(playerActions.getKey().toString(), saveable);
        }
        return saveableActions;
    }

    public void loadSaveableActions(HashMap<String, LinkedHashMap<String, List<String>>> saveableActions) {
        for (Map.Entry<String, LinkedHashMap<String, List<String>>> playerActions : saveableActions.entrySet()) {
            EncodingActions encodingActions = SaveableActions.toEncodingActions(playerActions.getValue());
            if (encodingActions != null && encodingActions.getEncodingActions().size() > 0)
                PLAYER_ENCODING_ACTIONS.put(UUID.fromString(playerActions.getKey()), encodingActions);
        }
    }

    public EncodingActions putPlayerAction(UUID uuid, EncodingActions encodingActions) {
        return PLAYER_ENCODING_ACTIONS.put(uuid, encodingActions);
    }

    public boolean hasPlayerAction(UUID uuid) {
        return PLAYER_ENCODING_ACTIONS.containsKey(uuid);
    }

    public EncodingActions getPlayerAction(UUID uuid) {
        return PLAYER_ENCODING_ACTIONS.get(uuid);
    }

    public EncodingActions removePlayerAction(UUID uuid) {
        return PLAYER_ENCODING_ACTIONS.remove(uuid);
    }

    public void clearPlayerAction() {
        PLAYER_ENCODING_ACTIONS.clear();
    }
}
