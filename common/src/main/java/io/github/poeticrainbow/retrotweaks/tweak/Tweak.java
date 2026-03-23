package io.github.poeticrainbow.retrotweaks.tweak;

import java.util.function.Supplier;

public class Tweak<T> {
    private final String key;
    private final T defaultValue;
    private T value;
    private final Supplier<Boolean> isFunctional;

    public static final String ERROR_TOOLTIP = "retrotweaks.error_tooltip";

    public Tweak(String key, T defaultValue) {
        this(key, defaultValue, () -> Boolean.TRUE); // default true for all tweaks, functional
    }

    public Tweak(String key, T defaultValue, Supplier<Boolean> isFunctional) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.isFunctional = isFunctional;
    }

    public T get() {
        return this.value;
    }

    public void set(T value) {
        this.value = value;
    }

    public String key() {
        return this.key;
    }

    public T defaultValue() {
        return this.defaultValue;
    }

    public String translationKey() {
        return "retrotweaks.tweak." + this.key();
    }

    public String tooltip() {
        return isFunctional() ? this.translationKey() + ".tooltip" : ERROR_TOOLTIP;
    }

    public boolean isFunctional() {
        return isFunctional.get();
    }
}
