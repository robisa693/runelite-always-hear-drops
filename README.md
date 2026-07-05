# Always Hear Alerts

RuneLite plugin that plays game sound effects when you receive valuable drops or your prayer runs low — **works even with in-game Sound Effects volume fully muted**, and **requires no external audio files**.

Always Hear Alerts uses OSRS's built-in sound effect engine, letting you pick any in-game sound ID. No downloading, bundling, or recording audio files needed.

## Features

- Plays a sound for **Valuable drop:** and **Untradeable drop:** chat messages
- Plays a sound when you get a **pet** ("funny feeling" message) or a new **collection log** entry
- Plays a sound when your **prayer points** or **hitpoints** drop below a configurable threshold
- **Works with in-game Sound Effects volume muted** — no need to keep game sounds on
- **No external audio files** — uses native in-game sound IDs
- Configurable volume, sound IDs and thresholds per alert, with a test button for each sound

## Configuration

- **Volume** – Volume of all alert sounds (0–100). Multiplied by the game's Sound Effects volume if not muted.

### Drops

- **Drop Sound ID** – The game sound effect to play for drops (default: 6765 — unique drop).
- **Drop value Threshold** – Minimum coin value to trigger the sound (default: 0 = all valuable drops).
- **Untradeable drops** – Also play sound for untradeable drops (default: OFF).
- **Test drop sound** – Toggle to preview the configured drop sound.

### Pet & Collection Log

- **Pet drop sound** – Play a sound on the "funny feeling" pet messages (default: ON, sound 4218 — league trophy).
- **Collection log sound** – Play a sound when a new item is added to your collection log (default: ON, sound 3406 — coins jingle). Requires the in-game collection log message setting, see below.
- **Test pet / collection log sound** – Toggles to preview each sound.

### Low Prayer

- **Low Prayer sound** – Enable low prayer alerts (default: OFF).
- **Low Prayer threshold** – Play sound when prayer points are at or below this value (default: 20).
- **Low Prayer Sound ID** – The game sound effect to play for low prayer (default: 2674 — prayer recharge).
- **Repeat prayer sound** – Play the sound twice in sequence (one tick apart) for extra noticeability.
- **Test prayer sound** – Toggle to preview the configured prayer sound.

### Low Hitpoints

- **Low HP sound** – Enable low hitpoints alerts (default: OFF).
- **Low HP threshold** – Play sound when hitpoints are at or below this value (default: 20).
- **Low HP Sound ID** – The game sound effect to play for low HP (default: 2136).
- **Repeat HP sound** – Play the sound twice in sequence (one tick apart) for extra noticeability.
- **Test HP sound** – Toggle to preview the configured HP sound.

## Required In-Game Settings

Open OSRS Settings → All Settings and set:

- **Loot drop notification:** ON (for drop alerts)
- **Minimum item value:** 100,000 (or whatever you desire)
- **Collection log - new addition message:** ON (for collection log alerts)

The plugin needs these enabled to receive the chat messages it listens for.
