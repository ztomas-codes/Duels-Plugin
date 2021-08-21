package me.lordmefloun.duels.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Colors {
    public static void sendMessage(Player Player, String ColoredMessage){
        Player.sendMessage(ChatColor.translateAlternateColorCodes('&', ColoredMessage));
    }

    public static String prefix(){
        return ChatColor.translateAlternateColorCodes('&', "&8[&cDuels&8] &7");
    }
}
