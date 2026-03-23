package io.github.poeticrainbow.goldentweaks.mixin.tweak.old_panorama_blur;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import io.github.poeticrainbow.goldentweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PanoramaRenderer.class)
public class PanoramaRendererMixin {
    @Shadow
    private Minecraft minecraft;

    /**
     * @author PoeticRainbow
     * @reason replace the panorama overlay with the old gradient one
     */
    @WrapOperation(method = "render", at = @At(target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIIII)V", value = "INVOKE"))
    private void goldentweaks$replace_panorama_overlay(GuiGraphics instance, RenderPipeline renderPipeline, Identifier arg, int i, int j, float f, float g, int k, int l, int m, int n, int o, int p, Operation<Void> original) {
        if (Tweaks.OLD_PANORAMA_BLUR.get()) {
            if (minecraft != null && minecraft.screen != null) {
                instance.fillGradient(0, 0, minecraft.screen.width, minecraft.screen.height, 0x80FFFFFF, 0x00FFFFFF);
                instance.fillGradient(0, 0, minecraft.screen.width, minecraft.screen.height, 0, 0x80000000);
            }
        } else {
            original.call(instance, renderPipeline, arg, i, j, f, g, k, l, m, n, o, p);
        }
    }
}
