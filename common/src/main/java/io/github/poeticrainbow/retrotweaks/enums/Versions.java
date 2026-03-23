package io.github.poeticrainbow.retrotweaks.enums;

import java.util.List;

public enum Versions {
    CLASSIC,
    INDEV,
    INFDEV,
    ALPHA,
    BETA,
    RELEASE,
    MODERN;

    public static final List<Versions> MAIN_VERSIONS = List.of(ALPHA, BETA, RELEASE, MODERN);
    public static final List<Versions> MAIN_VERSIONSINFDEV = List.of(INFDEV, ALPHA, BETA, RELEASE, MODERN);

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
