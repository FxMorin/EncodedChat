package ca.fxco.encodedchat.mixin;

import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.encodingSets.EncodingSet;
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

import static ca.fxco.encodedchat.EncodedChat.MULTILEVEL_MODE;

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
            multilevel : while(true) {
                for (EncodingSet encodedSet : EncodedChat.ENCODING_SETS.values()) {
                    if (encodedSet.canAutomaticallyDetect() && encodedSet.hasEncoding(msg)) {
                        msg = encodedSet.decode(msg);
                        if (MULTILEVEL_MODE && encodedSet.canUseMultiLevel()) continue multilevel;
                        break multilevel;
                    }
                }
                break;
            }
        }
        Text text = !msg.equals(modifyMsg) ? Text.of(msg) : message;
        for(ClientChatListener clientChatListener : this.listeners)
            clientChatListener.onChatMessage(type, text, sender);
    }
}
