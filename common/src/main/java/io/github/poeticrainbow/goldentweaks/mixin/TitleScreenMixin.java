package io.github.poeticrainbow.goldentweaks.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import io.github.poeticrainbow.goldentweaks.tweak.Tweaks;

import java.lang.String;
import java.util.List;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component component) {
        super(component);
    }

    @Shadow private boolean fading;

    @Redirect(method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/TitleScreen;renderPanorama(Lnet/minecraft/client/gui/GuiGraphics;F)V"
            )
    )
    private void goldentweaks$skipPanorama(TitleScreen instance, GuiGraphics guiGraphics, float f) {
        if (Tweaks.BETA_MAIN_MENU.get()) {
            //do nothing
        } else {
            this.renderPanorama(guiGraphics, f);
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void goldentweaks$skipFadeInAndDirtBackground(GuiGraphics instance, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (Tweaks.BETA_MAIN_MENU.get()) {
            this.renderMenuBackground(instance);
            this.fading = false;
        }
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void goldentweaks$adjustButtonPositions(CallbackInfo ci) {
        if (Tweaks.BETA_MAIN_MENU.get()) {
            int startY = this.height / 4 + 48;
            int spacing = 24;

            List<Renderable> renderables = ((ScreenAccessor) this).getRenderables();

            Minecraft mc = ((ScreenAccessor) this).getMinecraft();

            renderables.removeIf(r -> r instanceof net.minecraft.client.gui.components.SpriteIconButton);
            this.children().removeIf(c -> c instanceof net.minecraft.client.gui.components.SpriteIconButton);

            for (Object obj : renderables) {
                if (obj instanceof AbstractWidget widget) {
                    String msg = widget.getMessage().getString();

                    if (msg.equals(Component.translatable("menu.singleplayer").getString())) {
                        widget.setX(this.width / 2 - 100);
                        widget.setY(startY);
                    } else if (msg.equals(Component.translatable("menu.multiplayer").getString())) {
                        widget.setX(this.width / 2 - 100);
                        widget.setY(startY + spacing);
                    } else if (msg.equals(Component.translatable("menu.options").getString())) {
                        widget.setX(this.width / 2 - 100);
                        widget.setY(startY + (spacing * 3) + 12);
                        widget.setWidth(98);
                    } else if (msg.equals(Component.translatable("menu.quit").getString())) {
                        widget.setX(this.width / 2 + 2);
                        widget.setY(startY + (spacing * 3) + 12);
                        widget.setWidth(98);
                    }
                    // handle bad options
                    else if (msg.equals(Component.translatable("menu.online").getString())) {
                        widget.visible = false;
                        widget.active = false;
                        widget.setX(-1000);
                    } else if (msg.equals(Component.literal("Create Test World").getString())) {
                        widget.visible = false;
                        widget.active = false;
                        widget.setX(-1000);
                    } else if (msg.equals(Component.translatable("fml.menu.mods").getString())) {
                        widget.visible = false;
                        widget.active = false;
                        widget.setX(-1000);
                    }
                }
            }

            this.addRenderableWidget(
                    Button.builder(Component.translatable("goldentweaks.menu.mods"), button -> {
                                mc.setScreen(new PackSelectionScreen(
                                        mc.getResourcePackRepository(),
                                        repository -> {
                                            mc.options.updateResourcePacks(repository);
                                            mc.setScreen(this);
                                        },
                                        mc.getResourcePackDirectory(),
                                        Component.translatable("resourcePack.title")
                                ));
                            })
                            .bounds(this.width / 2 - 100, startY + (spacing * 2), 200, 20)
                            .build()
            );
        }
    }
}