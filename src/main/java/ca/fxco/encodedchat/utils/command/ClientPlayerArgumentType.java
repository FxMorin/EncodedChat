package ca.fxco.encodedchat.utils.command;

import ca.fxco.encodedchat.EncodedChat;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class ClientPlayerArgumentType implements ArgumentType<PlayerEntity> {
    private static final Collection<String> EXAMPLES = Arrays.asList("Player", "0123", "@e", "@e[type=foo]");
    public static final SimpleCommandExceptionType PLAYER_NOT_FOUND_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("argument.entity.notfound.player"));

    protected ClientPlayerArgumentType() {}

    public static ClientPlayerArgumentType player() {
        return new ClientPlayerArgumentType();
    }

    public PlayerEntity parse(StringReader stringReader) throws CommandSyntaxException {
        PlayerEntity player = null;
        String playerName = stringReader.readUnquotedString();
        for (AbstractClientPlayerEntity playerEntity : EncodedChat.MC.world.getPlayers()) {
            if (playerEntity.getEntityName().equals(playerName)) {
                player = playerEntity;
                break;
            }
        }
        if (player == null) {
            throw PLAYER_NOT_FOUND_EXCEPTION.create();
        } else {
            return player;
        }
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        Object var4 = context.getSource();
        if (var4 instanceof CommandSource commandSource) {
            StringReader stringReader = new StringReader(builder.getInput());
            stringReader.setCursor(builder.getStart());
            EntitySelectorReader entitySelectorReader = new EntitySelectorReader(stringReader);
            try {
                entitySelectorReader.read();
            } catch (CommandSyntaxException ignored) {}
            return entitySelectorReader.listSuggestions(builder, (builderx) -> {
                CommandSource.suggestMatching(commandSource.getPlayerNames(), builderx);
            });
        } else {
            return Suggestions.empty();
        }
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public static PlayerEntity getPlayerArgument(CommandContext<ServerCommandSource> context, String name) {
        return context.getArgument(name, PlayerEntity.class);
    }
}
