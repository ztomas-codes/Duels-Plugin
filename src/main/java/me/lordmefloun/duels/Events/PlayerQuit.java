package me.lordmefloun.duels.Events;

import me.lordmefloun.duels.Objects.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        PlayerManager p = PlayerManager.getPlayerManagerFromPlayer(e.getPlayer());
        if (p == null)
            return;

        p.getGame().playerLeave(p.getPlayer());

    }
}
