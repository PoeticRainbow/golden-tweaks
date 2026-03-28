package io.github.poeticrainbow.retrotweaks.mixin.client.tweak.beta_chat;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {
    protected ChatScreenMixin(Component component) {
        super(component);
    }

    @Shadow protected EditBox input;
    @Unique public int retrotweaks$updateCounter;

    @WrapMethod(method = "render")
    public void retrotweaks$render(GuiGraphics guiGraphics, int i, int j, float f, Operation<Void> original) {
        if (Tweaks.BETA_CHAT.get()) {
            guiGraphics.fill(2, this.height - 14, this.width - 2, this.height - 2, this.minecraft.options.getBackgroundColor(Integer.MIN_VALUE));
            this.minecraft.gui.getChat().render(guiGraphics, this.font, this.minecraft.gui.getGuiTicks(), i, j, true, this.minecraft.hasShiftDown());
            guiGraphics.drawString(this.font, "> " + this.input.getValue() + (this.retrotweaks$updateCounter / 6 % 2 == 0 ? "_" : ""), 4, this.height - 12, 0xFFE0E0E0);
        } else {
            original.call(guiGraphics, i, j, f);
        }
    }

    public void tick() {
        this.retrotweaks$updateCounter++;
    }
}
