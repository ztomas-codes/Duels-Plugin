package me.lordmefloun.duels;

import me.lordmefloun.duels.Objects.Game;
import me.lordmefloun.duels.Utils.Colors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MainCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {



        if (!(sender instanceof Player))
            return true;
        Player p = (Player) sender;


        if(args.length == 0){
            sendCommandList(p);
            return false;
        }


        if(args[0].equalsIgnoreCase("join")){
            if (args.length == 2) {
                if (getBestGame(args[1])  != null){
                    getBestGame(args[1]).playerJoin(p);
                }
                else{
                    Colors.sendMessage(p, Colors.prefix() + "&cAll arenas are full with this kit name");
                }
            }
            else{
                Colors.sendMessage(p, Colors.prefix() + "&cPlease type kit name you want to play!");
            }
        }
        if(args[0].equalsIgnoreCase("leave")){

            Game game = Game.getGameFromPlayer(p);
            if (game == null){
                Colors.sendMessage(p, "&cYou aren't in a game");
                return false;
            }
            Game.getGameFromPlayer(p).playerLeave(p);
        }


        if(args[0].equalsIgnoreCase("admin")){
            if (p.hasPermission("duels.admin")) {
                if(args.length >= 3 || args.length == 2) {
                    if (args[1].equalsIgnoreCase("setspawn")) {
                        Duels.main.getConfig().set("SpawnLocation.World", p.getPlayer().getLocation().getWorld().getName());
                        Duels.main.getConfig().set("SpawnLocation.x", p.getPlayer().getLocation().getX());
                        Duels.main.getConfig().set("SpawnLocation.y", p.getPlayer().getLocation().getY());
                        Duels.main.getConfig().set("SpawnLocation.z", p.getPlayer().getLocation().getZ());
                        Duels.main.saveConfig();
                        Colors.sendMessage(p, Colors.prefix() + "&aSpawnpoint set");
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("createkit")) {
                        if (args[2] != null) {
                            Duels.main.getConfig().set("Kits." + args[2] + ".items", p.getInventory().getContents());
                            Duels.main.getConfig().set("Kits." + args[2] + ".armor", p.getInventory().getArmorContents());
                            Duels.main.saveConfig();
                            Colors.sendMessage(p, Colors.prefix() + "&aKit has been saved &2" + args[2]);
                        }
                        else{
                            Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of kit");
                        }
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("arenacreate")) {
                        if (args[2] != null) {
                            Duels.main.getConfig().set("Arenas." + args[2] + ".MapName", "Unknown");
                            Duels.main.getConfig().set("Arenas." + args[2] + ".MapAuthor", "Unknown");
                            Duels.main.saveConfig();
                            Colors.sendMessage(p, Colors.prefix() + "&aNew arena named &2" + args[2] + "&a has been created");
                        }
                        else{
                            Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of arena");
                        }
                        return false;

                    }
                    if (args[1].equalsIgnoreCase("setkitname")) {
                        if (args[2] != null) {
                            if (args[3] != null) {
                                if(arenaExists(args[2])) {
                                    if(kitExists(args[3])) {
                                        Duels.main.getConfig().set("Arenas." + args[2] + ".Kit", args[3]);
                                        Duels.main.saveConfig();
                                        Colors.sendMessage(p, Colors.prefix() + "&aKit of arena named &2" + args[2] + "&a has been set to &2" + args[3]);
                                    }
                                    else Colors.sendMessage(p, Colors.prefix() + "&cThis kit doesn't exist");
                                }
                                else Colors.sendMessage(p, Colors.prefix() + "&cThis arena doesn't exist");
                            }
                            else {
                                Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of kit");
                            }
                        }
                        else{
                            Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of arena");
                        }
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("setmapname")) {
                        if (args[2] != null) {
                            if (args[3] != null) {
                                if(arenaExists(args[2])) {
                                    Duels.main.getConfig().set("Arenas." + args[2] + ".MapName", args[3]);
                                    Duels.main.saveConfig();
                                    Colors.sendMessage(p, Colors.prefix() + "&2" + args[2] + "'s&a map name has been set");
                                }
                                else Colors.sendMessage(p, Colors.prefix() + "&cThis arena doesn't exist");
                            }
                            else {
                                Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of map");
                            }
                        }
                        else{
                            Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of arena");
                        }
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("setmapauthor")) {
                        if (args[2] != null) {
                            if (args[3] != null) {
                                if (arenaExists(args[2])) {
                                    Duels.main.getConfig().set("Arenas." + args[2] + ".MapAuthor", args[3]);
                                    Duels.main.saveConfig();
                                    Colors.sendMessage(p, Colors.prefix() + "&2" + args[2] + "'s&a map author name has been set");
                                }
                                else Colors.sendMessage(p, Colors.prefix() + "&cThis arena doesn't exist");
                            }
                            else {
                                Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of map author");
                            }
                        }
                        else{
                            Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of arena");
                        }
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("setlocation1")) {
                        if (args[2] != null) {
                            if (arenaExists(args[2])) {
                                Duels.main.getConfig().set("Arenas." + args[2] + ".World", p.getPlayer().getLocation().getWorld().getName());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.1.x", p.getPlayer().getLocation().getX());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.1.y", p.getPlayer().getLocation().getY());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.1.z", p.getPlayer().getLocation().getZ());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.1.yaw", p.getPlayer().getLocation().getYaw());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.1.pitch", p.getPlayer().getLocation().getPitch());
                                Duels.main.saveConfig();
                                Colors.sendMessage(p, Colors.prefix() + "&aLocation1 has been saved for arena &2" + args[2]);
                            }
                            else Colors.sendMessage(p, Colors.prefix() + "&cThis arena doesn't exist");
                        }
                        else{
                            Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of arena");
                        }
                        return false;
                    }
                    if (args[1].equalsIgnoreCase("setlocation2")) {
                        if (args[2] != null) {
                            if(arenaExists(args[2])) {
                                Duels.main.getConfig().set("Arenas." + args[2] + ".World", p.getPlayer().getLocation().getWorld().getName());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.2.x", p.getPlayer().getLocation().getX());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.2.y", p.getPlayer().getLocation().getY());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.2.z", p.getPlayer().getLocation().getZ());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.2.yaw", p.getPlayer().getLocation().getYaw());
                                Duels.main.getConfig().set("Arenas." + args[2] + ".Locations.2.pitch", p.getPlayer().getLocation().getPitch());
                                Duels.main.saveConfig();
                                Colors.sendMessage(p, Colors.prefix() + "&aLocation2 has been saved for arena &2" + args[2]);
                            }
                            else Colors.sendMessage(p, Colors.prefix() + "&cThis arena doesn't exist");
                        }
                        else{
                            Colors.sendMessage(p, Colors.prefix() + "&cPlease write name of arena");
                        }
                        return false;
                    }

                    if(args.length >= 3 || args.length == 2) Colors.sendMessage(p, "&4After every changes in arenas/kit you need to reload or restart server so the plugin loads with new set arenas and kits!!!!!");
                }
                sendCommandList(p);


            }
            else{
                sendCommandList(p);
            }
        }



        return false;
    }

    public void sendCommandList(Player p ){
        Colors.sendMessage(p, "");
        Colors.sendMessage(p, Colors.prefix() + "      &c&lCommands");
        Colors.sendMessage(p, "");
        Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels join <kitname>");
        Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels leave");
        Colors.sendMessage(p, "");
        if(p.hasPermission("duels.admin")){
            Colors.sendMessage(p, Colors.prefix() + "      &c&lAdmin Commands");
            Colors.sendMessage(p, "");
            Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels admin arenacreate <name>");
            Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels admin setspawn");
            Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels admin createkit <name> &7- Saves your inventory as a kit");
            Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels admin setkitname <arena> <name>");
            Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels admin setmapname <arena> <name>");
            Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels admin setmapauthor <arena> <name>");
            Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels admin setlocation1 <arena>");
            Colors.sendMessage(p, Colors.prefix() + "  &8- &3/duels admin setlocation2 <arena>");
            Colors.sendMessage(p, "");
        }
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }


    public Game getBestGame(String ArenaKit){

        for (Game game : Game.Games){

            if (game.Kit.equalsIgnoreCase(ArenaKit)) {

                if (game.GameState == Game.GameStates.ONEPLAYER) {
                    return game;
                }
            }
        }

        for (Game game : Game.Games){

            if (game.Kit.equalsIgnoreCase(ArenaKit)) {

                if (game.GameState == Game.GameStates.WAITING) {
                    return game;
                }
            }
        }

        return null;
    }

    public boolean arenaExists(String arena){
        return Duels.main.getConfig().contains("Arenas." + arena);
    }
    public boolean kitExists(String kit){
        return Duels.main.getConfig().contains("Kits." + kit);
    }

}