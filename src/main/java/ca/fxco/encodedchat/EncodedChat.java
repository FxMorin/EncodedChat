package ca.fxco.encodedchat;

import ca.fxco.encodedchat.config.ConfigManager;
import ca.fxco.encodedchat.config.ECConfig;
import ca.fxco.encodedchat.config.PlayerActions;
import ca.fxco.encodedchat.encodingSets.*;
import ca.fxco.encodedchat.utils.command.EncodedChatCommand;
import ca.fxco.encodedchat.actions.EncodingActions;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.CommandRegistryAccess;

import java.util.HashMap;
import java.util.Set;

@Environment(EnvType.CLIENT)
public class EncodedChat implements ClientModInitializer {

    public static final MinecraftClient MC = MinecraftClient.getInstance();

    /*
    TODO:
        - Add server-based decoding and writing as system message (for servers that support saveminecraft)
        - Add Config for rules/instructions and options (A bit like preview hyjack)
        - Add No-Chat-Reports detection to disable mod (if option enabled, needs support)
        - Make it easier for other mods to add there own encoding sets (maybe through an entrypoint)
     */

    public final static String MODID = "encodedchat";
    public final static HashMap<String, EncodingSet> ENCODING_SETS = new HashMap<>();

    public final static EncodingActions SELF_ENCODING_ACTIONS = new EncodingActions();

    private static ConfigManager configManager;
    public static ECConfig CONFIG;
    public static PlayerActions PLAYER_ACTIONS;

    @Override
    public void onInitializeClient() {
        initializeEncodingSets();
        initializeConfig(false);
        initializeClientCommands();
    }

    public static void initializeClientCommands() {
        ClientCommandRegistrationCallback.EVENT.register(new ClientCommandRegistrationCallback() {
            @Override
            public void register(CommandDispatcher<FabricClientCommandSource> dispatcher,
                                 CommandRegistryAccess registryAccess) {
                EncodedChatCommand.registerCommands(dispatcher);
            }
        });
    }

    public static void initializeConfig(boolean testing) {
        configManager = new ConfigManager(MODID, testing);
        CONFIG = configManager.loadConfig();
        PLAYER_ACTIONS = configManager.loadPlayerActions();
    }

    public static void initializeEncodingSets() {
        addEncodingSet(new Base32EncodingSet());
        addEncodingSet(new Base64EncodingSet());
        addEncodingSet(new CaesarEncodingSet());
        addEncodingSet(new Rot13EncodingSet());
        addEncodingSet(new SeedEncodingSet());
        addEncodingSet(new ReplaceEncodingSet());
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static Set<String> getEncodingSetNames() {
        return ENCODING_SETS.keySet();
    }

    public static void addEncodingSet(EncodingSet set) {
        ENCODING_SETS.put(set.getId(), set);
    }
}
