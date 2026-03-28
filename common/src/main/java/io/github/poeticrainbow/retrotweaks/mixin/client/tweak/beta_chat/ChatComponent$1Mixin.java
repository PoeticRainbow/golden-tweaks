package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.beta_chat;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.gui.components.ChatComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.components.ChatComponent$1")
public class ChatComponent$1Mixin {
    @WrapMethod(method = "accept")
    private void retrotweaks$accept(GuiMessage.Line line, int i, float f, Operation<Void> original) {
        if (Tweaks.BETA_CHAT.get()) {
            original.call(line, i, 1f);
        } else {
            original.call(line, i, f);
        }
    }
}
