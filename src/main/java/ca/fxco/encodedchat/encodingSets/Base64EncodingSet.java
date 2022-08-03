package ca.fxco.encodedchat.encodingSets;

import java.util.Base64;
import java.util.regex.Pattern;

public class Base64EncodingSet implements EncodingSet {

    private static final Pattern pattern = Pattern.compile("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$");

    @Override
    public String getId() {
        return "Base64";
    }

    @Override
    public boolean canUseMultiLevel() {
        return true;
    }

    @Override
    public boolean canEncode(String msg) {
        return true;
    }

    @Override
    public boolean hasEncoding(String msg) {
        return pattern.matcher(msg).find();
    }

    @Override
    public String decode(String msg) {
        return new String(Base64.getDecoder().decode(msg));
    }

    @Override
    public String encode(String msg) {
        return Base64.getEncoder().encodeToString(msg.getBytes());
    }
}
