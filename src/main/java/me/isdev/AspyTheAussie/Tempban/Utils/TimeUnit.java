package me.isdev.AspyTheAussie.Tempban.Utils;

import me.isdev.AspyTheAussie.Tempban.Main;

public enum TimeUnit {
    DAY("lang.time_template_days",86400000),
    HOUR("lang.time_template_hours",3600000),
    MINUTE("lang.time_template_minutes",60000),
    SECOND("lang.time_template_seconds",1000);
    private String format;
    private long milliseconds;
    TimeUnit(String format, long milliseconds) {
        this.format=(format);
        this.milliseconds=milliseconds;
    }

    public String getFormat(String timeleft){
        return replace(Main.config.getString(format),timeleft);
    }

    public String replace(String s, String timeleft) {
        return s.replaceAll("%"+name().toUpperCase()+"%",(timeleft)+"");
    }
    public long FormatMS(Long time){
        return ((time==0?1:time)/milliseconds);
    }
    public long getMilliseconds() {
        return milliseconds;
    }
}
