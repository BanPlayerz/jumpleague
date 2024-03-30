package at.banplayerz.jl.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import at.banplayerz.jl.Main;
import at.banplayerz.jl.utils.GameManager;

public class PlayerRepawnJL implements Listener {

	@EventHandler
	public void onRepawn(PlayerRespawnEvent e) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(GameManager.isState(GameManager.INGAME) && Main.getInstance().deathmatchCD) {
					e.getPlayer().getInventory().clear();
					e.getPlayer().getInventory().setArmorContents(null);
					e.getPlayer().updateInventory();
					e.getPlayer().setGameMode(GameMode.SPECTATOR);
					e.getPlayer().teleport(Main.getInstance().getMethods().getSpawn(Main.getInstance().map, 1));
					if(!Main.getInstance().spectators.contains(e.getPlayer().getUniqueId().toString())) {
						Main.getInstance().spectators.add(e.getPlayer().getUniqueId().toString());
					}
					for(Player all : Bukkit.getOnlinePlayers()){
						all.hideEntity(Main.getInstance(), e.getPlayer());
					}
					e.getPlayer().sendMessage(Main.prefix+"§eDu bist nun Zuschauer!");
				}
				
				cancel();
			}
		}.runTaskLater(Main.getInstance(), 5);
	}
	
}
