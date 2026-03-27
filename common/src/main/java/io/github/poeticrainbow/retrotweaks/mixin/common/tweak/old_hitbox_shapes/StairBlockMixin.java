package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.old_hitbox_shapes;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.retrotweaks.util.OldBlockShapesHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;

@Mixin(StairBlock.class)
public abstract class StairBlockMixin extends Block {
    @Shadow @Final public static EnumProperty<@NotNull Half> HALF;
    @Shadow @Final public static EnumProperty<@NotNull Direction> FACING;
    @Shadow @Final public static EnumProperty<@NotNull StairsShape> SHAPE;

    @Shadow @Final private static Map<Direction, VoxelShape> SHAPE_BOTTOM_STRAIGHT;
    @Shadow @Final private static Map<Direction, VoxelShape> SHAPE_TOP_STRAIGHT;

    @Shadow @Final private static Map<Direction, VoxelShape> SHAPE_BOTTOM_OUTER;
    @Shadow @Final private static Map<Direction, VoxelShape> SHAPE_TOP_OUTER;

    @Shadow @Final private static Map<Direction, VoxelShape> SHAPE_BOTTOM_INNER;
    @Shadow @Final private static Map<Direction, VoxelShape> SHAPE_TOP_INNER;

    public StairBlockMixin(Properties properties) {
        super(properties);
    }

    @ModifyReturnValue(method = "getShape", at = @At("RETURN"))
    public VoxelShape retrotweaks$get_shape(VoxelShape original, BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OldBlockShapesHelper.getFullBlockShape().orElse(original);
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (OldBlockShapesHelper.shouldOverrideBlockShapes()) {
            // reimplementation of vanilla due to modifying getShape method
            boolean bl = state.getValue(HALF) == Half.BOTTOM;
            var directionToShape = switch (state.getValue(SHAPE)) {
                case STRAIGHT -> bl ? SHAPE_BOTTOM_STRAIGHT : SHAPE_TOP_STRAIGHT;
                case OUTER_LEFT, OUTER_RIGHT -> bl ? SHAPE_BOTTOM_OUTER : SHAPE_TOP_OUTER;
                case INNER_RIGHT, INNER_LEFT -> bl ? SHAPE_BOTTOM_INNER : SHAPE_TOP_INNER;
            };

            Direction direction = state.getValue(FACING);
            return directionToShape.get(switch (state.getValue(SHAPE)) {
                case STRAIGHT, OUTER_LEFT, INNER_RIGHT -> direction;
                case INNER_LEFT -> direction.getCounterClockWise();
                case OUTER_RIGHT -> direction.getClockWise();
            });
        } else {
            return super.getCollisionShape(state, getter, pos, context);
        }
    }
}
