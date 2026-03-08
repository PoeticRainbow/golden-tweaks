package io.github.poeticrainbow.goldentweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.poeticrainbow.goldentweaks.tweak.Tweaks;
import net.minecraft.client.renderer.SkyRenderer;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SkyRenderer.class)
public class SkyRendererMixin {
    @ModifyVariable(method = "buildStars", at = @At(value = "STORE"), name = "k")
    private static float goldentweaks$modify_star_size(float value, @Local(name = "randomSource") RandomSource source) {
        if (Tweaks.BIG_STARS.get()) {
            // modern 0.15F + randomSource.nextFloat() * 0.1F
            // beta 0.25f + source.nextFloat() * 0.25f

            // we do NOT want to roll the RandomSource again, or it will shift the random values
            var random = (value - 0.15f) * 10;
            return 0.25f + random * 0.25f;
        }
        return value;
    }
}
