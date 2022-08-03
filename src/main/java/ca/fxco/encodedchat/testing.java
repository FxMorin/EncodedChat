package ca.fxco.encodedchat;

import ca.fxco.encodedchat.encodingSets.*;

public class testing {

    public static void main(String[] args) {
        String encodeMe = "Wow you're pretty smart, congrats!";

        SeedEncodingSet seedEncoding = new SeedEncodingSet();
        Base32EncodingSet base32Encoding = new Base32EncodingSet();
        Base64EncodingSet base64Encoding = new Base64EncodingSet();
        Rot13EncodingSet rot13Encoding = new Rot13EncodingSet();
        CaesarEncodingSet caesarEncoding = new CaesarEncodingSet();

        String seedEncoded = seedEncoding.encode(encodeMe);
        String base32Encoded = base32Encoding.encode(encodeMe);
        String base64Encoded = base64Encoding.encode(encodeMe);
        String rot13Encoded = rot13Encoding.encode(encodeMe);
        String caesarEncoded = caesarEncoding.encode(encodeMe);

        String seedDecoded = seedEncoding.decode(seedEncoded);
        String base32Decoded = base32Encoding.decode(base32Encoded);
        String base64Decoded = base64Encoding.decode(base64Encoded);
        String rot13Decoded = rot13Encoding.decode(rot13Encoded);
        String caesarDecoded = caesarEncoding.decode(caesarEncoded);

        System.out.println("seed - encoded: "+seedEncoded+ " decoded: "+seedDecoded);
        System.out.println("base32 - encoded: "+base32Encoded+ " decoded: "+base32Decoded);
        System.out.println("base64 - encoded: "+base64Encoded+ " decoded: "+base64Decoded);
        System.out.println("rot13 - encoded: "+rot13Encoded+ " decoded: "+rot13Decoded);
        System.out.println("caesar - encoded: "+caesarEncoded+ " decoded: "+caesarDecoded);
    }
}
