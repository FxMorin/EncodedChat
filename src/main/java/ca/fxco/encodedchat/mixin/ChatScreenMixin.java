package ca.fxco.encodedchat.mixin;

import ca.fxco.encodedchat.EncodedChat;
import net.minecraft.client.gui.screen.ChatScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {


    @Inject(
            method = "normalize",
            at = @At("HEAD"),
            cancellable = true
    )
    public void onBeforeMessage(String chatText, CallbackInfoReturnable<String> cir) {
        if (!chatText.isEmpty()) cir.setReturnValue(EncodedChat.SELF_ENCODING_ACTIONS.runEncode(chatText));
    }
}
