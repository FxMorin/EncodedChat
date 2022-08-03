package ca.fxco.encodedchat;

import ca.fxco.encodedchat.encodingSets.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class EncodedChat implements ClientModInitializer {

    /*
    TODO:
        - Add multi-level instructions
        - Add per-player rules/instructions
        - Add password/seed (also per-player)
        - Add server-based decoding and writing as system message (for servers that support saveminecraft)
        - Add Config for rules/instructions and options (A bit like preview hyjack)
        - Add No-Chat-Reports detection to disable mod (if option enabled, needs support)
        - Make it easier for other mods to add there own encoding sets (maybe through an entrypoint)
     */

    // Using multiple encoding sets in a row (testing only, will be replaced by instruction list)
    public final static boolean MULTILEVEL_MODE = false;

    public final static HashMap<String, EncodingSet> ENCODING_SETS = new HashMap<>();

    @Override
    public void onInitializeClient() {
        addEncodingSet(new Base32EncodingSet());
        addEncodingSet(new Base32EncodingSet());
        addEncodingSet(new Base64EncodingSet());
        addEncodingSet(new CaesarEncodingSet());
        addEncodingSet(new Rot13EncodingSet());
        addEncodingSet(new SeedEncodingSet());
    }

    public static void addEncodingSet(EncodingSet set) {
        ENCODING_SETS.put(set.getId(), set);
    }
}
