package ca.fxco.encodedchat.utils;

public class EncodingUtils {

    public static boolean isNumeric(String str) {
        return str != null && str.matches("[0-9.]+");
    }
}
