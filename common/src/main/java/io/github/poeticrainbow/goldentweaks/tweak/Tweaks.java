package io.github.poeticrainbow.goldentweaks.tweak;

import io.github.poeticrainbow.goldentweaks.GoldenTweaks;
import io.github.poeticrainbow.goldentweaks.tweak.types.BooleanTweak;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tweaks {
    public static final Map<String, Tweak<?>> REGISTRY = new LinkedHashMap<>(); // order of inserted elements maintained

    public static final Tweak<Boolean> BETA_LEAVES_LIGHTING = register(new BooleanTweak("beta_leaves_lighting", true));
    public static final Tweak<Boolean> DARK_AMBIENT_OCCLUSION = register(new BooleanTweak("dark_ambient_occlusion", true));
    public static final Tweak<Boolean> FULL_FACE_SHADING = register(new BooleanTweak("full_face_shading", true, GoldenTweaks.PLATFORM::isVanillaAo));
    public static final Tweak<Boolean> BIG_STARS = register(new BooleanTweak("big_stars", true));


    public static <V> Tweak<V> register(Tweak<V> tweak) {
        REGISTRY.put(tweak.key(), tweak);
        return tweak;
    }

    public static Collection<Tweak<?>> values() {
        return REGISTRY.values();
    }

    public static Tweak<?> get(String key) {
        return REGISTRY.get(key);
    }
}
