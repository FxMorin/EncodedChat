package ca.fxco.encodedchat.encodingSets;

import ca.fxco.encodedchat.utils.EncodingUtils;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.Xoroshiro128PlusPlusRandom;

import java.util.*;
import java.util.stream.Collectors;

public class SeedEncodingSet implements EncodingSet {

    /**
     *  This encoding is a shuffle using a 2 digit seed.
     *  The seed gets used in the random to get the reverse of the order.
     *  Padding: S33D[10-99]<shuffled characters>|
     */

    private final static Xoroshiro128PlusPlusRandom rand = new Xoroshiro128PlusPlusRandom(0);
    private final static String START = "S33D";
    private final static String END = "|";

    @Override
    public String getId() {
        return "Seed";
    }

    @Override
    public boolean canEncode(String msg, Object[] args) {
        return msg.length() > 2 && (msg.length() + START.length() + END.length()) < 256;
    }

    @Override
    public boolean hasEncoding(String msg, Object[] args) {
        return msg.startsWith(START) &&
                msg.endsWith(END) &&
                EncodingUtils.isNumeric(msg.substring(START.length(),START.length()+2)) &&
                msg.length() > START.length() + 4;
    }

    @Override
    public String decode(String msg, Object[] args) {
        int startLength = START.length();
        int seed = Integer.parseInt(msg.substring(startLength, startLength+2));
        List<Character> chars = msg.substring(startLength+2, msg.length()-1).chars()
                .mapToObj(c -> (char)c).collect(Collectors.toList());
        LinkedList<Character> decodedMessage = new LinkedList<>();
        int count = 0;
        while (chars.size() > 0) {
            rand.setSeed((chars.size()-1)+seed);
            decodedMessage.add(rand.nextInt(++count), chars.remove(0));
        }
        return decodedMessage.stream().map(String::valueOf).collect(Collectors.joining());
    }

    @Override
    public String encode(String msg, Object[] args) {
        int seed = Random.create().nextBetween(10, 99);
        List<Character> chars = msg.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        StringBuilder encodedMessage = new StringBuilder();
        int count = 0;
        while (chars.size() > 0) {
            rand.setSeed(count+++seed);
            encodedMessage.insert(0, chars.remove(rand.nextInt(chars.size())));
        }
        return START+seed+encodedMessage+END;
    }
}
