package me.isdev.AspyTheAussie.Tempban.Events;

import me.isdev.AspyTheAussie.Tempban.Main;
import me.isdev.AspyTheAussie.Tempban.Utils.JavaUtils;
import me.isdev.AspyTheAussie.Tempban.Utils.PlayerBan;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Player implements Listener {

    @EventHandler()
    public void PlayerJoin(AsyncPlayerPreLoginEvent e){
        // Check if ban exists
        PlayerBan ban = Main.player_bans.get(e.getUniqueId());
        if(Main.player_bans.containsKey(e.getUniqueId())){
            // Player with ban record joined
            // Check ban has not already expired
            if(ban.isExpired()) {
                Main.player_bans.remove(e.getUniqueId());
                return;
            }
            // Kick if applicable
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED,JavaUtils.format(JavaUtils.doApplyTranslations(ban, JavaUtils.Translation.DENY)));
            return;
        }
    }

}
