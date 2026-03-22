package io.github.poeticrainbow.goldentweaks;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.goldentweaks.config.Config;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GoldenTweaks {
    public static final String MOD_ID = "goldentweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static TweakPlatform PLATFORM;
    public static void init() {
        // Write common init code here.
        Config.load();
        Config.save();

        if (Platform.getEnvironment() == Env.CLIENT) {
            GoldenTweaksClient.init();
        }
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
