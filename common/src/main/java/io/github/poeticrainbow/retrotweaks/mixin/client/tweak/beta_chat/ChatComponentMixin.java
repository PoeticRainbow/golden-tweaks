package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.beta_chat;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.GuiMessage;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Objects;

@Mixin(ChatComponent.class)
public abstract class ChatComponentMixin {

    @Shadow @Final protected abstract boolean isChatHidden();
    @Shadow @Final private List<GuiMessage.Line> trimmedMessages;
    @Shadow @Final protected abstract double getScale();
    @Shadow @Final protected abstract int getWidth();
    @Shadow @Final protected Minecraft minecraft;

    @Redirect(method = "method_75802", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;fill(IIIII)V"))
    private static void retrotweaks$chat_fill(ChatComponent.ChatGraphicsAccess instance, int i, int j, int g, int k, int l) {
        if (Tweaks.BETA_CHAT.get()) {
            instance.fill(-2, j, g, k, l);
        } else {
            instance.fill(i, j, g, k, l);
        }
    }
    @Redirect(method = "render(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;fill(IIIII)V", ordinal = 2))
    private void retrotweaks$remove_scrollbar1(ChatComponent.ChatGraphicsAccess instance, int i, int j, int g, int k, int l) {
        if (!Tweaks.BETA_CHAT.get()) {
            instance.fill(i, j, g, k, l);
        }
    }
    @Redirect(method = "render(Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;IIZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent$ChatGraphicsAccess;fill(IIIII)V", ordinal = 1))
    private void retrotweaks$remove_scrollbar2(ChatComponent.ChatGraphicsAccess instance, int i, int j, int g, int k, int l) {
        if (!Tweaks.BETA_CHAT.get()) {
            instance.fill(i, j, g, k, l);
        }
    }
}


