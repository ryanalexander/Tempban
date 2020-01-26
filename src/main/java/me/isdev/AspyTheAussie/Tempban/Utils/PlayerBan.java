package me.isdev.AspyTheAussie.Tempban.Utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.isdev.AspyTheAussie.Tempban.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerBan implements ConfigurationSerializable {

    private OfflinePlayer player;
    private String reason;
    private long expire;

    public PlayerBan(OfflinePlayer player, String reason, long expire){
        this.player=player;
        this.reason=reason;
        this.expire=expire;

        if(player.isOnline()){
            // Kick player if online
            ((Player)player).kickPlayer(JavaUtils.format(JavaUtils.doApplyTranslations(this, JavaUtils.Translation.DENY)));
        }

        Main.player_bans.put(player.getUniqueId(),this);
        ArrayList<PlayerBan> bans=new ArrayList<>();
        bans.addAll(Main.player_bans.values());
        Main.config.set("bans",bans);
        Main.getPlugin(Main.class).saveConfig();
    }

    public PlayerBan(Map<String, Object> args){
        setPlayer(Bukkit.getOfflinePlayer(UUID.fromString((String) args.get("player"))));
        setReason((String)args.get("reason"));
        setExpire((long)args.get("expire"));
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public long getExpire() {
        return expire;
    }

    public long getTimeLeft() {
        return expire-System.currentTimeMillis();
    }

    public String getReason() {
        return reason;
    }

    public void setPlayer(OfflinePlayer player) {
        this.player = player;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public boolean isExpired() {
        return getTimeLeft()<0&&expire!=-1;
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap result = new LinkedHashMap();
        result.put("player",player.getUniqueId().toString());
        result.put("reason",reason);
        result.put("expire",expire);
        return result;
    }
}
