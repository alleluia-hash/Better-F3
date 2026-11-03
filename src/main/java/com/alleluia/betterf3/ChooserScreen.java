package com.alleluia.betterf3;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The chooser. Keyboard first, mouse optional. Opening it is a setup step,
 * so it may cover the screen, but it never pauses the game.
 */
public final class ChooserScreen extends Screen {
    private static final int LEFT = 16;
    private static final int TOP = 44;
    private static final int ROW_HEIGHT = 12;
    private static final int MAX_PREVIEW = 56;

    private final List<String> keys = new ArrayList<>();
    private final Map<String, String> previews = new HashMap<>();
    private int cursor;

    public ChooserScreen() {
        super(Text.literal("Better-F3"));
        refreshKeys();
    }

    private void refreshKeys() {
        keys.clear();
        previews.clear();
        for (F3Lines.Line line : F3Lines.snapshot(MinecraftClient.getInstance())) {
            if (!previews.containsKey(line.key())) {
                previews.put(line.key(), line.text());
                keys.add(line.key());
            }
        }
        // Keep saved items even when they aren't on screen right now.
        for (String key : F3Config.activeKeys()) {
            if (!keys.contains(key)) {
                keys.add(key);
            }
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        fill(matrices, 8, 8, width - 8, height - 8, 0xB0000000);
        textRenderer.drawWithShadow(matrices,
                "Better-F3  -  preset: " + F3Config.activePresetName(), LEFT, 16, 0xFFFFAA);

        if (keys.isEmpty()) {
            textRenderer.drawWithShadow(matrices,
                    "No F3 items found. Join a world first.", LEFT, TOP, 0xFFFFFF);
        }
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String row = (F3Config.isActive(key) ? "[x] " : "[ ] ") + key + "   " + preview(key);
            int color = i == cursor ? 0xFFFF55 : 0xFFFFFF;
            textRenderer.drawWithShadow(matrices, row, LEFT, TOP + i * ROW_HEIGHT, color);
        }

        textRenderer.drawWithShadow(matrices,
                "Up/Down move   Enter toggle   Left/Right preset   N new   Del delete",
                LEFT, height - 26, 0xAAAAAA);
        textRenderer.drawWithShadow(matrices,
                "Esc keep overlay   F6 turn off", LEFT, height - 14, 0xAAAAAA);
    }

    private String preview(String key) {
        String text = previews.getOrDefault(key, "(not visible right now)");
        return text.length() > MAX_PREVIEW ? text.substring(0, MAX_PREVIEW) + "..." : text;
    }

    private void toggleCursor() {
        if (!keys.isEmpty()) {
            F3Config.toggle(keys.get(cursor));
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        switch (keyCode) {
            case GLFW.GLFW_KEY_UP -> cursor = Math.max(0, cursor - 1);
            case GLFW.GLFW_KEY_DOWN -> cursor = Math.min(Math.max(0, keys.size() - 1), cursor + 1);
            case GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER, GLFW.GLFW_KEY_SPACE -> toggleCursor();
            case GLFW.GLFW_KEY_LEFT -> F3Config.previousPreset();
            case GLFW.GLFW_KEY_RIGHT -> F3Config.nextPreset();
            case GLFW.GLFW_KEY_N -> F3Config.newPresetFromActive();
            case GLFW.GLFW_KEY_DELETE -> F3Config.deleteActivePreset();
            case GLFW.GLFW_KEY_ESCAPE -> {
                F3Overlay.setMode(F3Mode.FILTERED);
                close();
            }
            case GLFW.GLFW_KEY_F6 -> {
                F3Overlay.setMode(F3Mode.OFF);
                close();
            }
            default -> {
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
        }
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            for (int i = 0; i < keys.size(); i++) {
                int y = TOP + i * ROW_HEIGHT;
                if (mouseX >= LEFT && mouseX < width - LEFT && mouseY >= y && mouseY < y + ROW_HEIGHT) {
                    cursor = i;
                    toggleCursor();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
