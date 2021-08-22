package me.lordmefloun.duels.Events;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import me.lordmefloun.duels.Objects.Game;
import me.lordmefloun.duels.Objects.PlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerBlock implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e){

        PlayerManager p = PlayerManager.getPlayerManagerFromPlayer(e.getPlayer());
        if (p == null)
            return;

        p.getGame().PlacedBlocks.add(e.getBlock().getLocation());
    }


    @EventHandler
    public void onDestroy(BlockBreakEvent e){
        PlayerManager p = PlayerManager.getPlayerManagerFromPlayer(e.getPlayer());
        if (p == null)
            return;

        if(!p.getGame().PlacedBlocks.contains(e.getBlock().getLocation())){
            e.setCancelled(true);
        }

    }
}
