package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.remove_experience_bar;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ContextualBarRenderer.class)
public interface ContextualBarRendererMixin {
    @WrapMethod(method = "renderExperienceLevel")
    private static void renderExperienceLevel(GuiGraphics guiGraphics, Font font, int i, Operation<Void> original) {
        if (!Tweaks.REMOVE_XP_BAR.get()) {
            original.call(guiGraphics, font, i);
        }
    }
}
