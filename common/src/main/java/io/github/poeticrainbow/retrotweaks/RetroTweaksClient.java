package io.github.poeticrainbow.retrotweaks;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientCommandRegistrationEvent;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import io.github.poeticrainbow.retrotweaks.config.screen.ConfigScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import static dev.architectury.event.events.client.ClientCommandRegistrationEvent.literal;

public class RetroTweaksClient {
    public static final KeyMapping RETRO_TWEAKS_BUTTON = new KeyMapping("key.retrotweaks", GLFW.GLFW_KEY_O, KeyMapping.Category.MISC);

    public static void init() {
        KeyMappingRegistry.register(RETRO_TWEAKS_BUTTON);

        ClientRawInputEvent.KEY_PRESSED.register((client, action, keyEvent) -> {
            if (RETRO_TWEAKS_BUTTON.consumeClick()) {
                RetroTweaksClient.openConfigScreen();
                return EventResult.interruptTrue();
            }
            return EventResult.pass();
        });

        ClientCommandRegistrationEvent.EVENT.register((dispatcher, context) -> {
            dispatcher.register(
                literal("retrotweaks")
                    .executes(ctx -> {
                        RetroTweaksClient.openConfigScreen();
                        return 1;
                    })
            );
        });
    }

    public static void openConfigScreen() {
        Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(new ConfigScreen(Minecraft.getInstance().screen)));
    }

    @SuppressWarnings("ConstantValue")
    public static boolean isLogicalSide() {
        // if client side, this is the controller of logic
        var minecraft = Minecraft.getInstance();
        if (minecraft == null) {
            // minecraft is null during neoforge loading
            return true;
        }
        var level = minecraft.level;
        // if there is no level, we have full control
        // if there is a level, we check if we have logical control over the server (singleplayer)
        return level == null || minecraft.isSingleplayer();
    }
}
