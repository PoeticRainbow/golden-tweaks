package io.github.poeticrainbow.goldentweaks;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import io.github.poeticrainbow.goldentweaks.config.screen.ConfigScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import static dev.architectury.event.events.client.ClientCommandRegistrationEvent.literal;

public class GoldenTweaksClient {
    public static KeyMapping GOLDEN_TWEAKS_BUTTON = new KeyMapping("key.goldentweaks", GLFW.GLFW_KEY_O, KeyMapping.Category.MISC);

    public static void init() {
        KeyMappingRegistry.register(GOLDEN_TWEAKS_BUTTON);

        ClientRawInputEvent.KEY_PRESSED.register((client, action, keyEvent) -> {
            if (GOLDEN_TWEAKS_BUTTON.consumeClick()) {
                GoldenTweaksClient.openConfigScreen();
                return EventResult.interruptTrue();
            }
            return EventResult.pass();
        });

        ClientCommandRegistrationEvent.EVENT.register((dispatcher, context) -> {
            dispatcher.register(
                literal("goldentweaks")
                    .executes(ctx -> {
                        GoldenTweaksClient.openConfigScreen();
                        return 1;
                    })
            );
        });
    }

    public static void openConfigScreen() {
        Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(new ConfigScreen()));
    }
}
