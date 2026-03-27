package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.old_hitbox_shapes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.retrotweaks.util.OldBlockShapesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.EnderChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnderChestBlock.class)
public abstract class EnderChestBlockMixin{
    @ModifyReturnValue(method = "getShape", at = @At("RETURN"))
    public VoxelShape retrotweaks$get_shape(VoxelShape original, BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OldBlockShapesHelper.getFullBlockShape().orElse(original);
    }
}
