package io.github.poeticrainbow.retrotweaks.tweak.types;

import dev.architectury.utils.Env;

import java.util.function.Supplier;

public class BooleanTweak extends Tweak<Boolean> {
    public BooleanTweak(String key, Env logicalSide, Boolean enabledValue) {
        super(key, logicalSide, enabledValue, !enabledValue);
    }

    public BooleanTweak(String key, Env logicalSide, Boolean enabledValue, Supplier<Boolean> isFunctional) {
        super(key, logicalSide, enabledValue, !enabledValue, isFunctional);
    }

    public void toggle() {
        this.set(!this.get());
    }
}
