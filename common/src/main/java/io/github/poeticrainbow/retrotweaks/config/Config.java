package io.github.poeticrainbow.retrotweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import dev.architectury.platform.Platform;
import io.github.poeticrainbow.retrotweaks.RetroTweaks;
import io.github.poeticrainbow.retrotweaks.tweak.Tweak;
import io.github.poeticrainbow.retrotweaks.tweak.Tweaks;

import java.io.*;
import java.nio.file.Path;

public class Config {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final Path CONFIG_PATH = Platform.getConfigFolder().resolve("retrotweaks.json");

    public static void save() {
        JsonObject obj = new JsonObject();

        for (Tweak<?> value : Tweaks.values()) {
            obj.add(value.key(), GSON.toJsonTree(value.get()));
        }

        try (Writer writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(obj, writer);
            RetroTweaks.LOGGER.info("Successfully saved config to {}", CONFIG_PATH);
        } catch (IOException e) {
            RetroTweaks.LOGGER.error("Could not save config file to path {}", CONFIG_PATH);
        }
    }

    @SuppressWarnings("unchecked")
    public static void load() {
        try {
            // todo: change this to load a default config from the resources in the jar
            try (Reader reader = new FileReader(CONFIG_PATH.toFile())) {
                JsonObject obj = GSON.fromJson(reader, JsonObject.class);

                for (String key : obj.keySet()) {
                    Tweak<?> value = Tweaks.get(key);
                    if (value == null) continue;

                    try {
                        Object parsed = GSON.fromJson(obj.get(key), value.defaultValue().getClass());
                        ((Tweak<Object>) value).set(parsed);
                    } catch (JsonSyntaxException e) {
                        RetroTweaks.LOGGER.error("Failed to create tweak for tweak config {}", key);
                    } catch (ExceptionInInitializerError e) {
                        RetroTweaks.LOGGER.error("A severe issue occured while loading tweak {}:\n{}", key, e.getMessage());
                    }
                }

                RetroTweaks.LOGGER.info("Successfully loaded config from {}", CONFIG_PATH);
            }
        } catch (IOException e) {
            RetroTweaks.LOGGER.error("Could not read config file from path {}", CONFIG_PATH);
        }
    }
}
