# Better-F3

A client-side Fabric mod for Minecraft 1.20.1 that lets you choose exactly which F3 debug items you see, in a small strip on the left edge of the screen.

It's built for PvP and farming, where the debug screen is useful but a long wall of text gets in the way.

## Features (planned)

- **Left-edge strip**: A thin strip that doesn't cover the screen. Expand it to see only the items you picked.
- **Keyboard first**: F3 cycles through three states: off, filtered view, and chooser.
- **Arrow keys to choose**: Move through the list with the arrow keys and press Enter to toggle an item.
- **Mouse optional**: Clicking works in the chooser if you want it, but nothing requires it.
- **Presets**: Save layouts like "PvP", "Farming", and "Building", and switch between them without opening the chooser.
- **Combat safe**: The in-game view never pauses the game and never captures input.
- **Vanilla items first**: The first version covers vanilla F3 items only. Mod integrations come later.

## Controls (planned)

| Key | Action |
| --- | --- |
| F3 | Cycle: off → filtered view → chooser → off |
| Arrow keys | Move in the chooser |
| Enter | Toggle the highlighted item |
| Mouse click | Toggle an item (optional) |
| Preset key (planned) | Switch presets during a match |

Vanilla F3 combos such as F3+G (chunk borders) and F3+B (hitboxes) keep working.

## Requirements

- Minecraft 1.20.1
- Fabric Loader
- Fabric API

## Installation

1. Install Fabric Loader for Minecraft 1.20.1.
2. Put Fabric API and the `Better-F3` jar in your `.minecraft/mods` folder.
3. Start the game.

This mod is client-side only. It doesn't need to be installed on a server.

## Building

```
./gradlew build
```

The built jar will be in `build/libs/`.

## Status

Early development. The core keyboard flow comes first, then presets, then mouse support.

## License

MIT. See [LICENSE](LICENSE).
