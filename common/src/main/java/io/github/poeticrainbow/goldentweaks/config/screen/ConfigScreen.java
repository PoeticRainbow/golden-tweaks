package io.github.poeticrainbow.goldentweaks.config.screen;

import io.github.poeticrainbow.goldentweaks.config.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ConfigScreen extends Screen {
    //private HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 40, 40);
    private TweakButtonList list;

    public static final int sidebarWidth = 240;

    public ConfigScreen() {
        super(Component.translatable("gui.goldentweaks.config"));
    }

    @Override
    protected void init() {
        var padding = 20;
        list = new TweakButtonList(minecraft, sidebarWidth, height - padding);

        //layout.addTitleHeader(getTitle(), getFont());
        //layout.addToContents(list);
        //layout.addToFooter(new Button.Builder(CommonComponents.GUI_DONE, button -> {
        //    Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(null));
        //}).build());
        //layout.arrangeElements();

        addRenderableWidget(list);
        addRenderableWidget(
            new Button.Builder(CommonComponents.GUI_DONE, button -> this.onClose())
                .bounds(0, height - padding, sidebarWidth, padding).build()
        );
    }

    @Override
    public void onClose() {
        super.onClose();
        Config.save();
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {

        this.minecraft.gui.renderDeferredSubtitles();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int i, int j, float f) {
        graphics.drawCenteredString(getFont(), getTitle(), 0, 0, 0);
        //renderMenuBackground(graphics);

        //layout.visitChildren(layoutElement -> layoutElement.visitWidgets(abstractWidget -> abstractWidget.render(graphics, i, j, f)));

        super.render(graphics, i, j, f);
    }
}
