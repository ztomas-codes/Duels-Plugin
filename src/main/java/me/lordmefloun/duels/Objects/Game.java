package me.lordmefloun.duels.Objects;

import me.lordmefloun.duels.Utils.Colors;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Game {


    public static ArrayList<Game> Games = new ArrayList<>();
    private ArrayList<PlayerManager> Players = new ArrayList<>();
    private ArrayList<Location> Locations = new ArrayList<>();

    private String ArenaName;
    private String MapName;



    public Game(String ArenaName){
        this.ArenaName = ArenaName;
    }


    public ArrayList<Location> getLocations() {
        return Locations;
    }

    public ArrayList<PlayerManager> getPlayers() {
        return Players;
    }

    public String getMapName() {
        return MapName;
    }

    public String getArenaName() {
        return ArenaName;
    }


    public static Game getGameFromName(String Name){
        for (Game Game : Games){
            if(Games.contains(Game)) return Game;
        }

        return null;
    }

    public static Game getGameFromPlayer(Player p){

        for (Game Game : Games){
            if(Game.Players.contains(PlayerManager.getPlayerManagerFromPlayer(p))) return Game;
        }

        return null;
    }


    public void playerJoin(Player p){

        getPlayers().add(new PlayerManager(p.getUniqueId(), this));
        Colors.sendMessage(p, "&aSuccessfully joined game&6 " + getArenaName());
        Colors.sendMessage(p, "&awith map &6" + getMapName());
    }

    public void playerLeave(Player p){

        getPlayers().add(new PlayerManager(p.getUniqueId(), this));
        Colors.sendMessage(p, "&aSuccessfully joined game&6 " + getArenaName());
        Colors.sendMessage(p, "&awith map &6" + getMapName());
    }
}
