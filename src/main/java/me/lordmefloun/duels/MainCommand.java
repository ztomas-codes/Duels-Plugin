package me.lordmefloun.duels;

import me.lordmefloun.duels.Objects.Game;
import me.lordmefloun.duels.Utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player p = (Player) sender;
        if (p == null)
            return false;

        if(args[0].equalsIgnoreCase("join")){
            Game.Games.get(0).playerJoin(p);
        }
        if(args[0].equalsIgnoreCase("leave")){

            Game game = Game.getGameFromPlayer(p);
            if (game == null){
                Colors.sendMessage(p, "&cYou aren't in a game");
                return false;
            }


            Game.getGameFromPlayer(p).playerLeave(p);
        }


        return false;
    }
}
