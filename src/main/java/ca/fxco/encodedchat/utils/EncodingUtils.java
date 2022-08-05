package ca.fxco.encodedchat.utils;

import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.actions.NoArguments;
import ca.fxco.encodedchat.actions.ParsedArguments;
import ca.fxco.encodedchat.encodingSets.EncodingSet;

import java.util.ArrayList;
import java.util.List;

public class EncodingUtils {

    public final static ParsedArguments NO_ARGS = new NoArguments();

    public static boolean isNumeric(String str) {
        return str != null && str.matches("^-*[\\d.]+$");
    }

    public static boolean isByte(String str) {
        return str != null && str.length() <= 3 && (str.length() == 3 ?
                str.matches("^(-*1(?>[0-2][0-7])|(?>[0-1][\\d]))$") : // Make sure it's within byte bounds
                str.matches("^-*[\\d.]+$")
        );
    }

    public static String attemptAutomaticDecoding(String msg) {
        List<EncodingSet> usedEncodingSets = new ArrayList<>();
        multi : while(true) {
            for (EncodingSet encodingSet : EncodedChat.ENCODING_SETS.values()) {
                if (usedEncodingSets.contains(encodingSet)) continue;
                if (encodingSet.canAutomaticallyDetect() && encodingSet.hasEncoding(msg, NO_ARGS)) {
                    msg = encodingSet.decode(msg, NO_ARGS);
                    usedEncodingSets.add(encodingSet);
                    continue multi;
                }
            }
            break;
        }
        return msg;
    }
}
