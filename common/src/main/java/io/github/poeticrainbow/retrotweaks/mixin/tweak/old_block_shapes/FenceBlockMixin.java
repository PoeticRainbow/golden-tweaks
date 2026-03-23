package io.github.poeticrainbow.retrotweaks.mixin.tweak.old_block_shapes;

import io.github.poeticrainbow.retrotweaks.helper.OldBlockShapesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FenceBlock.class)
public abstract class FenceBlockMixin extends Block {

    public FenceBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OldBlockShapesHelper.getFullBlockShape()
                                   .orElseGet(() -> super.getShape(state, world, pos, context));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OldBlockShapesHelper.getFenceShape()
                                   .orElseGet(() -> super.getCollisionShape(state, world, pos, context));
    }
}
