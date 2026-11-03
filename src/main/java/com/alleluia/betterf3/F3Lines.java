package com.alleluia.betterf3;

import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.List;

/** Reads the current vanilla F3 lines and tags each one with a stable key. */
public final class F3Lines {
    /** One F3 line. {@code key} is the label used for selection; {@code text} is what gets drawn. */
    public record Line(String key, String text) {
    }

    /** The vanilla debug HUD, captured by {@code DebugHudMixin} when it is created. */
    private static volatile BetterF3DebugAccess hud;

    private F3Lines() {
    }

    public static void register(BetterF3DebugAccess access) {
        hud = access;
    }

    public static List<Line> snapshot(MinecraftClient client) {
        List<Line> out = new ArrayList<>();
        BetterF3DebugAccess access = hud;
        if (access == null) {
            return out;
        }
        addAll(out, access.betterF3Left());
        addAll(out, access.betterF3Right());
        return out;
    }

    private static void addAll(List<Line> out, List<String> texts) {
        if (texts == null) {
            return;
        }
        for (String text : texts) {
            if (text != null && !text.isBlank()) {
                out.add(new Line(keyOf(text), text));
            }
        }
    }

    /**
     * Label before the first colon, with digits and punctuation removed so
     * changing numbers don't change the key. Lines without a colon use their first word.
     */
    static String keyOf(String text) {
        int colon = text.indexOf(':');
        String label = colon > 0 ? text.substring(0, colon) : text;
        if (colon < 0) {
            int space = label.indexOf(' ');
            if (space > 0) {
                label = label.substring(0, space);
            }
        }
        label = label.replaceAll("[0-9.,/%()\\-]", "").trim();
        return label.isEmpty() ? "misc" : label;
    }
}
