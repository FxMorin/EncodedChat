package ca.fxco.encodedchat.encodingSets;

import org.apache.commons.codec.binary.Base32;

import java.util.regex.Pattern;

public class Base32EncodingSet implements EncodingSet {

    private static final Pattern pattern = Pattern.compile("^(?:[A-Z2-7]{8})*(?:[A-Z2-7]{2}={6}|[A-Z2-7]{4}={4}|[A-Z2-7]{5}={3}|[A-Z2-7]{7}=)?$");
    private static final Base32 base32 = new Base32();

    @Override
    public String getId() {
        return "Base32";
    }

    @Override
    public boolean canEncode(String msg, Object[] args) {
        return true;
    }

    @Override
    public boolean hasEncoding(String msg, Object[] args) {
        return pattern.matcher(msg).find();
    }

    @Override
    public String decode(String msg, Object[] args) {
        return new String(base32.decode(msg));
    }

    @Override
    public String encode(String msg, Object[] args) {
        return base32.encodeAsString(msg.getBytes());
    }
}
