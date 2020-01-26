package me.isdev.AspyTheAussie.Tempban.Commands;

import me.isdev.AspyTheAussie.Tempban.Main;
import me.isdev.AspyTheAussie.Tempban.Utils.JavaUtils;
import me.isdev.AspyTheAussie.Tempban.Utils.PlayerBan;
import me.isdev.AspyTheAussie.Tempban.Utils.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TempbanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        // Usage
        // [0]<user> [1]<time> [2][reason]
        // [0]view [1]<user>

        if(args[0].equalsIgnoreCase("view")){
            // View existing ban
            OfflinePlayer victim = Bukkit.getOfflinePlayer(args[1]);
            if(victim==null){
                // Player does not exist
                sender.sendMessage(JavaUtils.format(Main.config.getString("lang.errors.player_nonexistent")));
                return true;
            }
            PlayerBan ban = Main.player_bans.get(victim.getUniqueId());
            if(ban==null||ban.isExpired()){
                sender.sendMessage(JavaUtils.format("&cThat player is not banned."));
                return true;
            }
            sender.sendMessage(JavaUtils.format(String.format("%s is banned for %s",ban.getPlayer().getName(),JavaUtils.doApplyTranslations(ban, JavaUtils.Translation.TIME))));
            return true;
        }
        if(args.length<3) return false;
        if(Bukkit.getOfflinePlayer(args[0])==null){
            // Player does not exist
            sender.sendMessage(JavaUtils.format(Main.config.getString("lang.errors.player_nonexistent")));
            return true;
        }
        TimeUnit unit = JavaUtils.getUnit(args[1]);
        if(unit==null){
            // Player does not exist
            sender.sendMessage(JavaUtils.format(Main.config.getString("lang.errors.non_numeric")));
            return true;
        }

        String reason = "";

        for(int i=2;i<args.length;i++)
            reason+=args[i]+" ";

        // Ban player & inform banner
        sender.sendMessage(JavaUtils.format(Main.config.getString("lang.player_banned").replaceAll("%player%",Bukkit.getOfflinePlayer(args[0]).getName()).replaceAll("%time%",JavaUtils.doApplyTranslations(new PlayerBan(Bukkit.getOfflinePlayer(args[0]),reason,System.currentTimeMillis()+JavaUtils.getNumberfromUnit(args[1],unit)), JavaUtils.Translation.SELF))));
        return true;
    }
}
