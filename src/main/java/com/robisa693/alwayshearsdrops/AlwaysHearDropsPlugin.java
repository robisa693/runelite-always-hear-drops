package com.robisa693.alwayshearsdrops;

import com.google.inject.Provides;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.api.SoundEffectVolume;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@PluginDescriptor(
    name = "Always Hear Alerts",
    description = "Personal audio alerts (drops, pets, collection log, low prayer, low HP) that play even if in-game sound is muted.",
    tags = {"drops", "prayer", "hitpoints", "hp", "pet", "collection", "alerts", "sound", "notification", "muted", "loot", "alert", "audio"}
)
public class AlwaysHearDropsPlugin extends Plugin
{
    private static final Pattern VALUABLE_DROP_PATTERN = Pattern.compile(
        "Valuable drop: (.+) \\(([\\d,]+) coins\\)"
    );
    private static final Pattern UNTRADEABLE_DROP_PATTERN = Pattern.compile(
        "Untradeable drop: (.+)"
    );
    // "You have a funny feeling like you're being followed", "... like you
    // would have been followed", "You feel something weird sneaking into
    // your backpack" - all three pet messages share one of these fragments.
    private static final Pattern PET_PATTERN = Pattern.compile(
        "funny feeling|weird sneaking"
    );
    private static final Pattern COLLECTION_LOG_PATTERN = Pattern.compile(
        "New item added to your collection log: (.+)"
    );

    @Inject
    private AlwaysHearDropsConfig config;

    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    private int threshold;
    private boolean untradeableDrops;
    private int volume;
    private int soundEffectId;
    private boolean petEnabled;
    private int petSoundEffectId;
    private boolean collectionLogEnabled;
    private int collectionLogSoundEffectId;
    private boolean lowPrayerEnabled;
    private int lowPrayerThreshold;
    private int lowPrayerSoundEffectId;
    private boolean lowPrayerRepeat;
    private boolean prayerSoundPlayed;
    private int prayerRepeatPending;
    private boolean lowHpEnabled;
    private int lowHpThreshold;
    private int lowHpSoundEffectId;
    private boolean lowHpRepeat;
    private boolean hpSoundPlayed;
    private int hpRepeatPending;

    @Provides
    AlwaysHearDropsConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(AlwaysHearDropsConfig.class);
    }

    @Override
    protected void startUp()
    {
        prayerRepeatPending = 0;
        hpRepeatPending = 0;
        reloadConfig();
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals("alwayshearsdrops"))
        {
            return;
        }

        Integer testSoundId = testSoundFor(event.getKey());
        if (testSoundId != null)
        {
            if (event.getNewValue().equals("true"))
            {
                playSound(testSoundId);
            }
            return;
        }

        if (event.getKey().equals("inGameSetup"))
        {
            return;
        }

        reloadConfig();
    }

    /** The sound the given test-toggle key previews, or null for non-test keys. */
    private Integer testSoundFor(String key)
    {
        switch (key)
        {
            case "testDrop":
                return config.soundEffectId();
            case "testPet":
                return config.petSoundEffectId();
            case "testCollectionLog":
                return config.collectionLogSoundEffectId();
            case "testPrayer":
                return config.lowPrayerSoundEffectId();
            case "testHp":
                return config.lowHpSoundEffectId();
            default:
                return null;
        }
    }

    private void reloadConfig()
    {
        threshold = config.threshold();
        untradeableDrops = config.untradeableDrops();
        volume = (config.replayVolume() * SoundEffectVolume.HIGH) / 100;
        soundEffectId = config.soundEffectId();
        petEnabled = config.petEnabled();
        petSoundEffectId = config.petSoundEffectId();
        collectionLogEnabled = config.collectionLogEnabled();
        collectionLogSoundEffectId = config.collectionLogSoundEffectId();
        lowPrayerEnabled = config.lowPrayerEnabled();
        lowPrayerThreshold = config.lowPrayerThreshold();
        lowPrayerSoundEffectId = config.lowPrayerSoundEffectId();
        lowPrayerRepeat = config.lowPrayerRepeat();
        if (!lowPrayerRepeat)
        {
            prayerRepeatPending = 0;
        }
        lowHpEnabled = config.lowHpEnabled();
        lowHpThreshold = config.lowHpThreshold();
        lowHpSoundEffectId = config.lowHpSoundEffectId();
        lowHpRepeat = config.lowHpRepeat();
        if (!lowHpRepeat)
        {
            hpRepeatPending = 0;
        }
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (prayerRepeatPending > 0)
        {
            client.playSoundEffect(lowPrayerSoundEffectId, volume);
            prayerRepeatPending = 0;
        }
        if (hpRepeatPending > 0)
        {
            client.playSoundEffect(lowHpSoundEffectId, volume);
            hpRepeatPending = 0;
        }

        if (lowPrayerEnabled)
        {
            int currentPrayer = client.getBoostedSkillLevel(Skill.PRAYER);
            if (currentPrayer <= lowPrayerThreshold)
            {
                if (!prayerSoundPlayed)
                {
                    playPrayerSound();
                    prayerSoundPlayed = true;
                }
            }
            else
            {
                prayerSoundPlayed = false;
            }
        }

        if (lowHpEnabled)
        {
            int currentHp = client.getBoostedSkillLevel(Skill.HITPOINTS);
            if (currentHp <= lowHpThreshold)
            {
                if (!hpSoundPlayed)
                {
                    playHpSound();
                    hpSoundPlayed = true;
                }
            }
            else
            {
                hpSoundPlayed = false;
            }
        }
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        String message = event.getMessage();

        if (event.getType() != ChatMessageType.GAMEMESSAGE
            && event.getType() != ChatMessageType.SPAM)
        {
            return;
        }

        Matcher valuableMatcher = VALUABLE_DROP_PATTERN.matcher(message);
        if (valuableMatcher.find())
        {
            String valueStr = valuableMatcher.group(2).replace(",", "");
            long dropValue = Long.parseLong(valueStr);
            if (dropValue >= threshold)
            {
                playSound(soundEffectId);
            }
            return;
        }

        if (petEnabled && PET_PATTERN.matcher(message).find())
        {
            playSound(petSoundEffectId);
            return;
        }

        if (collectionLogEnabled && COLLECTION_LOG_PATTERN.matcher(message).find())
        {
            playSound(collectionLogSoundEffectId);
            return;
        }

        if (untradeableDrops && UNTRADEABLE_DROP_PATTERN.matcher(message).find())
        {
            playSound(soundEffectId);
        }
    }

    private void playSound(int soundId)
    {
        clientThread.invoke(() -> client.playSoundEffect(soundId, volume));
    }

    private void playPrayerSound()
    {
        clientThread.invoke(() -> {
            client.playSoundEffect(lowPrayerSoundEffectId, volume);
            if (lowPrayerRepeat)
            {
                prayerRepeatPending = 1;
            }
        });
    }

    private void playHpSound()
    {
        clientThread.invoke(() -> {
            client.playSoundEffect(lowHpSoundEffectId, volume);
            if (lowHpRepeat)
            {
                hpRepeatPending = 1;
            }
        });
    }
}
