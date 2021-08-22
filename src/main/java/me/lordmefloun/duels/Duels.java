package me.lordmefloun.duels;

import me.lordmefloun.duels.Events.PlayerBlock;
import me.lordmefloun.duels.Events.PlayerDeath;
import me.lordmefloun.duels.Events.PlayerMove;
import me.lordmefloun.duels.Events.PlayerQuit;
import me.lordmefloun.duels.Objects.Game;
import me.lordmefloun.duels.Objects.PlayerManager;
import me.lordmefloun.duels.Utils.Colors;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class Duels extends JavaPlugin {

    public static Duels main;

    @Override
    public void onEnable() {
        main = this;
        getLogger().info("Plugin developed by LordMefloun");


        saveDefaultConfig();

        ConfigurationSection configSection = getConfig().getConfigurationSection("Arenas");
        if (configSection != null) {
            for (String key : configSection.getKeys(false)) {

                try {
                    Game.Games.add(new Game(key));
                } catch (Exception e) {
                    getLogger().warning("Error while setting up arena " + key);
                }
            }
        }
        getCommand("duels").setExecutor( new MainCommand());
        getServer().getPluginManager().registerEvents( new PlayerDeath(),this);
        getServer().getPluginManager().registerEvents( new PlayerMove(),this);
        getServer().getPluginManager().registerEvents( new PlayerQuit(),this);
        getServer().getPluginManager().registerEvents( new PlayerBlock(),this);


    }

    @Override
    public void onDisable() {
        try {
            if (Game.Games != null){
                for (Game game : Game.Games) {
                    game.resetArena();
                }
                if (PlayerManager.Players != null) {
                    for (PlayerManager pm : PlayerManager.Players) {
                        pm.getPlayer().teleport(pm.getGame().SpawnLocation);
                        pm.getPlayer().getInventory().clear();
                        Colors.sendMessage(pm.getPlayer(), Colors.prefix() + "&4You were disconnected from game due to arenas restarting");
                    }
                }
            }
        }
        catch (Exception e){
        }
    }
}
