package io.github.poeticrainbow.goldentweaks.helper;

import io.github.poeticrainbow.goldentweaks.tweak.Tweaks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class OldBlockShapesHelper {
    public static final VoxelShape FENCE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D);

    public static boolean shouldOverrideBlockShapes() {
        try {
            if (Tweaks.OLD_BLOCK_SHAPES.get()) {
                net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();
                // only override on singleplayer, for now
                return mc.getSingleplayerServer() != null;
            }
        } catch (Exception ignored) {
        }
        return false;
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
}
