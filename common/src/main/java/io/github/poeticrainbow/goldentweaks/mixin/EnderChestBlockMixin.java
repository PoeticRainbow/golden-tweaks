package io.github.poeticrainbow.goldentweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderChestBlock.class)
public class EnderChestBlockMixin {
    @ModifyReturnValue(method = "getShape", at = @At("RETURN"))
    public VoxelShape goldentweaks$getShape(VoxelShape original, BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();

        if (mc == null || mc.getSingleplayerServer() == null) {
            return original;
        }

        return Shapes.block();
    }
}
