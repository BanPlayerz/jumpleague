package at.banplayerz.jl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.banplayerz.jl.Main;

public class Lobby_Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("lobby")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				
				if(args.length == 0) {
					Main.getInstance().getMethods().connectToLobby(p);
				} else {
					p.sendMessage(Main.prefix+"§cUm in die Lobby zu kommen, verwende die Befehle: /lobby, /leave, /l, /hub, /quit oder /q");
				}
			}
		}
		return false;
	}

}
