package me.isdev.AspyTheAussie.Tempban;

import me.isdev.AspyTheAussie.Tempban.Commands.TempbanCommand;
import me.isdev.AspyTheAussie.Tempban.Events.Player;
import me.isdev.AspyTheAussie.Tempban.Utils.JavaUtils;
import me.isdev.AspyTheAussie.Tempban.Utils.PlayerBan;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public static FileConfiguration config;
    public static HashMap<UUID, PlayerBan> player_bans = new HashMap<>();

    @Override
    public void onEnable() {
        Logger logger = Bukkit.getLogger();

        logger.info("Successfully enabled Tempban");

        ConfigurationSerialization.registerClass(PlayerBan.class);

        saveDefaultConfig();
        config=getConfig();

        // Register commands
        getCommand("tempban").setExecutor(new TempbanCommand());

        // Register events
        Bukkit.getServer().getPluginManager().registerEvents(new Player(),this);

        // Load ban cache
        List<PlayerBan> bans = (List<PlayerBan>) getConfig().get("bans");
        if(bans!=null)
            for(PlayerBan ban : bans)
                player_bans.put(ban.getPlayer().getUniqueId(),ban);
        Bukkit.getServer().getLogger().info(String.format("Loaded %s ban%s from database",player_bans.size(), JavaUtils.isPlural(player_bans.size())?"s":""));
    }


}
