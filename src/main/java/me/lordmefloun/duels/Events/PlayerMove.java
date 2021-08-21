package me.lordmefloun.duels.Events;

import me.lordmefloun.duels.Objects.Game;
import me.lordmefloun.duels.Objects.PlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {


    @EventHandler
    public void onMove(PlayerMoveEvent e){

        PlayerManager p = PlayerManager.getPlayerManagerFromPlayer(e.getPlayer());
        if (p == null)
            return;

        if (p.getGame().GameState == Game.GameStates.WAITING  || p.getGame().GameState == Game.GameStates.STARTING || p.getGame().GameState == Game.GameStates.ONEPLAYER){
            e.setCancelled(true);
        }
    }
}
