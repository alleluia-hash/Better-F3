package com.alleluia.betterf3;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class BetterF3Client implements ClientModInitializer {
    private static KeyBinding cycleKey;
    private static KeyBinding presetKey;

    @Override
    public void onInitializeClient() {
        F3Config.load();

        cycleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.better-f3.cycle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F6, "category.better-f3"));
        presetKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.better-f3.preset", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_F7, "category.better-f3"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen instanceof ChooserScreen) {
                // The chooser handles F6 itself; drain the press so it doesn't fire twice.
                while (cycleKey.wasPressed()) {
                    // no-op
                }
            } else {
                while (cycleKey.wasPressed()) {
                    F3Overlay.cycle(client);
                }
            }
            while (presetKey.wasPressed()) {
                F3Config.nextPreset();
            }
        });

        HudRenderCallback.EVENT.register((matrices, tickDelta) -> F3Overlay.render(matrices));
    }
}
