package ca.fxco.encodedchat.mixin;

import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.actions.EncodingActions;
import ca.fxco.encodedchat.utils.EncodingUtils;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MessageHandler.class)
public class MessageHandlerMixin {


    @Redirect(
            method = "processChatMessageInternal",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;" +
                            "Lnet/minecraft/network/message/MessageSignatureData;" +
                            "Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
                    ordinal = 0
            )
    )
    private void modifyChatMessages(ChatHud instance, Text message, MessageSignatureData signature,
                                    MessageIndicator indicator, MessageType.Parameters params, SignedMessage message2,
                                    Text decorated, @Nullable PlayerListEntry senderEntry) {
        if (senderEntry != null) {
            String msg = message.getString();
            String modifyMsg = msg;
            if (!msg.isEmpty()) {
                EncodingActions actions = EncodedChat.PLAYER_ENCODING_ACTIONS.get(senderEntry.getProfile().getId());
                if (actions != null) {
                    msg = actions.runDecode(msg);
                } else {
                    msg = EncodingUtils.attemptAutomaticDecoding(msg);
                }
            }
            instance.addMessage(!msg.equals(modifyMsg) ? Text.of(msg) : message, signature, indicator);
        } else {
            instance.addMessage(message, signature, indicator);
        }
    }
}
