package io.github.poeticrainbow.goldentweaks.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.poeticrainbow.goldentweaks.helper.BillboardHelper;
import io.github.poeticrainbow.goldentweaks.tweak.Tweaks;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin {
    @Redirect(
        method = "submit(Lnet/minecraft/client/renderer/entity/state/ItemEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;getSpin(FF)F")
    )
    private float goldentweaks$flat_items_billboard_yaw(float f, float g, @Local(argsOnly = true) ItemEntityRenderState state) {
        try {
            // ignore items that use block lighting as they are usually blocks! duhh
            if (Tweaks.FLAT_ITEMS.get() && !state.item.usesBlockLight()) {
                return BillboardHelper.billboardYawRadians();
            }
        } catch (Exception ignored) {
        }
        return ItemEntity.getSpin(f, g);
    }
}
