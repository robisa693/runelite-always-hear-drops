package com.robisa693.alwayshearsdrops;

import com.google.inject.Provides;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.SoundEffectID;
import net.runelite.api.SoundEffectVolume;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

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

    private boolean enabled;
    private int threshold;
    private boolean untradeableDrops;
    private int volume;

    @Provides
    AlwaysHearDropsConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(AlwaysHearDropsConfig.class);
    }

    @Override
    protected void startUp()
    {
        reloadConfig();
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (!event.getGroup().equals("alwayshearsdrops"))
        {
            return;
        }
        reloadConfig();
    }

    private void reloadConfig()
    {
        enabled = config.enabled();
        threshold = config.threshold();
        untradeableDrops = config.untradeableDrops();
        volume = (config.replayVolume() * SoundEffectVolume.HIGH) / 100;
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (!enabled)
        {
            return;
        }

        String message = event.getMessage();

        if (message.startsWith("::testdrop"))
        {
            playDropSound();
            return;
        }

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
        client.playSoundEffect(SoundEffectID.ITEM_DROP, volume);
    }
}
