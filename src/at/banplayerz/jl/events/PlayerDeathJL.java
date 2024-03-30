package at.banplayerz.jl.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import at.banplayerz.jl.Main;
import at.banplayerz.jl.utils.GameManager;

public class PlayerDeathJL implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		if(GameManager.isState(GameManager.INGAME) && Main.getInstance().deathmatchCD) {
			if(e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				
				int lives = Main.getInstance().playerLives.get(p.getUniqueId().toString());
				lives = lives - 1;
				Main.getInstance().playerLives.remove(p.getUniqueId().toString());
				Main.getInstance().playerLives.put(p.getUniqueId().toString(), lives);
				if(Main.getInstance().playerLives.get(p.getUniqueId().toString()) == 0) {
					if(Main.getInstance().playerIDs.containsKey(p.getUniqueId().toString())) {
						Main.getInstance().playerIDs.remove(p.getUniqueId().toString());
					}
					
					if(Main.getInstance().playerModules.containsKey(p.getUniqueId().toString())) {
						Main.getInstance().playerModules.remove(p.getUniqueId().toString());
					}
					Main.getInstance().playerLives.remove(p.getUniqueId().toString());
				}
				
				//adddeath to p
				
				if(p.getKiller() instanceof Player) {
					Player k = (Player) p.getKiller();
					
					//addkill to k
					
					Main.getInstance().getMethods().broadcastJL(p.getPlayerListName()+" §3wurde von "+k.getPlayerListName()+" §3getötet!");
				} else {
					Main.getInstance().getMethods().broadcastJL(p.getPlayerListName()+" §3ist gestorben!");
				}
				
				if(Main.getInstance().playerLives.containsKey(p.getUniqueId().toString())) {
					p.spigot().respawn();
					p.teleport(Main.getInstance().getMethods().getDeathmatchSpawn(Main.getInstance().map,
							Main.getInstance().playerIDs.get(p.getUniqueId().toString())));
				}
				
				Main.getInstance().getMethods().checkendGame();
			}
		}
	}
	
}
