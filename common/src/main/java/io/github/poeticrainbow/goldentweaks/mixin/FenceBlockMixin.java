package io.github.poeticrainbow.goldentweaks.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FenceBlock.class)
public class FenceBlockMixin extends Block {

    public FenceBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();

        if (mc == null || mc.getSingleplayerServer() == null) {
            return super.getShape(blockState, blockGetter, blockPos, collisionContext);
        }

        return Shapes.block();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();

        if (mc == null || mc.getSingleplayerServer() == null) {
            return super.getCollisionShape(state, world, pos, context);
        }

        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 24.0D, 16.0D);
    }
}
