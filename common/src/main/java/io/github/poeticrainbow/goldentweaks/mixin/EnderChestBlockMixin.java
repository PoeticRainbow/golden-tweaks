package io.github.poeticrainbow.goldentweaks.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EnderChestBlock.class)
public class EnderChestBlockMixin {
    @Shadow @Final protected static VoxelShape SHAPE;

    /**
     * @author GMPDX
     * @reason Full Block Enderchest
     */
    @Overwrite
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();

        if (mc == null || mc.getSingleplayerServer() == null) {
            return SHAPE;
        }

        return Shapes.block();
    }
}
