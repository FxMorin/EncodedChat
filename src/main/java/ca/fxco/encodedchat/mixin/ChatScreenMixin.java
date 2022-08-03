package ca.fxco.encodedchat.mixin;

import ca.fxco.encodedchat.EncodedChat;
import ca.fxco.encodedchat.encodingSets.EncodingSet;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {

    /*
    TODO:
        - Add multi-level encoding support
     */


    @Inject(
            method = "normalize",
            at = @At("HEAD"),
            cancellable = true
    )
    public void normalize(String chatText, CallbackInfoReturnable<String> cir) {
        if (!chatText.isEmpty()) {
            boolean modified = false;
            for (EncodingSet encodedSet : EncodedChat.ENCODING_SETS.values()) {
                if (encodedSet.canAutomaticallyDetect() && encodedSet.canEncode(chatText)) {
                    chatText = encodedSet.encode(chatText);
                    modified = true;
                    break;
                }
            }
            if (modified) cir.setReturnValue(chatText);
        }
    }
}
