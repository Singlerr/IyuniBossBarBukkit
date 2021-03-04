package io.github.singlerr;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;


import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Main extends JavaPlugin implements PluginMessageListener {
    private static BossBar bar;
    @Override
    public void onEnable() {
        getServer().getMessenger().registerIncomingPluginChannel(this,"ann:channel",this);
    }
    @Override
    public void onPluginMessageReceived(String s, Player player, byte[] bytes) {
            if(! s.equalsIgnoreCase("ann:channel")){
                return;
            }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
            String ch = in.readUTF();
            if(ch.equalsIgnoreCase("sub")){
                String var = in.readUTF();
                if(var.equalsIgnoreCase("remove")){
                    if(bar != null)
                        bar.removeAll();

                }else{
                    try {
                        JSONParser parser = new JSONParser();

                        JSONObject object = (JSONObject) parser.parse(var);
                        if (bar != null)
                            bar.removeAll();
                        bar = Bukkit.createBossBar(String.valueOf(object.get("content")), BarColor.valueOf(String.valueOf(object.get("color")).toUpperCase()), BarStyle.valueOf(String.valueOf(object.get("style")).toUpperCase()));
                        for(Player p : Bukkit.getOnlinePlayers())
                            bar.addPlayer(p);
                    }catch (ParseException ex){
                        ex.printStackTrace();
                    }
                    }
            }
    }

}
