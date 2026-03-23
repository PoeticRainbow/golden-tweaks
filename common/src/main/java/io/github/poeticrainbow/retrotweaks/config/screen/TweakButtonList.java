package io.github.poeticrainbow.retrotweaks.config.screen;

import io.github.poeticrainbow.retrotweaks.tweak.Tweak;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import org.jetbrains.annotations.NotNull;

public class TweakButtonList extends ObjectSelectionList<@NotNull TweakButtonEntry> {
    public TweakButtonList(Minecraft minecraft, int width, int height) {
        super(minecraft, width, height, 0, 0);

        for (Tweak<?> tweak : Tweaks.values()) {
            addEntry(new TweakButtonEntry(tweak), 20);
        }
    }
}
