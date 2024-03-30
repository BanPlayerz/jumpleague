package at.banplayerz.jl.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import at.banplayerz.jl.Main;
import at.banplayerz.jl.utils.GameManager;

public class PlayerDamageJL implements Listener {
	
	@EventHandler
	public void onDMG(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(GameManager.isState(GameManager.INGAME) && Main.getInstance().deathmatchCD) {
				if(Main.getInstance().spectators.contains(p.getUniqueId().toString())) {
					e.setCancelled(true);
				}
			} else {
				e.setCancelled(true);
			}
		}
	}

}
