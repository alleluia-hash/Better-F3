package com.alleluia.betterf3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Presets and their checked items, saved to config/better-f3.json. */
public final class F3Config {
    private static final Logger LOGGER = LoggerFactory.getLogger("better-f3");
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("better-f3.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Data data = new Data();

    static final class Data {
        String activePreset = "Default";
        LinkedHashMap<String, LinkedHashSet<String>> presets = new LinkedHashMap<>();
    }

    private F3Config() {
    }

    public static void load() {
        if (Files.exists(PATH)) {
            try (Reader reader = Files.newBufferedReader(PATH)) {
                Data loaded = GSON.fromJson(reader, Data.class);
                if (loaded != null) {
                    data = loaded;
                }
            } catch (Exception e) {
                LOGGER.warn("Could not read {}, using defaults", PATH, e);
                data = new Data();
            }
        }
        if (data.presets == null) {
            data.presets = new LinkedHashMap<>();
        }
        if (data.presets.isEmpty()) {
            data.presets.put("Default", new LinkedHashSet<>(List.of("XYZ", "Facing")));
        }
        if (!data.presets.containsKey(data.activePreset)) {
            data.activePreset = data.presets.keySet().iterator().next();
        }
        save();
    }

    static void save() {
        try {
            Files.createDirectories(PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(PATH)) {
                GSON.toJson(data, writer);
            }
        } catch (IOException e) {
            LOGGER.warn("Could not save {}", PATH, e);
        }
    }

    public static String activePresetName() {
        return data.activePreset;
    }

    public static Set<String> activeKeys() {
        return Collections.unmodifiableSet(current());
    }

    public static boolean isActive(String key) {
        return current().contains(key);
    }

    public static void toggle(String key) {
        Set<String> keys = current();
        if (!keys.remove(key)) {
            keys.add(key);
        }
        save();
    }

    public static void nextPreset() {
        switchBy(1);
    }

    public static void previousPreset() {
        switchBy(-1);
    }

    public static void newPresetFromActive() {
        int n = data.presets.size() + 1;
        String name;
        do {
            name = "Preset " + n++;
        } while (data.presets.containsKey(name));
        data.presets.put(name, new LinkedHashSet<>(current()));
        data.activePreset = name;
        save();
    }

    public static void deleteActivePreset() {
        if (data.presets.size() <= 1) {
            return;
        }
        data.presets.remove(data.activePreset);
        data.activePreset = data.presets.keySet().iterator().next();
        save();
    }

    private static void switchBy(int direction) {
        List<String> names = new ArrayList<>(data.presets.keySet());
        int index = names.indexOf(data.activePreset);
        data.activePreset = names.get(Math.floorMod(index + direction, names.size()));
        save();
    }

    private static LinkedHashSet<String> current() {
        return data.presets.get(data.activePreset);
    }
}
