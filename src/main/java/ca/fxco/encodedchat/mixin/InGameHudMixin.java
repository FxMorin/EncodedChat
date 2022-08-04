package ca.fxco.encodedchat.mixin;

import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.encodingSets.EncodingSet;
import ca.fxco.encodedchat.utils.EncodingActions;
import ca.fxco.encodedchat.utils.EncodingUtils;
import net.minecraft.client.gui.ClientChatListener;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow
    @Final
    private List<ClientChatListener> listeners;


    @Inject(
            method = "onChatMessage",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/gui/hud/InGameHud;listeners:Ljava/util/List;",
                    shift = At.Shift.BEFORE
            ),
            cancellable = true
    )
    public void onChatMessage(MessageType type, Text message, MessageSender sender, CallbackInfo ci) {
        ci.cancel();
        String msg = message.getString();
        String modifyMsg = msg;
        if (!msg.isEmpty()) {
            EncodingActions encodingActions = EncodedChat.PLAYER_ENCODING_ACTIONS.get(sender.uuid());
            if (encodingActions != null) {
                msg = encodingActions.runDecode(msg);
            } else {
                msg = EncodingUtils.attemptAutomaticDecoding(msg);
            }
        }
        Text text = !msg.equals(modifyMsg) ? Text.of(msg) : message;
        for(ClientChatListener clientChatListener : this.listeners)
            clientChatListener.onChatMessage(type, text, sender);
    }
}
