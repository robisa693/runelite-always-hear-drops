package com.robisa693.alwayshearsdrops;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("alwayshearsdrops")
public interface AlwaysHearDropsConfig extends Config
{
    @ConfigItem(
        keyName = "enabled",
        name = "Enabled",
        description = "Enable the plugin."
    )
    default boolean enabled()
    {
        return true;
    }

    @ConfigItem(
        keyName = "threshold",
        name = "Minimum value",
        description = "Minimum coin value of a valuable drop to trigger the sound (0 = all valuable drops)."
    )
    default int threshold()
    {
        return 0;
    }

    @ConfigItem(
        keyName = "untradeableDrops",
        name = "Untradeable drops",
        description = "Also play sound for untradeable drops."
    )
    default boolean untradeableDrops()
    {
        return true;
    }

    @Range(min = 0, max = 100)
    @ConfigItem(
        keyName = "replayVolume",
        name = "Volume",
        description = "Volume of the replayed drop sound (0-100)."
    )
    default int replayVolume()
    {
        return 100;
    }

    @ConfigItem(
        keyName = "testDrop",
        name = "Test drop sound",
        description = "Toggle to test the notification sound (auto-resets)."
    )
    default boolean testDrop()
    {
        return false;
    }

    @Range(min = 0, max = 11000)
    @ConfigItem(
        keyName = "soundEffectId",
        name = "Sound Effect ID",
        description = "The game sound effect ID to play. 2739=item drop, 3406=coins jingle, 4218=league trophy, 6765=unique drop, 3924=GE coins. Check OSRS Wiki for more."
    )
    default int soundEffectId()
    {
        return 3406;
    }
}
