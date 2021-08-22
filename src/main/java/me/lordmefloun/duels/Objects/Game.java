package me.lordmefloun.duels.Objects;

import me.lordmefloun.duels.Duels;
import me.lordmefloun.duels.Threads.Starting;
import me.lordmefloun.duels.Utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Game {


    public static ArrayList<Game> Games = new ArrayList<>();
    private ArrayList<PlayerManager> Players = new ArrayList<>();
    private ArrayList<Location> Locations = new ArrayList<>();

    public GameStates GameState = GameStates.WAITING;
    public ArrayList<Location> PlacedBlocks = new ArrayList<>();
    private ItemStack[] KitItems;
    private ItemStack[] KitArmor;
    public String Kit;
    private String ArenaName;
    private String MapAuthor;
    private String MapName;

    public Location SpawnLocation;



    public Game(String ArenaName){
        this.ArenaName = ArenaName;
        try {
            this.MapName = Duels.main.getConfig().getString("Arenas." + ArenaName + ".MapName");
            this.MapAuthor = Duels.main.getConfig().getString("Arenas." + ArenaName + ".MapAuthor");
            this.Kit = Duels.main.getConfig().getString("Arenas." + ArenaName + ".Kit");
        }
        catch (Exception e){
            Bukkit.getLogger().warning("Error in creating arena (MapName or MapAuthor or Kit name is missing) " + getArenaName());
        }

        ConfigurationSection configSection = Duels.main.getConfig().getConfigurationSection("Arenas." + ArenaName + ".Locations");
        for(String key : configSection.getKeys(false)){

            Location newLoc = new Location(Bukkit.getWorld(Duels.main.getConfig().getString("Arenas." + ArenaName + ".World")),
                    Duels.main.getConfig().getDouble("Arenas." + ArenaName + ".Locations." + key + ".x"),
                    Duels.main.getConfig().getDouble("Arenas." + ArenaName + ".Locations." + key + ".y"),
                    Duels.main.getConfig().getDouble("Arenas." + ArenaName + ".Locations." + key + ".z"),
                    Float.parseFloat(Duels.main.getConfig().getString("Arenas." + ArenaName + ".Locations." + key + ".yaw")),
                    Float.parseFloat(Duels.main.getConfig().getString("Arenas." + ArenaName + ".Locations." + key + ".pitch"))
                    );

            this.Locations.add(newLoc);
        }

        if(Locations.size() != 2){
            Bukkit.getLogger().warning("Error in creating arena (LocationsError) " + getArenaName());
        }

        this.SpawnLocation = new Location(Bukkit.getWorld(Duels.main.getConfig().getString("SpawnLocation.World")),
                Duels.main.getConfig().getDouble("SpawnLocation.x"),
                Duels.main.getConfig().getDouble("SpawnLocation.y"),
                Duels.main.getConfig().getDouble("SpawnLocation.z")
                );

        try {
            KitArmor = ((List<ItemStack>) Duels.main.getConfig().get("Kits." + Kit + ".armor")).toArray(new ItemStack[0]);
            KitItems = ((List<ItemStack>) Duels.main.getConfig().get("Kits." + Kit + ".items")).toArray(new ItemStack[0]);
        }
        catch (Exception e){
            Bukkit.getLogger().warning("Error in creating arena (Arena Kit) " + getArenaName());
        }
    }


    public enum GameStates{
        INGAME,WAITING,STARTING,ONEPLAYER
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
        if (PlayerManager.getPlayerManagerFromPlayer(p) == null) {

            if (this.GameState == GameStates.ONEPLAYER || this.GameState == GameStates.WAITING ) {

                if (getPlayers().size() == 0) {
                    getPlayers().add(new PlayerManager(p.getUniqueId(), this, 1));
                    p.teleport(Locations.get(0));
                    this.GameState = GameStates.ONEPLAYER;
                } else {
                    getPlayers().add(new PlayerManager(p.getUniqueId(), this, 2));
                    p.teleport(Locations.get(1));
                    Starting.inGameStarting(this);
                }

                p.setHealth(20);
                p.setFoodLevel(20);
                Colors.sendMessage(p, Colors.prefix() + "Connecting to &2" + getArenaName());
                Colors.sendMessage(p, Colors.prefix() + "&cYou&7 have joined the game");
                Colors.sendMessage(p, "");
                Colors.sendMessage(p, "  &aMap: &2&l" + getMapName());
                Colors.sendMessage(p, "  &aAuthor: &2&l" + this.MapAuthor);
                Colors.sendMessage(p, "");

                p.getInventory().setContents(KitItems);
                p.getInventory().setArmorContents(KitArmor);
            }
            else Colors.sendMessage(p, "&cThis game already started or is starting");


        }
        else Colors.sendMessage(p, "&cAlready joined in a game!");
    }

    public void playerLeave(Player p){
        if (PlayerManager.getPlayerManagerFromPlayer(p) != null) {

            if(this.GameState == GameStates.ONEPLAYER)
                 this.GameState = GameStates.WAITING;

            if(this.GameState == GameStates.INGAME)
                end(PlayerManager.getPlayerManagerFromPlayer(p).getGame().getOpponent(PlayerManager.getPlayerManagerFromPlayer(p)));


            Colors.sendMessage(p, Colors.prefix() + "&aSuccessfully left game&6 " + getArenaName());
            p.teleport(SpawnLocation);
            p.getInventory().clear();

            try {
                getPlayers().remove(PlayerManager.getPlayerManagerFromPlayer(p));
                PlayerManager.getPlayerManagerFromPlayer(p).remove();
                getPlayers().remove(PlayerManager.getPlayerManagerFromPlayer(p));
            }
            catch (Exception e){}



        }
        else Colors.sendMessage(p, "&cYou aren't in a game");
    }

    public void start(){
         this.GameState = GameState.INGAME;
         broadcastMessage(Colors.prefix() + "&aGame started &cFIGHT!");


    }

    public void end(PlayerManager winner){


        winner.getGame().broadcastMessage(Colors.prefix() + "&9" + winner.getPlayer().getName() + " &7killed &c" + winner.getGame().getOpponent(winner).getPlayer().getName() );
        this.resetArena();
        this.GameState = GameState.WAITING;
        for(PlayerManager player: this.getPlayers()){
            player.getPlayer().teleport(SpawnLocation);
            player.getPlayer().setHealth(20);
            player.getPlayer().setFoodLevel(20);
            player.getPlayer().getInventory().clear();
            player.remove();
        }
        this.getPlayers().clear();
    }

    public void broadcastMessage(String message){
        for (PlayerManager player: this.getPlayers()){
            Player p = player.getPlayer();
            Colors.sendMessage(p, message);
        }
    }

    public PlayerManager getOpponent(PlayerManager p){
        if (this.getPlayers().get(0) == p) return this.getPlayers().get(1);
        if (this.getPlayers().get(1) == p) return this.getPlayers().get(0);
        return null;
    }

    public void resetArena(){
        if (PlacedBlocks != null){
            for(Location location : PlacedBlocks){
                location.getBlock().setType(Material.AIR);
            }
            PlacedBlocks.clear();
        }
    }

}
