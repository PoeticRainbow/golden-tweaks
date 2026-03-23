package io.github.poeticrainbow.retrotweaks.config.screen;

import io.github.poeticrainbow.retrotweaks.ErrorCollector;
import io.github.poeticrainbow.retrotweaks.config.Config;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConfigScreen extends Screen {
    private List<String> errors;

    public static final int sidebarWidth = 240;

    private Screen parent;

    public ConfigScreen() {
        super(Component.translatable("gui.retrotweaks.config"));
    }

    public ConfigScreen(Screen parent) {
        super(Component.translatable("gui.retrotweaks.config"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        var padding = 20;
        //private HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this, 40, 40);
        TweakButtonList list = new TweakButtonList(minecraft, sidebarWidth, height - padding);
        errors = ErrorCollector.checkForErrors();

        //layout.addTitleHeader(getTitle(), getFont());
        //layout.addToContents(list);
        //layout.addToFooter(new Button.Builder(CommonComponents.GUI_DONE, button -> {
        //    Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(null));
        //}).build());
        //layout.arrangeElements();

        addRenderableWidget(list);
        addRenderableWidget(
            new Button.Builder(CommonComponents.GUI_DONE, button -> this.onClose())
                .bounds((sidebarWidth - 220) / 2, height - padding, 220, padding).build()
        );
    }

    @Override
    public void onClose() {
        Config.save();
        if (parent != null) {
            minecraft.setScreen(parent);
        } else {
            super.onClose();
        }
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int i, int j, float f) {
        if (minecraft.level == null) {
            super.renderBackground(guiGraphics, i, j, f);
        } else {
            this.minecraft.gui.renderDeferredSubtitles();
        }
    }

    public void renderErrors(@NotNull GuiGraphics graphics, int i, int j, float f) {
        if (!errors.isEmpty()) {
            for (String error : errors) {
                graphics.drawWordWrap(getFont(), FormattedText.of(error), sidebarWidth, 0, width - sidebarWidth, 0xFFFF0000);
            }
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int i, int j, float f) {
        //graphics.drawCenteredString(getFont(), getTitle(), width / 2, 0, -1);
        //renderMenuBackground(graphics);


        //layout.visitChildren(layoutElement -> layoutElement.visitWidgets(abstractWidget -> abstractWidget.render(graphics, i, j, f)));

        super.render(graphics, i, j, f);
        renderErrors(graphics, i, j, f);
    }
}
