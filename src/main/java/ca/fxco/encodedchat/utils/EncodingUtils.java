package ca.fxco.encodedchat.utils;

import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.encodingSets.EncodingSet;

import java.util.ArrayList;
import java.util.List;

public class EncodingUtils {

    public final static Object[] EMPTY_ARGS = new Object[0];

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }

    public static String attemptAutomaticDecoding(String msg) {
        List<EncodingSet> usedEncodingSets = new ArrayList<>();
        multi : while(true) {
            for (EncodingSet encodingSet : EncodedChat.ENCODING_SETS.values()) {
                if (usedEncodingSets.contains(encodingSet)) continue;
                if (encodingSet.canAutomaticallyDetect() && encodingSet.hasEncoding(msg, EMPTY_ARGS)) {
                    msg = encodingSet.decode(msg, EMPTY_ARGS);
                    usedEncodingSets.add(encodingSet);
                    break multi;
                }
            }
            break;
        }
        return msg;
    }
}
