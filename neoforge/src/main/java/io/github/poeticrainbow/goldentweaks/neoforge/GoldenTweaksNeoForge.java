package io.github.poeticrainbow.goldentweaks.neoforge;

import dev.architectury.platform.client.ConfigurationScreenRegistry;
import io.github.poeticrainbow.goldentweaks.GoldenTweaks;
import net.minecraft.client.gui.screens.ChatScreen;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.config.NeoForgeClientConfig;
import net.neoforged.neoforge.client.event.ClientResourceLoadFinishedEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(GoldenTweaks.MOD_ID)
public final class GoldenTweaksNeoForge {
    public GoldenTweaksNeoForge() {
        // Run our common setup.
        GoldenTweaks.init();

        NeoForge.EVENT_BUS.addListener(GoldenTweaksNeoForge::onResourceReload);
    }

    private static void onResourceReload(ClientResourceLoadFinishedEvent event) {
        if (NeoForgeClientConfig.INSTANCE.enhancedLighting.getAsBoolean()) {
            GoldenTweaks.LOGGER.error("NeoForge's enhancedLighting is enabled in your neoforge-client.toml config file. The old lighting calculations will not work!");
        }
    }
}
