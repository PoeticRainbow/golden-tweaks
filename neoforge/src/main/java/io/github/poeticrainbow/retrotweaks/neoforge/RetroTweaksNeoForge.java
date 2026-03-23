package io.github.poeticrainbow.retrotweaks.neoforge;

import io.github.poeticrainbow.retrotweaks.ErrorCollector;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.TweakPlatform;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.config.NeoForgeClientConfig;
import net.neoforged.neoforge.client.event.ClientResourceLoadFinishedEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Optional;

@Mod(RetroTweaks.MOD_ID)
public final class RetroTweaksNeoForge implements TweakPlatform {
    public RetroTweaksNeoForge() {
        // Run our common setup.
        RetroTweaks.PLATFORM = this;
        // the platform MUST be set before initializing for config loading
        RetroTweaks.init();

        ErrorCollector.addErrorCheck(() -> {
            if (NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean()) {
                return Optional.of("NeoForge's enhancedLighting is enabled, breaking Per Face Lighting");
            }
            return Optional.empty();
        });

        NeoForge.EVENT_BUS.addListener(RetroTweaksNeoForge::onResourceReload);
    }

    private static void onResourceReload(ClientResourceLoadFinishedEvent event) {
        if (NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean()) {
            RetroTweaks.LOGGER.error("NeoForge's enhancedLighting is enabled in your neoforge-client.toml config file. The old lighting calculations will not work!");
        }
    }

    @Override
    public boolean isVanillaAo() {
        // return true when enhancedLighting is NOT enabled
        return !NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean();
    }
}
