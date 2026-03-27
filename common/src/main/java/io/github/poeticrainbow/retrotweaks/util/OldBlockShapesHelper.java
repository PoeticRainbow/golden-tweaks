package io.github.poeticrainbow.retrotweaks.util;

import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class OldBlockShapesHelper {
    public static final VoxelShape FENCE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D);

    public static boolean shouldOverrideBlockShapes() {
        return Tweaks.OLD_HITBOX_SHAPES.get();
    }

    public static Optional<VoxelShape> getFullBlockShape() {
        if (shouldOverrideBlockShapes()) {
            return Optional.of(Shapes.block());
        }
        return Optional.empty();
    }

    public static Optional<VoxelShape> getFenceShape() {
        if (shouldOverrideBlockShapes()) {
            return Optional.of(FENCE_SHAPE);
        }
        return Optional.empty();
    }

    public static Optional<VoxelShape> getEmptyShape() {
        if (shouldOverrideBlockShapes()) {
            return Optional.of(Shapes.empty());
        }
        return Optional.empty();
    }
}
