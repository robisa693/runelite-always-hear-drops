package com.robisa693.alwayshearsdrops;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("alwayshearsdrops")
public interface AlwaysHearDropsConfig extends Config
{
    @ConfigItem(
        keyName = "inGameSetup",
        name = "<html><b style='color:orange'>Required In-Game Settings</b><br>Set these in OSRS Settings &gt; All Settings:<br>• Loot drop notification: <b>ON</b> (for drop alerts)<br>• Minimum item value: <b>100,000</b> (or whatever you desire)<br>• Collection log new addition message: <b>ON</b><br>&nbsp;&nbsp;(for collection log alerts)<br><br>Note: if Sound Effects volume is not muted,<br>your game SE slider also controls alert volume</html>",
        description = "These OSRS game settings must be configured for the plugin to receive the chat messages it listens for.",
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
        description = "Volume of all alert sounds (0-100). Multiplied by the in-game Sound Effects volume if not muted.",
        position = 1
    )
    default int replayVolume()
    {
        return 100;
    }

    @ConfigSection(
        name = "Drops",
        description = "Alerts for valuable and untradeable drops",
        position = 2
    )
    String dropsSection = "drops";

    @Range(min = 0, max = 11000)
    @ConfigItem(
        keyName = "soundEffectId",
        name = "Drop Sound ID",
        description = "The game sound effect ID to play for drops. 2739=item drop, 3406=coins jingle, 4218=league trophy, 6765=unique drop, 3924=GE coins. Check OSRS Wiki for more.",
        position = 3,
        section = dropsSection
    )
    default int soundEffectId()
    {
        return 6765;
    }

    @ConfigItem(
        keyName = "threshold",
        name = "Drop value Threshold",
        description = "Minimum coin value of a valuable drop to trigger the sound (0 = all valuable drops).",
        position = 4,
        section = dropsSection
    )
    default int threshold()
    {
        return 0;
    }

    @ConfigItem(
        keyName = "untradeableDrops",
        name = "Untradeable drops",
        description = "Also play sound for untradeable drops.",
        position = 5,
        section = dropsSection
    )
    default boolean untradeableDrops()
    {
        return false;
    }

    @ConfigItem(
        keyName = "testDrop",
        name = "Test drop sound",
        description = "Click to play the drop sound. Every click plays it, ticked or not.",
        position = 6,
        section = dropsSection
    )
    default boolean testDrop()
    {
        return false;
    }

    @ConfigSection(
        name = "Pet & Collection Log",
        description = "Alerts for pet drops and new collection log entries",
        position = 7
    )
    String specialSection = "special";

    @ConfigItem(
        keyName = "petEnabled",
        name = "Pet drop sound",
        description = "Play a sound on the 'funny feeling' message when you receive a pet.",
        position = 8,
        section = specialSection
    )
    default boolean petEnabled()
    {
        return true;
    }

    @Range(min = 0, max = 11000)
    @ConfigItem(
        keyName = "petSoundEffectId",
        name = "Pet Sound ID",
        description = "The game sound effect ID to play for pet drops. 4218=league trophy, 6765=unique drop, 3406=coins jingle.",
        position = 9,
        section = specialSection
    )
    default int petSoundEffectId()
    {
        return 4218;
    }

    @ConfigItem(
        keyName = "testPet",
        name = "Test pet sound",
        description = "Click to play the pet sound. Every click plays it, ticked or not.",
        position = 10,
        section = specialSection
    )
    default boolean testPet()
    {
        return false;
    }

    @ConfigItem(
        keyName = "collectionLogEnabled",
        name = "Collection log sound",
        description = "Play a sound when a new item is added to your collection log. Requires the in-game 'Collection log - new addition message' setting.",
        position = 11,
        section = specialSection
    )
    default boolean collectionLogEnabled()
    {
        return true;
    }

    @Range(min = 0, max = 11000)
    @ConfigItem(
        keyName = "collectionLogSoundEffectId",
        name = "Collection Log Sound ID",
        description = "The game sound effect ID to play for collection log additions. 3406=coins jingle, 4218=league trophy, 6765=unique drop.",
        position = 12,
        section = specialSection
    )
    default int collectionLogSoundEffectId()
    {
        return 3406;
    }

    @ConfigItem(
        keyName = "testCollectionLog",
        name = "Test collection log sound",
        description = "Click to play the collection log sound. Every click plays it, ticked or not.",
        position = 13,
        section = specialSection
    )
    default boolean testCollectionLog()
    {
        return false;
    }

    @ConfigSection(
        name = "Low Prayer",
        description = "Alert when prayer points run low",
        position = 14
    )
    String prayerSection = "prayer";

    @ConfigItem(
        keyName = "lowPrayerEnabled",
        name = "Low Prayer sound",
        description = "Play a sound when your prayer points drop at or below the threshold.",
        position = 15,
        section = prayerSection
    )
    default boolean lowPrayerEnabled()
    {
        return false;
    }

    @ConfigItem(
        keyName = "lowPrayerThreshold",
        name = "Low Prayer threshold",
        description = "Play sound when prayer points are at or below this value.",
        position = 16,
        section = prayerSection
    )
    default int lowPrayerThreshold()
    {
        return 20;
    }

    @Range(min = 0, max = 11000)
    @ConfigItem(
        keyName = "lowPrayerSoundEffectId",
        name = "Low Prayer Sound ID",
        description = "The game sound effect ID to play for low prayer. 2674=prayer recharge, 932=bell.",
        position = 17,
        section = prayerSection
    )
    default int lowPrayerSoundEffectId()
    {
        return 2674;
    }

    @ConfigItem(
        keyName = "lowPrayerRepeat",
        name = "Repeat prayer sound",
        description = "Play the low prayer sound twice when triggered.",
        position = 18,
        section = prayerSection
    )
    default boolean lowPrayerRepeat()
    {
        return true;
    }

    @ConfigItem(
        keyName = "testPrayer",
        name = "Test prayer sound",
        description = "Click to play the low prayer sound. Every click plays it, ticked or not.",
        position = 19,
        section = prayerSection
    )
    default boolean testPrayer()
    {
        return false;
    }

    @ConfigSection(
        name = "Low Hitpoints",
        description = "Alert when hitpoints run low",
        position = 20
    )
    String hitpointsSection = "hitpoints";

    @ConfigItem(
        keyName = "lowHpEnabled",
        name = "Low HP sound",
        description = "Play a sound when your hitpoints drop at or below the threshold.",
        position = 21,
        section = hitpointsSection
    )
    default boolean lowHpEnabled()
    {
        return false;
    }

    @ConfigItem(
        keyName = "lowHpThreshold",
        name = "Low HP threshold",
        description = "Play sound when hitpoints are at or below this value.",
        position = 22,
        section = hitpointsSection
    )
    default int lowHpThreshold()
    {
        return 20;
    }

    @Range(min = 0, max = 11000)
    @ConfigItem(
        keyName = "lowHpSoundEffectId",
        name = "Low HP Sound ID",
        description = "The game sound effect ID to play for low hitpoints. 2136=alarm, 932=bell, 2674=prayer recharge.",
        position = 23,
        section = hitpointsSection
    )
    default int lowHpSoundEffectId()
    {
        return 2136;
    }

    @ConfigItem(
        keyName = "lowHpRepeat",
        name = "Repeat HP sound",
        description = "Play the low HP sound twice when triggered.",
        position = 24,
        section = hitpointsSection
    )
    default boolean lowHpRepeat()
    {
        return true;
    }

    @ConfigItem(
        keyName = "testHp",
        name = "Test HP sound",
        description = "Click to play the low HP sound. Every click plays it, ticked or not.",
        position = 25,
        section = hitpointsSection
    )
    default boolean testHp()
    {
        return false;
    }
}
