package at.banplayerz.jl.events;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

import at.banplayerz.jl.Main;
import at.banplayerz.jl.utils.GameManager;

public class PlayerJoinJL implements Listener {

	@EventHandler
	public void onJoin(final PlayerJoinEvent e){
		e.setJoinMessage(null);
		
		if(GameManager.isState(GameManager.LOBBY)) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					//if(!at.banplayerz.bcnick.Main.getInstance().nicked.containsKey(e.getPlayer().getUniqueId().toString())) {
					//	e.getPlayer().setPlayerListName(at.banplayerz.bcnick.utils.Methods.getPlayerPrefix(e.getPlayer())+e.getPlayer().getName());
					//}
					
					int playerCount = 0;
					for(Player players : Bukkit.getOnlinePlayers()) {
						if(!Main.getInstance().spectators.contains(players.getUniqueId().toString())) {
							playerCount++;
						}
					}
					
					if(playerCount <= Main.getInstance().getConfig().getInt("maxPlayers")) {
						e.getPlayer().teleport(Main.getInstance().getMethods().getWartelobby());
						e.getPlayer().getInventory().clear();
						e.getPlayer().getInventory().setArmorContents(null);
						e.getPlayer().updateInventory();
						e.getPlayer().setHealth(20);
						e.getPlayer().setFoodLevel(20);
						for(PotionEffect effect : e.getPlayer().getActivePotionEffects()) {
							e.getPlayer().removePotionEffect(effect.getType());
						}
						e.getPlayer().setGameMode(GameMode.SURVIVAL);
						Main.getInstance().getMethods().sendScoreboard(e.getPlayer());
								
						Main.getInstance().getMethods().broadcastJL(e.getPlayer().getPlayerListName()+" §3hat das Spiel §abetreten! §7[§e"+playerCount+"§7/§e"+Main.getInstance().getConfig().getInt("maxPlayers")+"§7]");
					}
					
					cancel();
				}
			}.runTaskLater(Main.getInstance(), 5);
		} else if(GameManager.isState(GameManager.INGAME) || GameManager.isState(GameManager.RESTART)) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
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
					
					cancel();
				}
			}.runTaskLater(Main.getInstance(), 10);
		}
	}
	
	@EventHandler
	public void onLogin(final PlayerLoginEvent e) {
		if(GameManager.isState(GameManager.LOBBY)) {
			if(Bukkit.getOnlinePlayers().size() >= Main.getInstance().getConfig().getInt("maxPlayers")) {
				e.disallow(Result.KICK_FULL, Main.prefix+"§cDer Server ist bereits voll!");
			} else {
				e.allow();
			}
		} else if(GameManager.isState(GameManager.INGAME) || GameManager.isState(GameManager.RESTART)) {
			e.allow();
		}
	}
	
}
