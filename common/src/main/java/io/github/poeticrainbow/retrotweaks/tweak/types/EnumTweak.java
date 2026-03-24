package io.github.poeticrainbow.retrotweaks.tweak.types;

import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class EnumTweak<T extends Enum<T>> extends Tweak<Enum<T>> {
    private final List<T> values;

    public EnumTweak(String key, Env logicalSide, Enum<T> defaultValue, Enum<T> disabledValue) {
        this(key, logicalSide, defaultValue, disabledValue, defaultValue.getDeclaringClass().getEnumConstants());
    }

    public EnumTweak(String key, Env logicalSide, Enum<T> defaultValue, Enum<T> disabledValue, T[] allowedValues) {
        this(key, logicalSide, defaultValue, disabledValue, List.of(allowedValues));
    }

    public EnumTweak(String key, Env logicalSide, Enum<T> defaultValue, Enum<T> disabledValue, List<T> allowedValues) {
        super(key, logicalSide, defaultValue, disabledValue);
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
