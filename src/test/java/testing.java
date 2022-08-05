import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.actions.EncodingAction;
import ca.fxco.encodedchat.actions.EncodingActions;
import ca.fxco.encodedchat.config.ConfigManager;
import ca.fxco.encodedchat.encodingSets.*;

import java.util.UUID;

import static ca.fxco.encodedchat.EncodedChat.ENCODING_SETS;
import static ca.fxco.encodedchat.EncodedChat.PLAYER_ACTIONS;
import static ca.fxco.encodedchat.utils.EncodingUtils.NO_ARGS;

public class testing {

    public static void main(String[] args) {
        testEncodingSets();
        testConfig();
    }

    // Testing config saving & loading
    public static void testConfig() {
        System.out.println("Initializing config");
        EncodedChat.initializeConfig(true);
        System.out.println("TESTING: `Config`");
        PLAYER_ACTIONS.putPlayerAction(new UUID(1L,2L),new EncodingActions());
        EncodingActions encodingActions = new EncodingActions();
        encodingActions.add(new EncodingAction(ENCODING_SETS.get("Base64")));
        encodingActions.add(new EncodingAction(ENCODING_SETS.get("Seed")));
        PLAYER_ACTIONS.putPlayerAction(new UUID(5L,9L), encodingActions);
        encodingActions = new EncodingActions();
        encodingActions.add(new EncodingAction(ENCODING_SETS.get("Base32")));
        EncodingSet seedEncodingSet = ENCODING_SETS.get("Seed");
        encodingActions.add(new EncodingAction(
                seedEncodingSet,
                seedEncodingSet.createArguments(new String[]{"25","67"}).parseArguments())
        );
        PLAYER_ACTIONS.putPlayerAction(new UUID(25L, 72L), encodingActions);
        encodingActions = new EncodingActions();
        encodingActions.add(new EncodingAction(ENCODING_SETS.get("Rot13")));
        PLAYER_ACTIONS.putPlayerAction(new UUID(50L, 12L), encodingActions);
        encodingActions = new EncodingActions();
        encodingActions.add(new EncodingAction(ENCODING_SETS.get("Caesar")));
        EncodingSet replaceEncodingSet = ENCODING_SETS.get("Replace");
        encodingActions.add(new EncodingAction(
                replaceEncodingSet,
                replaceEncodingSet.createArguments(
                        new String[]{"I","He","angry","happy","hate","love","kill","punch"}
                ).parseArguments())
        );
        encodingActions.add(new EncodingAction(ENCODING_SETS.get("Rot13")));
        encodingActions.add(new EncodingAction(ENCODING_SETS.get("Base32")));
        PLAYER_ACTIONS.putPlayerAction(new UUID(999L, 999L), encodingActions);
        ConfigManager manager = EncodedChat.getConfigManager();
        System.out.println("saving config");
        manager.savePlayerActions(PLAYER_ACTIONS);
        manager.saveConfig(EncodedChat.CONFIG);
        System.out.println("loading config");
        manager.loadPlayerActions();
        manager.loadConfig();
    }

    private static String[] testStrings = new String[]{
            "Wow you're pretty smart!",
            "this is a magical test",
            "Fx was here",
            "1234 I like to encode"
    };

    // Testing all the encoding sets and how they perform against samples (work needs to be done on arguments)
    public static void testEncodingSets() {
        System.out.println("Initializing encoding sets");
        EncodedChat.initializeEncodingSets();
        System.out.println("TESTING: `EncodingSets`");
        System.out.println(" set Name |autoDetect|canDecode|                  encoded                   |                  decoded");
        for (String encodeMe : testStrings) {
            if (encodeMe.length() > 36) continue;
            //System.out.println("Testing String: `"+encodeMe+"`");
            //System.out.println(" set Name |autoDetect|canDecode|                  encoded                   |                  decoded");
            for (EncodingSet set : ENCODING_SETS.values()) {
                if (set.canEncode(encodeMe, NO_ARGS)) {
                    String encoded = set.encode(encodeMe, NO_ARGS);
                    boolean hasEncoding = set.hasEncoding(encoded, NO_ARGS);
                    String decoded = hasEncoding ? set.decode(encoded, NO_ARGS) : "";
                    System.out.printf("%10s|%10b|%9b|%44s|%s%n",
                            set.getId(),
                            set.canAutomaticallyDetect(),
                            hasEncoding,
                            encoded,
                            decoded
                    );
                }
            }
        }
    }
}
