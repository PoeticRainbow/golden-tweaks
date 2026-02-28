package io.github.poeticrainbow.goldentweaks;

import io.github.poeticrainbow.goldentweaks.config.Config;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GoldenTweaks {
    public static final String MOD_ID = "goldentweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static Config CONFIG;

    public static void init() {
        // Write common init code here.

        CONFIG = Config.load().orElseThrow();
        CONFIG.save();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
