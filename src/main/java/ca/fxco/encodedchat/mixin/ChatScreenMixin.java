package ca.fxco.encodedchat.mixin;

import ca.fxco.encodedchat.EncodedChat;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

import static ca.fxco.encodedchat.EncodedChat.MC;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {


    @Inject(
            method = "normalize",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onBeforeMessage(String chatText, CallbackInfoReturnable<String> cir) {
        if (EncodedChat.CONFIG.enabled && !chatText.isEmpty() && MC.player != null) {
            UUID playerUUID = MC.player.getUuid();
            if (EncodedChat.PLAYER_ACTIONS.hasPlayerAction(playerUUID))
                cir.setReturnValue(EncodedChat.PLAYER_ACTIONS.getPlayerAction(playerUUID).runEncode(chatText));
        }
    }
}
