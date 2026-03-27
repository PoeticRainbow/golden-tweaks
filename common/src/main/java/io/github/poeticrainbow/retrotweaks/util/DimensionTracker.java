package io.github.poeticrainbow.retrotweaks.util;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import org.jetbrains.annotations.Nullable;

public class DimensionTracker {
    @Nullable
    private static Identifier previousDimension;
    @Nullable
    private static Identifier currentDimension;

    public static Identifier previousDimension() {
        return previousDimension == null ? BuiltinDimensionTypes.OVERWORLD.identifier() : previousDimension;
    }

    public static Component previousDimensionTranslation() {
        var id = previousDimension();
        return Component.translatable("dimension." + id.getNamespace() + "." + id.getPath());
    }

    public static void updateDimension(@Nullable Identifier newDimension) {
        // prev <- current <- new
        DimensionTracker.previousDimension = currentDimension;
        DimensionTracker.currentDimension = newDimension;
    }
}
