package io.github.poeticrainbow.goldentweaks.plugin;

import dev.architectury.platform.Platform;

import java.util.function.Function;
import java.util.function.Predicate;

public record MixinModCondition(String mixinClassPrefix, String modId) implements Predicate<String>, Function<String, Boolean> {
    public boolean test(String mixinClassName) {
        return mixinClassName.startsWith(mixinClassPrefix);
    }

    @Override
    public Boolean apply(String mixinClassName) {
        return Platform.isModLoaded(mixinClassName);
    }
}
