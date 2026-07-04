# Always Hear Alerts

RuneLite plugin that plays game sound effects when you receive valuable drops or your prayer runs low — **works even with in-game Sound Effects volume fully muted**, and **requires no external audio files**.

Always Hear Alerts uses OSRS's built-in sound effect engine, letting you pick any in-game sound ID. No downloading, bundling, or recording audio files needed.

## Features

- Plays a sound for **Valuable drop:** and **Untradeable drop:** chat messages
- Plays a sound when your **prayer points drop** below a configurable threshold
- **Works with in-game Sound Effects volume muted** — no need to keep game sounds on
- **No external audio files** — uses native in-game sound IDs
- Configurable volume, sound IDs, and thresholds for both drops and prayer
- Test button to preview sounds

## Configuration

### Drops

- **Volume** – Volume of the replayed sound (0–100). Multiplied by the game's Sound Effects volume if not muted.
- **Drop Sound ID** – The game sound effect to play for drops (default: 6765 — unique drop).
- **Drop value Threshold** – Minimum coin value to trigger the sound (default: 0 = all valuable drops).
- **Untradeable drops** – Also play sound for untradeable drops (default: OFF).
- **Test drop sound** – Click to preview the configured drop sound.

### Low Prayer

- **Low Prayer sound** – Enable low prayer alerts (default: OFF).
- **Low Prayer threshold** – Play sound when prayer points are at or below this value (default: 20).
- **Low Prayer Sound ID** – The game sound effect to play for low prayer (default: 2674 — prayer recharge).
- **Repeat prayer sound** – Play the sound twice in sequence (one tick apart) for extra noticeability.

## Required In-Game Settings

Open OSRS Settings → All Settings → Loot Drops and set:

- **Loot drop notification:** ON
- **Minimum item value:** 100,000 (or whatever you desire)

The plugin needs these enabled to receive drop chat messages.
