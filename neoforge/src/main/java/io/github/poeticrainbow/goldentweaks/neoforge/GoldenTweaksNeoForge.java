package io.github.poeticrainbow.goldentweaks.neoforge;

import io.github.poeticrainbow.goldentweaks.GoldenTweaks;
import net.neoforged.fml.common.Mod;

@Mod(GoldenTweaks.MOD_ID)
public final class GoldenTweaksNeoForge {
    public GoldenTweaksNeoForge() {
        // Run our common setup.
        GoldenTweaks.init();
    }
}
