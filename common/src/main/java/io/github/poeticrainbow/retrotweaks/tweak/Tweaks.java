package io.github.poeticrainbow.retrotweaks.tweak;

import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.enums.Versions;
import io.github.poeticrainbow.retrotweaks.tweak.types.BooleanTweak;
import io.github.poeticrainbow.retrotweaks.tweak.types.EnumTweak;
import io.github.poeticrainbow.retrotweaks.tweak.types.Tweak;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tweaks {
    public static final Map<String, Tweak<?>> REGISTRY = new LinkedHashMap<>(); // order of inserted elements maintained

    // CLIENT SIDE
    public static final Tweak<Boolean> BETA_LEAVES_LIGHTING = register(new BooleanTweak("beta_leaves_lighting", Env.CLIENT, true));
    public static final Tweak<Boolean> DARK_AMBIENT_OCCLUSION = register(new BooleanTweak("dark_ambient_occlusion", Env.CLIENT, true));
    public static final Tweak<Boolean> FULL_FACE_SHADING = register(new BooleanTweak("full_face_shading", Env.CLIENT, true, RetroTweaks::isVanillaAo));
    public static final Tweak<Boolean> BIG_STARS = register(new BooleanTweak("big_stars", Env.CLIENT, true));
    public static final Tweak<Boolean> FLAT_ITEMS = register(new BooleanTweak("flat_items", Env.CLIENT, true));
    public static final Tweak<Boolean> OLD_FOOTSTEPS = register(new BooleanTweak("old_footsteps", Env.CLIENT, true));
    public static final Tweak<Boolean> BETA_MAIN_MENU = register(new BooleanTweak("beta_main_menu", Env.CLIENT, true));
    public static final Tweak<Boolean> BETA_PAUSE_MENU = register(new BooleanTweak("beta_pause_menu", Env.CLIENT, true));
    public static final Tweak<Enum<Versions>> LOADING_SCREEN = registerEnum(new EnumTweak<>("loading_screen", Env.CLIENT, Versions.BETA, Versions.RELEASE, Versions.MAIN_VERSIONSINFDEV));
    public static final Tweak<Boolean> DIRT_GUI_BACKGROUND = register(new BooleanTweak("dirt_gui_background", Env.CLIENT, true));
    public static final Tweak<Boolean> OLD_PANORAMA_BLUR = register(new BooleanTweak("old_panorama_blur", Env.CLIENT, true));
    public static final Tweak<Boolean> OLD_BUTTON_COLORS = register(new BooleanTweak("old_button_colors", Env.CLIENT, true));

    // todo: eventually split tweaks into tweaks for clientside, and gamerules/similar for common
    // SERVER SIDE
    public static final Tweak<Boolean> DARK_WATER_LIGHTING = register(new BooleanTweak("dark_water_lighting", Env.SERVER, true));
    public static final Tweak<Boolean> OLD_HITBOX_SHAPES = register(new BooleanTweak("old_hitbox_shapes", Env.SERVER, true));
    public static final Tweak<Boolean> OLD_FENCE_CONNECTIONS = register(new BooleanTweak("old_fence_connections", Env.SERVER, true));

    //public static final Tweak<Enum<Versions>> TEST = registerEnum(new EnumTweak<>("test", Versions.BETA));

    private static <V extends Enum<V>> Tweak<Enum<V>> registerEnum(Tweak<Enum<V>> tweak) {
        REGISTRY.put(tweak.key(), tweak);
        return tweak;
    }

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
