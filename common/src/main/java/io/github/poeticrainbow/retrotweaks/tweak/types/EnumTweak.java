package io.github.poeticrainbow.retrotweaks.tweak.types;

import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.tweak.Tweak;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class EnumTweak<T extends Enum<T>> extends Tweak<Enum<T>> {
    private final List<T> values;

    public EnumTweak(String key, Enum<T> defaultValue) {
        this(key, defaultValue, defaultValue.getDeclaringClass().getEnumConstants());
    }

    public EnumTweak(String key, Enum<T> defaultValue, T[] allowedValues) {
        this(key, defaultValue, List.of(allowedValues));
    }

    public EnumTweak(String key, Enum<T> defaultValue, List<T> allowedValues) {
        super(key, defaultValue);
        this.values = allowedValues;
    }

    @Override
    public void set(Enum<T> value) {
        if (values.contains(value)) {
            super.set(value);
        } else {
            RetroTweaks.LOGGER.error(
                "Tried to set tweak to value {} which is not allowed (allowed: {})",
                value,
                StringUtils.join(values, ", ")
            );
        }
    }

    public void next() {
        var index = values.indexOf(this.get());
        var newValue = values.get((index + 1) % values.size());
        this.set(newValue);
    }
}
