import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.encodingSets.*;

public class testing {

    private static String[] testStrings = new String[]{
            "Wow you're pretty smart!",
            "this is a magical test",
            "Fx was here",
            "1234 I like to encode"
    };

    public static void main(String[] args) {
        new EncodedChat().onInitializeClient();
        for (String encodeMe : testStrings) {
            if (encodeMe.length() > 36) continue;
            System.out.println("Testing String: `"+encodeMe+"`");
            System.out.println(" set Name |autoDetect|canDecode|                  encoded                   |                  decoded");
            for (EncodingSet set : EncodedChat.ENCODING_SETS.values()) {
                if (set.canEncode(encodeMe)) {
                    String encoded = set.encode(encodeMe);
                    boolean hasEncoding = set.hasEncoding(encoded);
                    String decoded = hasEncoding ? set.decode(encoded) : "";
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
