package me.isdev.AspyTheAussie.Tempban.Utils;

import me.isdev.AspyTheAussie.Tempban.Main;
import org.bukkit.ChatColor;

import java.util.List;

public class JavaUtils {

    public enum Translation {DENY, SELF, TIME}

    public static boolean isPlural(int i){
        return i > 1 || i==0;
    }


    public static String doApplyTranslations(PlayerBan playerBan, Translation translation){
        String reply = "";
        switch (translation){
            case DENY:
                // Player attempted to join the server
                List<String> deny_template = Main.config.getStringList("lang.kick_template");
                for(String s : deny_template)
                    reply+=s.replaceAll("%reason%",playerBan.getReason())
                            .replaceAll("%time%",doApplyTranslations(playerBan,Translation.TIME)
                            .replaceAll("%player%",playerBan.getPlayer().getName()))+"\n";
                break;
            case SELF:
                // Player attempted to join the server
                reply=Main.config.getString("lang.player_banned")
                        .replaceAll("%reason%",playerBan.getReason())
                        .replaceAll("%time%",doApplyTranslations(playerBan,Translation.TIME)
                        .replaceAll("%player%",playerBan.getPlayer().getName()));
                break;
            case TIME:
                long SECONDS = TimeUnit.SECOND.FormatMS(playerBan.getTimeLeft());
                long MINUTES = TimeUnit.MINUTE.FormatMS(playerBan.getTimeLeft());
                long HOURS = TimeUnit.HOUR.FormatMS(playerBan.getTimeLeft());
                long DAYS = TimeUnit.DAY.FormatMS(playerBan.getTimeLeft());
                String DAYS_FORMATTED = DAYS+"";
                String HOURS_FORMATTED = (HOURS-(DAYS*24))+"";
                String MINUTES_FORMATTED = (MINUTES-(HOURS*60))+"";
                String SECONDS_FORMATTED = (SECONDS-(MINUTES*60))+"";
                if(playerBan.getTimeLeft()>TimeUnit.DAY.getMilliseconds())reply+=TimeUnit.DAY.getFormat(DAYS_FORMATTED)+" ";
                if(playerBan.getTimeLeft()>TimeUnit.HOUR.getMilliseconds())reply+=TimeUnit.HOUR.getFormat(HOURS_FORMATTED)+" ";
                if(playerBan.getTimeLeft()>TimeUnit.MINUTE.getMilliseconds())reply+=TimeUnit.MINUTE.getFormat(MINUTES_FORMATTED)+" ";
                if(playerBan.getTimeLeft()>TimeUnit.SECOND.getMilliseconds())reply+=TimeUnit.SECOND.getFormat(SECONDS_FORMATTED)+" ";
                break;
        }
        return reply;
    }

    public static String format(String s){
        return ChatColor.translateAlternateColorCodes('&',s);
    }

    public static TimeUnit getUnit(String s) {
        if(s.toLowerCase().indexOf("d")==s.length()-1){return TimeUnit.DAY;}
        if(s.toLowerCase().indexOf("h")==s.length()-1){return TimeUnit.HOUR;}
        if(s.toLowerCase().indexOf("m")==s.length()-1){return TimeUnit.MINUTE;}
        if(s.toLowerCase().indexOf("s")==s.length()-1){return TimeUnit.SECOND;}
        return null;
    }
    public static long getNumberfromUnit(String s, TimeUnit unit){
        switch (unit){
            case DAY:
                return Integer.parseInt(s.toLowerCase().replaceAll("d",""))*TimeUnit.DAY.getMilliseconds();
            case HOUR:
                return Integer.parseInt(s.toLowerCase().replaceAll("h",""))*TimeUnit.HOUR.getMilliseconds();
            case MINUTE:
                return Integer.parseInt(s.toLowerCase().replaceAll("m",""))*TimeUnit.MINUTE.getMilliseconds();
            case SECOND:
                return Integer.parseInt(s.toLowerCase().replaceAll("s",""))*TimeUnit.SECOND.getMilliseconds();
        }
        return 0;
    }

}
