package ca.fxco.encodedchat.encodingSets;

public class Rot13EncodingSet implements EncodingSet {

    @Override
    public String getId() {
        return "Rot13";
    }

    @Override
    public boolean canAutomaticallyDetect() {
        return false;
    }

    @Override
    public boolean canEncode(String msg, Object[] args) {
        return true;
    }

    @Override
    public boolean hasEncoding(String msg, Object[] args) {
        return true;
    }

    @Override
    public String decode(String msg, Object[] args) {
        return rot13(msg);
    }

    @Override
    public String encode(String msg, Object[] args) {
        return rot13(msg);
    }

    private static String rot13(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if      (c >= 'a' && c <= 'm') c += 13;
            else if (c >= 'A' && c <= 'M') c += 13;
            else if (c >= 'n' && c <= 'z') c -= 13;
            else if (c >= 'N' && c <= 'Z') c -= 13;
            sb.append(c);
        }
        return sb.toString();
    }
}
