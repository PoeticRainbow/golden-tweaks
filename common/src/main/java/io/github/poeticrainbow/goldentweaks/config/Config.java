package io.github.poeticrainbow.goldentweaks.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.platform.Platform;
import io.github.poeticrainbow.goldentweaks.GoldenTweaks;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public record Config(
    boolean darkAmbientOcclusion,
    boolean fullFaceShading,
    boolean betaLeavesLighting
) {
    public static final Codec<Config> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.BOOL.optionalFieldOf("dark_ambient_occlusion", true).forGetter(Config::darkAmbientOcclusion),
        Codec.BOOL.optionalFieldOf("full_face_shading", true).forGetter(Config::fullFaceShading),
        Codec.BOOL.optionalFieldOf("beta_leaves_lighting", true).forGetter(Config::betaLeavesLighting)
    ).apply(instance, Config::new));
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final Path CONFIG_PATH = Platform.getConfigFolder().resolve("goldentweaks.json");

    public void save() {
        var data = CODEC.encodeStart(JsonOps.INSTANCE, this);

        data.ifSuccess(json -> {
            try {
                Files.writeString(CONFIG_PATH, GSON.toJson(json), StandardCharsets.UTF_8);
            } catch (IOException e) {
                GoldenTweaks.LOGGER.error("Failed to save config at path {}", CONFIG_PATH);
            }
        });
    }

    public static Optional<Config> load() {
        try {
            // todo: change this to load a default config from the resources in the jar
            var data = "{}";
            if (CONFIG_PATH.toFile().exists()) {
                data = Files.readString(CONFIG_PATH);
            }
            var json = GSON.fromJson(data, JsonObject.class);
            var result = CODEC.parse(JsonOps.INSTANCE, json);

            return result.result();
        } catch (IOException e) {
            GoldenTweaks.LOGGER.error("Could not read config file from path {}", CONFIG_PATH);
            return Optional.empty();
        }
    }
}
