# Always Hear Drops

RuneLite plugin that plays a game sound effect when you receive valuable or untradeable drop notifications, even when your in-game Sound Effects volume is muted.

## Features

- Plays a sound for **Valuable drop:** and **Untradeable drop:** chat messages
- Configurable minimum coin value threshold
- Configurable volume (0–100)
- Configurable sound effect ID (default: 6765 — unique drop sound)
- Test button to preview the sound

## Configuration

- **Minimum value** – Minimum coin value to trigger the sound (default: 0 = all valuable drops).
- **Untradeable drops** – Also play sound for untradeable drops (default: OFF).
- **Volume** – Volume of the replayed drop sound (0–100). Multiplied by the game's Sound Effects volume if not muted.
- **Sound Effect ID** – The game sound effect to replay (default: 6765). See the [OSRS Sound Effects list](https://oldschool.runescape.wiki/w/Sound_effects) for IDs.
- **Test drop sound** – Click to preview the configured sound.

## Required In-Game Settings

Open OSRS Settings → All Settings → Loot Drops and set:

- **Loot drop notification:** ON
- **Minimum item value:** 100,000 (or whatever you desire)

The plugin needs these enabled to receive drop chat messages.
