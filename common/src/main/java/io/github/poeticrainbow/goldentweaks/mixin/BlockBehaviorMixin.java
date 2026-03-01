package io.github.poeticrainbow.goldentweaks.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.poeticrainbow.goldentweaks.config.Tweaks;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.class)
public abstract class BlockBehaviorMixin {
    /*
     * Handles the old Ambient Occlusion and old Leaves lighting
     */
    @ModifyReturnValue(method = "getShadeBrightness", at = @At("RETURN"))
    private float goldentweaks$old_ambient_occlusion(float original, BlockState state) {
        if (Tweaks.BETA_LEAVES_LIGHTING.get() && state.is(BlockTags.LEAVES)) {
            return 1f;
        }
        if (Tweaks.DARK_AMBIENT_OCCLUSION.get()) {
            return original == 0.2f ? 0f : original;
        }
        return original;
    }
}
