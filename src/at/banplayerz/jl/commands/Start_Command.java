package at.banplayerz.jl.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.banplayerz.jl.Main;
import at.banplayerz.jl.utils.GameManager;

public class Start_Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {		
		if(cmd.getName().equalsIgnoreCase("start")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				if(p.hasPermission("jl.start")) {
					if(GameManager.isState(GameManager.LOBBY)) {
						if(Bukkit.getOnlinePlayers().size() >= Main.getInstance().getConfig().getInt("minPlayers")) {
							if(Main.getInstance().lobbyValue > 5) {
								Main.getInstance().lobbyValue = 5;
								p.sendMessage(Main.prefix+"§aDas Spiel wird gestartet.");
							} else {
								p.sendMessage(Main.prefix+"§cDas Spiel startet bereits.");
							}
						} else {
							p.sendMessage(Main.prefix+"§cEs sind zu wenig Spieler online.");
						}
					} else {
						p.sendMessage(Main.prefix+"§cDas Spiel läuft bereits.");
					}
				} else {
					p.sendMessage(Main.prefix+"§cDu hast kein Recht!");
				}
			}
		}
		return false;
	}

}
