package me.lordmefloun.duels;

import me.lordmefloun.duels.Events.PlayerDeath;
import me.lordmefloun.duels.Events.PlayerMove;
import me.lordmefloun.duels.Events.PlayerQuit;
import me.lordmefloun.duels.Objects.Game;
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
        for (String key : configSection.getKeys(false)) {
            Game.Games.add( new Game(key));
        }

        getCommand("duels").setExecutor( new MainCommand());
        getServer().getPluginManager().registerEvents( new PlayerDeath(),this);
        getServer().getPluginManager().registerEvents( new PlayerMove(),this);
        getServer().getPluginManager().registerEvents( new PlayerQuit(),this);


    }
}
