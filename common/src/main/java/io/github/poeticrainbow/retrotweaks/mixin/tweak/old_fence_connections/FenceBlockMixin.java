package io.github.poeticrainbow.retrotweaks.mixin.tweak.old_fence_connections;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FenceBlock.class)
public abstract class FenceBlockMixin extends CrossCollisionBlock {
    protected FenceBlockMixin(float f, float g, float h, float i, float j, Properties properties) {
        super(f, g, h, i, j, properties);
    }

    @WrapMethod(method = "getStateForPlacement")
    private BlockState retrotweaks$fences_connect_to_fences(BlockPlaceContext blockPlaceContext, Operation<BlockState> original) {
        if (Tweaks.OLD_FENCE_CONNECTIONS.get()) {
            var state = defaultBlockState();
            var pos = blockPlaceContext.getClickedPos();
            var level = blockPlaceContext.getLevel();

            var north = level.getBlockState(pos.north());
            var east = level.getBlockState(pos.east());
            var south = level.getBlockState(pos.south());
            var west = level.getBlockState(pos.west());

            return state.setValue(NORTH, north.is(this))
                        .setValue(EAST, east.is(this))
                        .setValue(SOUTH, south.is(this))
                        .setValue(WEST, west.is(this));
        } else {
            return original.call(blockPlaceContext);
        }
    }
}
