package ca.fxco.encodedchat.encodingSets;

import ca.fxco.encodedchat.utils.EncodingUtils;
import net.minecraft.util.math.random.Random;

public class CaesarEncodingSet implements EncodingSet {

    private static final Random rand = Random.create();

    @Override
    public String getId() {
        return "Caesar";
    }

    @Override
    public boolean canAutomaticallyDetect() {
        return false;
    }

    @Override
    public boolean canEncode(String msg, Object[] args) {
        return msg.equals(msg.toLowerCase());
    }

    @Override
    public boolean hasEncoding(String msg, Object[] args) {
        return EncodingUtils.isNumeric(msg.substring(0,1)) && msg.equals(msg.toLowerCase());
    }

    @Override
    public String decode(String msg, Object[] args) {
        int shift = Integer.parseInt(msg.substring(0,1));
        String encodedMessage = msg.substring(1);
        StringBuilder decodedMessage = new StringBuilder();
        for (int i = 0; i < encodedMessage.length(); i++) {
            char c = encodedMessage.charAt(i);
            if(c >= 'a' && c <= 'z'){
                c = (char)(c - shift);
                if(c < 'a') c = (char)(c + 'z' - 'a' + 1);
            }
            decodedMessage.append(c);
        }
        return decodedMessage.toString();
    }

    @Override
    public String encode(String msg, Object[] args) {
        int shift = rand.nextBetween(2, 9);
        StringBuilder encodedMessage = new StringBuilder();
        for (int i = 0; i < msg.length(); i++) {
            char c = msg.charAt(i);
            if (c >= 'a' && c <= 'z') {
                c = (char)(c + shift);
                if(c > 'z') c = (char)(c - 'z' + 'a' - 1);
            }
            encodedMessage.append(c);
        }
        return shift+encodedMessage.toString();
    }
}
