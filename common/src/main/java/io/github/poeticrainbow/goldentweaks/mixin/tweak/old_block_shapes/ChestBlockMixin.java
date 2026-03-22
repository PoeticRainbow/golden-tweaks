package io.github.poeticrainbow.goldentweaks.mixin.tweak.old_block_shapes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.goldentweaks.helper.OldBlockShapesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {
    @ModifyReturnValue(method = "getShape", at = @At("RETURN"))
    public VoxelShape goldentweaks$getShape(VoxelShape original, BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OldBlockShapesHelper.getFullBlockShape().orElse(original);
    }
}
