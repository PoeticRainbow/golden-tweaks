package io.github.poeticrainbow.retrotweaks.tweak.types;

import io.github.poeticrainbow.retrotweaks.tweak.Tweak;

import java.util.function.Supplier;

public class BooleanTweak extends Tweak<Boolean> {
    public BooleanTweak(String key, Boolean defaultValue) {
        super(key, defaultValue);
    }

    public BooleanTweak(String key, Boolean defaultValue, Supplier<Boolean> isFunctional) {
        super(key, defaultValue, isFunctional);
    }

    public void toggle() {
        this.set(!this.get());
    }
}
