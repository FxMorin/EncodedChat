import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.encodingSets.*;
import ca.fxco.encodedchat.utils.EncodingUtils;

public class testing {

    private static String[] testStrings = new String[]{
            "Wow you're pretty smart!",
            "this is a magical test",
            "Fx was here",
            "1234 I like to encode"
    };

    public static void main(String[] args) {
        new EncodedChat().onInitializeClient();
        System.out.println(" set Name |autoDetect|canDecode|                  encoded                   |                  decoded");
        for (String encodeMe : testStrings) {
            if (encodeMe.length() > 36) continue;
            //System.out.println("Testing String: `"+encodeMe+"`");
            //System.out.println(" set Name |autoDetect|canDecode|                  encoded                   |                  decoded");
            for (EncodingSet set : EncodedChat.ENCODING_SETS.values()) {
                if (set.canEncode(encodeMe, EncodingUtils.EMPTY_ARGS)) {
                    String encoded = set.encode(encodeMe, EncodingUtils.EMPTY_ARGS);
                    boolean hasEncoding = set.hasEncoding(encoded, EncodingUtils.EMPTY_ARGS);
                    String decoded = hasEncoding ? set.decode(encoded, EncodingUtils.EMPTY_ARGS) : "";
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
