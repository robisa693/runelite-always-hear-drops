package com.robisa693.alwayshearsdrops;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("alwayshearsdrops")
public interface AlwaysHearDropsConfig extends Config
{
    @ConfigItem(
        keyName = "inGameSetup",
        name = "<html><b style='color:orange'>Required In-Game Settings</b><br>Set these in OSRS Settings &gt; All Settings &gt; Loot Drops:<br>• Loot drop notification: <b>All</b><br>• Minimum item value: <b>0</b> (or your threshold)<br><br>Note: if Sound Effects volume is not muted,<br>your game SE slider also controls replay volume</html>",
        description = "These OSRS game settings must be configured for the plugin to receive drop chat messages.",
        position = 0
    )
    default boolean inGameSetup()
    {
        return false;
    }

    @ConfigItem(
        keyName = "threshold",
        name = "Minimum value",
        description = "Minimum coin value of a valuable drop to trigger the sound (0 = all valuable drops).",
        position = 1
    )
    default int threshold()
    {
        return 0;
    }

    @ConfigItem(
        keyName = "untradeableDrops",
        name = "Untradeable drops",
        description = "Also play sound for untradeable drops.",
        position = 2
    )
    default boolean untradeableDrops()
    {
        return true;
    }

    @Range(min = 0, max = 100)
    @ConfigItem(
        keyName = "replayVolume",
        name = "Volume",
        description = "Volume of the replayed drop sound (0-100). Multiplied by the in-game Sound Effects volume if not muted.",
        position = 3
    )
    default int replayVolume()
    {
        return 100;
    }

    @ConfigItem(
        keyName = "testDrop",
        name = "Test drop sound",
        description = "Toggle to test the notification sound. Uncheck then recheck to test again.",
        position = 4
    )
    default boolean testDrop()
    {
        return false;
    }

    @Range(min = 0, max = 11000)
    @ConfigItem(
        keyName = "soundEffectId",
        name = "Sound Effect ID",
        description = "The game sound effect ID to play. 2739=item drop, 3406=coins jingle, 4218=league trophy, 6765=unique drop, 3924=GE coins. Check OSRS Wiki for more.",
        position = 5
    )
    default int soundEffectId()
    {
        return 6765;
    }
}
