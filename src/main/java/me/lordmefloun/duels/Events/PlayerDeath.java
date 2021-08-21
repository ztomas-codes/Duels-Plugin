package me.lordmefloun.duels.Events;

import me.lordmefloun.duels.Objects.Game;
import me.lordmefloun.duels.Objects.PlayerManager;
import me.lordmefloun.duels.Utils.Colors;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(EntityDamageEvent e){

        if(!(e.getEntity().getType() == EntityType.PLAYER))
            return;

        Player victim = (Player) e.getEntity();

        if(victim == null)
            return;

        PlayerManager victimManager = PlayerManager.getPlayerManagerFromPlayer(victim);
        if(victimManager == null)
            return;


        if ((victim.getHealth() - e.getFinalDamage()) < 0){

            PlayerManager winnerManager = victimManager.getGame().getOpponent(victimManager);
            Player winner = winnerManager.getPlayer();

            victimManager.getGame().end(winnerManager);
        }


    }
}
