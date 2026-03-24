package io.github.poeticrainbow.retrotweaks;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import io.github.poeticrainbow.retrotweaks.config.Config;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RetroTweaks {
    public static final String MOD_ID = "retrotweaks";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static TweakPlatform PLATFORM;

    public static void init() {
        // Write common init code here.
        Config.load();
        Config.save();

        if (Platform.getEnvironment() == Env.CLIENT) {
            RetroTweaksClient.init();
        }
    }

    public static boolean isClient() {
        return Platform.getEnvironment().equals(Env.CLIENT);
    }

    public static boolean isServer() {
        return Platform.getEnvironment().equals(Env.SERVER);
    }

    public static boolean isVanillaAo() {
        try {
            return RetroTweaks.PLATFORM.isVanillaAo();
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean isLogicalSide() {
        return switch (Platform.getEnvironment()) {
            // clients are only in control of logic when in singleplayer
            case CLIENT -> RetroTweaksClient.isLogicalSide();
            // dedicated servers are always in control of the logic
            case SERVER -> true;
        };
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
