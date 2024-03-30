package at.banplayerz.jl.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import at.banplayerz.jl.Main;
import at.banplayerz.jl.utils.GameManager;

public class PlayerDenyJL implements Listener {
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e){
		if(!Main.getInstance().spectators.contains(e.getPlayer().getUniqueId().toString())) {
			if(GameManager.isState(GameManager.LOBBY) || GameManager.isState(GameManager.RESTART)){
				e.setCancelled(true);
			}
		} else {
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void onPickupItem(EntityPickupItemEvent e){
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			if(!Main.getInstance().spectators.contains(p.getUniqueId().toString())) {
				if(GameManager.isState(GameManager.LOBBY) || GameManager.isState(GameManager.RESTART)){
					e.setCancelled(true);
				}
			} else {
				e.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler
	public void onFood(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			if(!Main.getInstance().spectators.contains(e.getEntity().getUniqueId().toString())) {
				if(GameManager.isState(GameManager.INGAME)) {
					if(Main.getInstance().ingameCD) {
						e.setCancelled(true);
					}
				} else if(GameManager.isState(GameManager.LOBBY) || GameManager.isState(GameManager.RESTART)) {
					e.setCancelled(true);
				}
			} else {
				e.setCancelled(true);
			}
		}
	}

}
