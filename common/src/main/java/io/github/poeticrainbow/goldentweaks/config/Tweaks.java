package io.github.poeticrainbow.goldentweaks.config;

import io.github.poeticrainbow.goldentweaks.GoldenTweaks;

import java.util.function.Function;

public class Tweaks {
    public static final Tweak<Boolean> BETA_LEAVES_LIGHTING = new Tweak<>(Config::betaLeavesLighting);
    public static final Tweak<Boolean> DARK_AMBIENT_OCCLUSION = new Tweak<>(Config::darkAmbientOcclusion);
    public static final Tweak<Boolean> FULL_FACE_SHADING = new Tweak<>(Config::fullFaceShading);

    public record Tweak<T>(Function<Config, T> getter) {
        public T get() {
            return getter.apply(GoldenTweaks.CONFIG);
        }
    }
}
