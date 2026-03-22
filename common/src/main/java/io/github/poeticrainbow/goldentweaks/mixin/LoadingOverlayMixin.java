package io.github.poeticrainbow.goldentweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.goldentweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import static io.github.poeticrainbow.goldentweaks.GoldenTweaks.MOD_ID;

@Mixin(LoadingOverlay.class)
public abstract class LoadingOverlayMixin {
    @Shadow private long fadeOutStart;
    @Shadow private long fadeInStart;
    @Shadow private boolean fadeIn;

    @Unique
    private static final Identifier BETA_MOJANG_LOGO = Identifier.fromNamespaceAndPath(MOD_ID, "textures/gui/logo.png");

    /**
     * @author GMPDX
     * @reason Beta Mojang Logo
     */
    @WrapMethod(method = "render")
    public void goldentweaks$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, Operation<Void> original) {
        if (Tweaks.BETA_MOJANG_LOGO.get()) {
            int width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
            int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
            long now = Util.getMillis();

            if (this.fadeIn && this.fadeInStart == -1L) {
                this.fadeInStart = now - 5000L;
            }

            guiGraphics.fill(RenderPipelines.GUI, 0, 0, width, height, 0xFFFFFFFF);

            int x = (int) ((width / 4.0D) - (128 / 2.0D));
            int y = (int) ((height / 4.0D) - (128 / 2.0D));

            guiGraphics.pose().pushMatrix();
            guiGraphics.pose().scale(2F, 2F);

            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BETA_MOJANG_LOGO, x, y, 0, 0, 128, 128, 128, 128);

            guiGraphics.pose().popMatrix();

            if (this.fadeOutStart != -1L) {
                float fadeOutPercentage = (float) (now - this.fadeOutStart) / 1000.0F;
                if (fadeOutPercentage >= 2.0F) {
                    net.minecraft.client.Minecraft.getInstance().setOverlay(null);
                }
            }
        } else {
            original.call(guiGraphics, mouseX, mouseY, partialTick);
        }
    }
}