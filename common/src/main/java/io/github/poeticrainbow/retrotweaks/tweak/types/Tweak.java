package io.github.poeticrainbow.retrotweaks.tweak.types;

import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class Tweak<T> {
    private final String key;
    private final T defaultValue;
    private final T disabledValue;
    private T currentValue;
    @Nullable private T serverSideValue = null;
    private final Supplier<Boolean> isFunctional;
    private final Env logicalSide;

    public static final String ERROR_TOOLTIP = "retrotweaks.error_tooltip";

    public Tweak(String key, Env logicalSide, T defaultValue, T disabledValue) {
        this(key, logicalSide, defaultValue, disabledValue, () -> Boolean.TRUE); // default true for all tweaks, functional
    }

    public Tweak(String key, Env logicalSide, T defaultValue, T disabledValue, Supplier<Boolean> isFunctional) {
        this.key = key;
        this.logicalSide = logicalSide;
        this.defaultValue = defaultValue;
        this.disabledValue = disabledValue;
        this.currentValue = defaultValue;
        this.isFunctional = isFunctional;
    }

    public T get() {
        return switch (logicalSide) {
            // clientside tweaks are always controlled by the client
            case CLIENT -> this.currentValue;
            // singleplayer or dedicated server (tweak host) : get the value from the current dedicated server (tweak client/guest)
            case SERVER -> RetroTweaks.isLogicalSide() ? this.currentValue : Objects.requireNonNullElse(this.serverSideValue, this.disabledValue);
        };
    }

    public void set(T value) {
        switch (logicalSide) {
            // clientside tweaks are always controlled by the client
            case CLIENT -> this.currentValue = value;
            case SERVER -> {
                // singleplayer or dedicated server (tweak host)
                if (RetroTweaks.isLogicalSide()) {
                    this.currentValue = value;
                }
            }
        }
    }

    public String key() {
        return this.key;
    }

    public T defaultValue() {
        return this.defaultValue;
    }

    public T disabledValue() {
        return this.disabledValue;
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
