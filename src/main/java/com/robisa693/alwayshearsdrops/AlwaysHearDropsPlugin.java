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
    description = "Personal audio alerts (drops, low prayer, more) that play even if in-game sound is muted.",
    tags = {"drops", "prayer", "alerts", "sound", "notification", "muted", "loot", "alert", "audio"}
)
public class AlwaysHearDropsPlugin extends Plugin
{
    private static final Pattern VALUABLE_DROP_PATTERN = Pattern.compile(
        "Valuable drop: (.+) \\(([\\d,]+) coins\\)"
    );
    private static final Pattern UNTRADEABLE_DROP_PATTERN = Pattern.compile(
        "Untradeable drop: (.+)"
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
    private boolean lowPrayerEnabled;
    private int lowPrayerThreshold;
    private int lowPrayerSoundEffectId;
    private boolean lowPrayerRepeat;
    private boolean prayerSoundPlayed;
    private int prayerRepeatPending;

    @Provides
    AlwaysHearDropsConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(AlwaysHearDropsConfig.class);
    }

    @Override
    protected void startUp()
    {
        prayerRepeatPending = 0;
        reloadConfig();
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals("alwayshearsdrops"))
        {
            return;
        }

        if (event.getKey().equals("testDrop"))
        {
            if (event.getNewValue().equals("true"))
            {
                playDropSound();
            }
            return;
        }

        if (event.getKey().equals("inGameSetup"))
        {
            return;
        }

        reloadConfig();
    }

    private void reloadConfig()
    {
        threshold = config.threshold();
        untradeableDrops = config.untradeableDrops();
        volume = (config.replayVolume() * SoundEffectVolume.HIGH) / 100;
        soundEffectId = config.soundEffectId();
        lowPrayerEnabled = config.lowPrayerEnabled();
        lowPrayerThreshold = config.lowPrayerThreshold();
        lowPrayerSoundEffectId = config.lowPrayerSoundEffectId();
        lowPrayerRepeat = config.lowPrayerRepeat();
        if (!lowPrayerRepeat)
        {
            prayerRepeatPending = 0;
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

        if (!lowPrayerEnabled)
        {
            return;
        }

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
            int dropValue = Integer.parseInt(valueStr);
            if (dropValue >= threshold)
            {
                playDropSound();
            }
            return;
        }

        if (untradeableDrops)
        {
            Matcher untradeableMatcher = UNTRADEABLE_DROP_PATTERN.matcher(message);
            if (untradeableMatcher.find())
            {
                playDropSound();
            }
        }
    }

    private void playDropSound()
    {
        clientThread.invoke(() -> client.playSoundEffect(soundEffectId, volume));
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
}
