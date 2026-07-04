package com.robisa693.alwayshearsdrops;

import com.google.inject.Provides;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.SoundEffectVolume;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j

@PluginDescriptor(
    name = "Always Hear Drops",
    description = "Even if game is muted, hear all valuable drop sounds through your system sound.",
    tags = {"drops", "valuable", "untradeable", "sound", "notification", "loot"}
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
    private ConfigManager configManager;

    @Inject
    private ClientThread clientThread;

    private int threshold;
    private boolean untradeableDrops;
    private int volume;
    private int soundEffectId;

    @Provides
    AlwaysHearDropsConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(AlwaysHearDropsConfig.class);
    }

    @Override
    protected void startUp()
    {
        log.info("Always Hear Drops plugin started");
        reloadConfig();
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals("alwayshearsdrops"))
        {
            return;
        }

        log.info("Config changed: {} = {}", event.getKey(), event.getNewValue());

        if (event.getKey().equals("testDrop"))
        {
            if (event.getNewValue().equals("true"))
            {
                playDropSound();
            }
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
        log.info("Config reloaded: threshold={}, untradeable={}, volume={}, soundId={}",
            threshold, untradeableDrops, volume, soundEffectId);
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        String message = event.getMessage();
        log.debug("ChatMessage: type={}, message={}", event.getType(), message);

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
        log.info("Playing drop sound {} at volume {}", soundEffectId, volume);
        clientThread.invoke(() -> client.playSoundEffect(soundEffectId, volume));
    }
}
