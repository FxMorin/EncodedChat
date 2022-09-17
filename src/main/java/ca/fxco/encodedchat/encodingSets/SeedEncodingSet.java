package ca.fxco.encodedchat.encodingSets;

import ca.fxco.encodedchat.actions.ParsedArguments;
import ca.fxco.encodedchat.utils.EncodingUtils;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.math.random.Xoroshiro128PlusPlusRandom;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class SeedEncodingSet implements EncodingSet {

    /**
     *  This encoding is a shuffle using a 2 digit seed.
     *  The seed gets used in the random to get the reverse of the order.
     *  Padding: S33D[10-99]<shuffled characters>|
     *
     *  Arguments:
     *  1) seed [Integer] - If using the integer as part of the arguments, it will not be included in the message
     */

    private final static Xoroshiro128PlusPlusRandom rand = new Xoroshiro128PlusPlusRandom(0);
    private final static String START = "S33D";
    private final static String END = "|";

    @Override
    public String getId() {
        return "Seed";
    }

    @Override
    public boolean canEncode(String msg, ParsedArguments args) {
        return msg.length() > 2 && (msg.length() + START.length() + END.length()) < 256;
    }

    @Override
    public boolean hasEncoding(String msg, ParsedArguments args) {
        return msg.startsWith(START) &&
                msg.endsWith(END) &&
                (args.size() == 1 || EncodingUtils.isNumeric(msg.substring(START.length(),START.length()+2))) &&
                msg.length() > START.length() + 4;
    }

    @Override
    public String decode(String msg, ParsedArguments args) {
        int startLength = START.length();
        boolean hasInternalSeed = args.size() == 1;
        int seed = hasInternalSeed ? (int)args.get(0) : Integer.parseInt(msg.substring(startLength, startLength + 2));
        List<Character> chars = msg.substring(startLength + (hasInternalSeed ? 0 : 2), msg.length() - 1).chars()
                .mapToObj(c -> (char)c).collect(Collectors.toList());
        LinkedList<Character> decodedMessage = new LinkedList<>();
        int count = 0;
        while (chars.size() > 0) {
            rand.setSeed((chars.size() - 1) + seed);
            decodedMessage.add(rand.nextInt(++count), chars.remove(0));
        }
        return decodedMessage.stream().map(String::valueOf).collect(Collectors.joining());
    }

    @Override
    public String encode(String msg, ParsedArguments args) {
        boolean hasInternalSeed = args.size() == 1;
        int seed = hasInternalSeed ? (int)args.get(0) : Random.create().nextBetween(10, 99);
        List<Character> chars = msg.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        StringBuilder encodedMessage = new StringBuilder();
        int count = 0;
        while (chars.size() > 0) {
            rand.setSeed(count+++seed);
            encodedMessage.insert(0, chars.remove(rand.nextInt(chars.size())));
        }
        return START + (hasInternalSeed ? "" : seed) + encodedMessage + END;
    }

    @Override
    public ParsedArguments createArguments(@Nullable String[] originalArgs) {
        return new SeedArguments(originalArgs);
    }

    static class SeedArguments implements ParsedArguments {

        @Nullable
        private final String[] originalArgs; // Used to save and recreate arguments
        @Nullable
        private Integer seed = null;

        public SeedArguments(@Nullable String[] args) {
            this.originalArgs = args;
        }

        @Override
        public boolean validateArguments() {
            if (originalArgs == null || originalArgs.length == 0) return true;
            return originalArgs.length == 1 && EncodingUtils.isNumeric(originalArgs[0]);
        }

        @Override
        public ParsedArguments parseArguments() {
            if (originalArgs != null && originalArgs.length > 0)
                this.seed = Integer.parseInt(originalArgs[0]);
            return this;
        }

        @Override
        public Object get(int index) {
            return seed;
        }

        @Override
        public String[] getOriginalInput() {
            return originalArgs;
        }

        @Override
        public int size() {
            return seed == null ? 0 : 1;
        }
    }
}
