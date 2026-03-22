package io.github.poeticrainbow.goldentweaks.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(ChestBlock.class)
public class ChestBlockMixin {
    @Shadow @Final public static EnumProperty<ChestType> TYPE;
    @Shadow @Final protected static VoxelShape SHAPE;
    @Shadow @Final protected static Map<Direction, VoxelShape> HALF_SHAPES;
    @Shadow public static Direction getConnectedDirection(BlockState state) { return null; }

    /**
     * @author GMPDX
     * @reason Full Block Chests
     */
    @Overwrite
    public VoxelShape getShape(BlockState blockState, BlockGetter world, BlockPos pos, CollisionContext context) {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getInstance();

        if (mc == null || mc.getSingleplayerServer() == null) {
            return switch ((net.minecraft.world.level.block.state.properties.ChestType)blockState.getValue(TYPE)) {
                case SINGLE -> SHAPE;
                case LEFT, RIGHT -> (VoxelShape)HALF_SHAPES.get(getConnectedDirection(blockState));
            };
        }

        return Shapes.block();
    }
}
