package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.old_hitbox_shapes;

import io.github.poeticrainbow.retrotweaks.util.OldBlockShapesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FenceBlock.class)
public abstract class FenceBlockMixin extends Block {
    public FenceBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return OldBlockShapesHelper.getFullBlockShape()
                                   .orElse(super.getShape(state, world, pos, context));
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return OldBlockShapesHelper.getFenceShape()
                                   .orElse(super.getCollisionShape(state, world, pos, context));
    }
}
