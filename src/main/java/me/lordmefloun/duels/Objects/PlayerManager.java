package me.lordmefloun.duels.Objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager {

    private UUID Uuid;
    private Game Game;
    public int PlayerNumber;
    public static ArrayList<PlayerManager> Players = new ArrayList<>();


    public PlayerManager(UUID uuid, Game game, int PlayerNumber){
        this.Uuid = uuid;
        this.Game = game;
        this.PlayerNumber = PlayerNumber;
        this.Players.add(this);
    }

    public Game getGame() {
        return Game;
    }

    public UUID getUuid() {
        return Uuid;
    }

    public static PlayerManager getPlayerManagerFromPlayer(Player p){

        for(PlayerManager looped : Players){
            Player player = Bukkit.getPlayer(looped.Uuid);
            if (p == player) return looped;
        }

        return null;
    }

    public void remove(){
        Players.remove(this);
        this.Uuid = null;
        this.Game = null;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(this.getUuid());
    }
}
