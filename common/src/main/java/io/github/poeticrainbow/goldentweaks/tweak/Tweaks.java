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
    public static final Tweak<Boolean> FULL_FACE_SHADING = register(new BooleanTweak("full_face_shading", true, () -> {
        // directly referencing GoldenTweaks.PLATFORM will cause a
        // crash on launch while the block shape cache is being built
        try {
            return GoldenTweaks.PLATFORM.isVanillaAo();
        } catch (Exception e) {
            return true;
        }
    }));
    public static final Tweak<Boolean> BIG_STARS = register(new BooleanTweak("big_stars", true));
    public static final Tweak<Boolean> FLAT_ITEMS = register(new BooleanTweak("flat_items", true));
    public static final Tweak<Boolean> DARK_WATER_LIGHTING = register(new BooleanTweak("dark_water_lighting", true));
    public static final Tweak<Boolean> OLD_FOOTSTEPS = register(new BooleanTweak("old_footsteps", true));
    public static final Tweak<Boolean> BETA_MAIN_MENU = register(new BooleanTweak("beta_main_menu", true));
    public static final Tweak<Boolean> BETA_MOJANG_LOGO = register(new BooleanTweak("beta_mojang_logo", true));
    public static final Tweak<Boolean> DIRT_GUI_BACKGROUND = register(new BooleanTweak("dirt_gui_background", true));
    public static final Tweak<Boolean> OLD_PANORAMA_BLUR = register(new BooleanTweak("old_panorama_blur", true));
    // todo: eventually split tweaks into tweaks for clientside, and gamerules/similar for common
    public static final Tweak<Boolean> OLD_BLOCK_SHAPES = register(new BooleanTweak("old_block_shapes", true));


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
