package io.github.poeticrainbow.retrotweaks.config.screen;

import io.github.poeticrainbow.retrotweaks.tweak.Tweak;
import io.github.poeticrainbow.retrotweaks.tweak.types.BooleanTweak;
import io.github.poeticrainbow.retrotweaks.tweak.types.EnumTweak;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

public class TweakButtonEntry extends ObjectSelectionList.Entry<@NotNull TweakButtonEntry> {
    public final Tweak<?> tweak;

    private final Button child;

    public TweakButtonEntry(Tweak<?> tweak) {
        this.tweak = tweak;
        var tooltip = Component.translatable(tweak.tooltip());
        if (!tweak.isFunctional()) {
            // if the tweak is not functional, an error occurred. mark it with red
            tooltip.setStyle(Style.EMPTY.withColor(0xFFFF0000));
        }

        this.child = Button.builder(
                               getMessage(), button -> {
                                   if (tweak instanceof BooleanTweak booleanTweak) booleanTweak.toggle();
                                   if (tweak instanceof EnumTweak<?> enumTweak) enumTweak.next();
                                   updateMessage();
                                   Minecraft.getInstance().levelRenderer.allChanged();
                               }
                           )
                           .bounds(0, 0, 220, 20)
                           .tooltip(Tooltip.create(tooltip)).build();
        child.active = tweak.isFunctional();
    }

    @Override
    public boolean mouseClicked(@NotNull MouseButtonEvent mouseButtonEvent, boolean bl) {
        return child.mouseClicked(mouseButtonEvent, bl);
    }

    public Component getMessage() {
        var value = tweak.get();
        Component message;
        if (value instanceof Enum) {
            // key is retrotweaks.enum.class_name.value_name
            var className = ((Enum<?>) value).getDeclaringClass().getSimpleName().toLowerCase();
            var valueName = value.toString().toLowerCase();
            message = Component.translatable("retrotweaks.enum." + className + "." + valueName);
        } else {
            message = Component.literal(value.toString());
        }

        return Component.literal("")
                        .append(getNarration())
                        .append(": ")
                        .append(message);
    }

    public void updateMessage() {
        child.setMessage(getMessage());
    }

    @Override
    public @NotNull Component getNarration() {
        return Component.translatable(tweak.translationKey());
    }

    @Override
    public void renderContent(@NotNull GuiGraphics graphics, int i, int j, boolean bl, float f) {
        child.setPosition(getX(), getY());
        child.render(graphics, i, j, f);
        //graphics.textRenderer().accept(TextAlignment.LEFT, i, j, this.getNarration());
    }
}
