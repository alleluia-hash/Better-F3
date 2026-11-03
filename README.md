# Better-F3

A client-side Fabric mod for Minecraft 1.20.1 that lets you choose exactly which F3 debug items you see, in a small overlay in the top-left corner.

Built for PvP and farming, where the debug screen is useful but a long wall of text gets in the way.

## Features

- **Pick your items**: A chooser screen lists every F3 item, with a checkbox for each one. Search-free, so you only scroll through the list.
- **Filtered overlay**: Only the items you checked are drawn, in a compact list in the top-left corner.
- **Presets**: Save layouts such as "PvP", "Farming", or "Building", and switch between them in one key press.
- **Keyboard first**: Everything works with the keyboard. The mouse is optional.
- **Combat safe**: The overlay never pauses the game and never captures input. Your keys keep working while it's on.
- **Vanilla F3 untouched**: The vanilla F3 screen and its combos (F3+G chunk borders, F3+B hitboxes, and so on) keep working exactly as before.

## Controls

| Key | Action |
| --- | --- |
| F6 | Cycle: off → filtered overlay → chooser → off |
| F7 | Switch to the next preset (works while the overlay is on) |
| Up / Down | Move through the list in the chooser |
| Enter or Space | Check or uncheck the highlighted item |
| Left / Right | Switch preset in the chooser |
| N | Create a new preset from the current one |
| Delete | Delete the current preset (the last one can't be deleted) |
| Esc | Leave the chooser and keep the filtered overlay on |
| F6 (in the chooser) | Close the chooser and turn the overlay off |
| Mouse click | Check or uncheck an item (optional) |

F6 and F7 are the defaults. You can change them in **Options → Controls → Better-F3**.

**Why not F3?** The F3 key already drives the vanilla debug screen and its combos. Taking it over would break them, so Better-F3 uses its own keys.

## Requirements

- Minecraft 1.20.1
- Fabric Loader 0.15 or newer
- Fabric API

## Installation

1. Install Fabric Loader for Minecraft 1.20.1.
2. Put Fabric API and the `Better-F3` jar in your `.minecraft/mods` folder.
3. Start the game.

This mod is client-side only. It doesn't need to be installed on a server.

## Settings

Presets are saved to `config/better-f3.json` in your Minecraft folder. Edit it by hand if you want, or change items through the chooser.

Items are identified by their label. For example, the line `XYZ: 12 / 64 / -4` has the label `XYZ`. Numbers are removed from labels, so an item such as `60 fps T: 50/50` is saved as `fps T`.

## Building

This project uses Gradle with Fabric Loom. Generate the Gradle wrapper once, then build:

```
gradle wrapper --gradle-version 8.8
./gradlew build
```

The built jar will be in `build/libs/`. You need Java 17 or newer.

## Status

Early development. The first version covers vanilla F3 items. It hasn't been tested in game yet, so expect rough edges.

## License

MIT. See [LICENSE](LICENSE).
