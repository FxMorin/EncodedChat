package ca.fxco.encodedchat.encodingSets;

public class SquishEncodingSet implements EncodingSet {

    @Override
    public String getId() {
        return "Squish";
    }

    @Override
    public boolean canEncode(String msg) {
        return false;
    }

    @Override
    public boolean hasEncoding(String msg) {
        return false;
    }

    @Override
    public String decode(String msg) {
        return null;
    }

    @Override
    public String encode(String msg) {
        return null;
    }
}