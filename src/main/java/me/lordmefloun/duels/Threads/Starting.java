package me.lordmefloun.duels.Threads;

import me.lordmefloun.duels.Duels;
import me.lordmefloun.duels.Objects.Game;
import me.lordmefloun.duels.Objects.PlayerManager;
import me.lordmefloun.duels.Utils.Colors;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Starting {

    public static void inGameStarting(Game game) {
        new BukkitRunnable() {
            int i = 5;

            @Override
            public void run() {
                if (i != 0) {
                    if (game.getPlayers().size() == 2) {
                        game.broadcastMessage(Colors.prefix() + "&aGame starts in &6" + i + "&a seconds");
                        for (PlayerManager p : game.getPlayers()){
                            p.getPlayer().sendTitle(ChatColor.translateAlternateColorCodes('&', "&6&lDUELS"), ChatColor.translateAlternateColorCodes('&', "&aGame starts in &2" + i), 0, 20, 0);
                            p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK,2, 1);
                        }
                        game.GameState = game.GameState.STARTING;
                        i = i - 1;
                    } else {
                        game.broadcastMessage(Colors.prefix() + "&cGame counting stopped");
                        for (PlayerManager p : game.getPlayers()){
                            p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.BLOCK_GLASS_BREAK,2, 1);
                        }
                        game.GameState = game.GameState.ONEPLAYER;
                        this.cancel();
                    }
                }
                if (i == 0) {
                    game.start();
                    this.cancel();
                }
            }
        }.runTaskTimer(Duels.main, 0, 20L);
    }
}
