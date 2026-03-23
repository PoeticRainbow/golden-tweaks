package io.github.poeticrainbow.goldentweaks.fabric.mixin;

import io.github.poeticrainbow.goldentweaks.tweak.Tweaks;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TitleScreen.class)
public class TitleScreenMixinFabric {
    @Redirect(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V",
                    ordinal = 0
            )
    )
    public void redirectVersionText(GuiGraphics instance, Font font, String text, int x, int y, int color) {
        if (Tweaks.BETA_MAIN_MENU.get()) {
            String version = Component.translatable("goldentweaks.menu.version").getString();
            if (Tweaks.DIRT_GUI_BACKGROUND.get()) {
                // render version in top left if no panorama
                instance.drawString(font, version, 2, 2, 0xFF505050);
            } else {
                // render version in bottom left if there is a panorama
                instance.drawString(font, version, x, y, color);
            }
        }
        else {
            instance.drawString(font, text, x, y, color);
        }
    }
}
