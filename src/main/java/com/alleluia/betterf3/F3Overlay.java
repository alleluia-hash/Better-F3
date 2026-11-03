package com.alleluia.betterf3;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Set;

/** Draws the filtered overlay and handles the mode cycle. Never pauses or captures input. */
public final class F3Overlay {
    private static final int TEXT_COLOR = 0xFFFFFF;
    private static final int PRESET_COLOR = 0xAAAAAA;
    private static final int LEFT = 4;
    private static final int TOP = 4;
    private static final int LINE_HEIGHT = 10;

    private static F3Mode mode = F3Mode.OFF;

    private F3Overlay() {
    }

    public static F3Mode mode() {
        return mode;
    }

    public static void setMode(F3Mode newMode) {
        mode = newMode;
    }

    /** OFF → FILTERED → CHOOSER → OFF. */
    public static void cycle(MinecraftClient client) {
        switch (mode) {
            case OFF -> mode = F3Mode.FILTERED;
            case FILTERED -> {
                mode = F3Mode.CHOOSER;
                client.setScreen(new ChooserScreen());
            }
            case CHOOSER -> {
                mode = F3Mode.OFF;
                if (client.currentScreen instanceof ChooserScreen) {
                    client.setScreen(null);
                }
            }
        }
    }

    public static void render(MatrixStack matrices) {
        if (mode != F3Mode.FILTERED) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden) {
            return;
        }
        TextRenderer text = client.textRenderer;
        text.drawWithShadow(matrices, "[" + F3Config.activePresetName() + "]", LEFT, TOP, PRESET_COLOR);

        Set<String> active = F3Config.activeKeys();
        int y = TOP + LINE_HEIGHT;
        for (F3Lines.Line line : F3Lines.snapshot(client)) {
            if (active.contains(line.key())) {
                text.drawWithShadow(matrices, line.text(), LEFT, y, TEXT_COLOR);
                y += LINE_HEIGHT;
            }
        }
    }
}
