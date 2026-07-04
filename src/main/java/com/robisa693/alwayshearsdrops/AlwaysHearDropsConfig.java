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
        name = "<html><b style='color:orange'>Required In-Game Settings</b><br>Set these in OSRS Settings &gt; All Settings &gt; Loot Drops:<br>• Loot drop notification: <b>ON</b><br>• Minimum item value: <b>100,000</b> (or whatever you desire)<br><br>Note: if Sound Effects volume is not muted,<br>your game SE slider also controls replay volume</html>",
        description = "These OSRS game settings must be configured for the plugin to receive drop chat messages.",
        position = 0
    )
    default boolean inGameSetup()
    {
        return false;
    }

    @Range(min = 0, max = 100)
    @ConfigItem(
        keyName = "replayVolume",
        name = "Volume",
        description = "Volume of the replayed drop sound (0-100). Multiplied by the in-game Sound Effects volume if not muted.",
        position = 1
    )
    default int replayVolume()
    {
        return 100;
    }

    @Range(min = 0, max = 11000)
    @ConfigItem(
        keyName = "soundEffectId",
        name = "Drop Sound ID",
        description = "The game sound effect ID to play for drops. 2739=item drop, 3406=coins jingle, 4218=league trophy, 6765=unique drop, 3924=GE coins. Check OSRS Wiki for more.",
        position = 2
    )
    default int soundEffectId()
    {
        return 6765;
    }

    @ConfigItem(
        keyName = "threshold",
        name = "Drop value Threshold",
        description = "Minimum coin value of a valuable drop to trigger the sound (0 = all valuable drops).",
        position = 3
    )
    default int threshold()
    {
        return 0;
    }

    @ConfigItem(
        keyName = "untradeableDrops",
        name = "Untradeable drops",
        description = "Also play sound for untradeable drops.",
        position = 4
    )
    default boolean untradeableDrops()
    {
        return false;
    }

    @ConfigItem(
        keyName = "testDrop",
        name = "Test drop sound",
        description = "Toggle to test the notification sound. Uncheck then recheck to test again.",
        position = 5
    )
    default boolean testDrop()
    {
        return false;
    }

    @ConfigItem(
        keyName = "lowPrayerEnabled",
        name = "Low Prayer sound",
        description = "Play a sound when your prayer points drop at or below the threshold.",
        position = 6
    )
    default boolean lowPrayerEnabled()
    {
        return false;
    }

    @ConfigItem(
        keyName = "lowPrayerThreshold",
        name = "Low Prayer threshold",
        description = "Play sound when prayer points are at or below this value.",
        position = 7
    )
    default int lowPrayerThreshold()
    {
        return 20;
    }

    @Range(min = 0, max = 11000)
    @ConfigItem(
        keyName = "lowPrayerSoundEffectId",
        name = "Low Prayer Sound ID",
        description = "The game sound effect ID to play for low prayer. 932=bell.",
        position = 8
    )
    default int lowPrayerSoundEffectId()
    {
        return 932;
    }
}
