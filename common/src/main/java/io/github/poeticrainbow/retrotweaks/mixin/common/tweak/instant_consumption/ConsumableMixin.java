package io.github.poeticrainbow.retrotweaks.mixin.common.tweak.instant_consumption;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.datafixers.util.Either;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(Consumable.class)
public abstract class ConsumableMixin {
    @Shadow public abstract boolean canConsume(LivingEntity livingEntity, ItemStack itemStack);
    @Shadow public abstract ItemStack onConsume(Level level, LivingEntity livingEntity, ItemStack itemStack);

    @Mutable
    @Shadow
    @Final
    private Holder<SoundEvent> sound;

    @WrapMethod(method = "startConsuming")
    public InteractionResult retrotweaks$start_consuming(LivingEntity livingEntity, ItemStack itemStack, InteractionHand interactionHand, Operation<InteractionResult> original) {
        if (Tweaks.INSTANT_CONSUMPTION.get()) {
            if (!this.canConsume(livingEntity, itemStack)) {
                return InteractionResult.FAIL;
            } else {
                ItemStack itemStack2 = this.onConsume(livingEntity.level(), livingEntity, itemStack);
                return InteractionResult.CONSUME.heldItemTransformedTo(itemStack2);
            }
        } else {
            return original.call(livingEntity, itemStack, interactionHand);
        }
    }

    @WrapMethod(method = "emitParticlesAndSounds")
    public void retrotweaks$dont_emit_particles_and_sounds(RandomSource randomSource, LivingEntity livingEntity, ItemStack itemStack, int i, Operation<Void> original) {
        if (Tweaks.INSTANT_CONSUMPTION.get()) {
            //do nothing
        } else {
            original.call(randomSource, livingEntity, itemStack, i);
        }
    }

    @Redirect(method = "onConsume", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;gameEvent(Lnet/minecraft/core/Holder;)V"))
    public void retrotweaks$use_no_anim(LivingEntity instance, Holder holder) {
        if (Tweaks.INSTANT_CONSUMPTION.get()) {
            // do nothing i think
        } else {
            instance.gameEvent(holder);
        }
    }

    @WrapMethod(method = "sound")
    public Holder<SoundEvent> sound(Operation<Holder<SoundEvent>> original) {
        if (Tweaks.INSTANT_CONSUMPTION.get()) {
            this.sound = new Holder<SoundEvent>() {
                @Override
                public SoundEvent value() {
                    return null;
                }

                @Override
                public boolean isBound() {
                    return false;
                }

                @Override
                public boolean is(Identifier identifier) {
                    return false;
                }

                @Override
                public boolean is(ResourceKey<SoundEvent> resourceKey) {
                    return false;
                }

                @Override
                public boolean is(Predicate<ResourceKey<SoundEvent>> predicate) {
                    return false;
                }

                @Override
                public boolean is(TagKey<SoundEvent> tagKey) {
                    return false;
                }

                @Override
                public boolean is(Holder<SoundEvent> holder) {
                    return false;
                }

                @Override
                public Stream<TagKey<SoundEvent>> tags() {
                    return Stream.empty();
                }

                @Override
                public Either<ResourceKey<SoundEvent>, SoundEvent> unwrap() {
                    return null;
                }

                @Override
                public Optional<ResourceKey<SoundEvent>> unwrapKey() {
                    return Optional.empty();
                }

                @Override
                public Kind kind() {
                    return null;
                }

                @Override
                public boolean canSerializeIn(HolderOwner<SoundEvent> holderOwner) {
                    return false;
                }
            };
            return this.sound;
        } else {
            return original.call();
        }
    }
}
