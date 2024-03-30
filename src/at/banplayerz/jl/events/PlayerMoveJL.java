package at.banplayerz.jl.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import at.banplayerz.jl.Main;
import at.banplayerz.jl.utils.GameManager;

public class PlayerMoveJL implements Listener {

	@EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
	    if(Main.getInstance().dontMove) {
	        if(!Main.getInstance().spectators.contains(p.getUniqueId().toString())) {
	        	Location from = e.getFrom();
		        Location to = e.getTo();
		        double x = Math.floor(from.getX());
		        double z = Math.floor(from.getZ());
		        if(Math.floor(to.getX()) != x || Math.floor(to.getZ()) != z) {
		            x += .5;
		            z += .5;
		            e.getPlayer().teleport(new Location(from.getWorld(), x, from.getY(), z, from.getYaw(), from.getPitch()));
		        }
	        }
	    }
	    
	    if(GameManager.isState(GameManager.INGAME)) {
	    	if(Main.getInstance().ingameCD) {
	    		if(p.getLocation().getY() <= Main.getInstance().getConfig().getInt("deathY")) {
	    			int module = Main.getInstance().playerModules.get(p.getUniqueId().toString());
	    			int id = Main.getInstance().playerIDs.get(p.getUniqueId().toString());
	    			if(module == 1) {
	    				p.teleport(Main.getInstance().getMethods().getSpawn(Main.getInstance().map, id));
	    			} else {
	    				p.teleport(Main.getInstance().getMethods().getModuleFinish(Main.getInstance().map, id, module-1));
	    			}
	    		}
	    	}
	    	
	    	int module = Main.getInstance().playerModules.get(p.getUniqueId().toString());
	    	if(module < 11) {
	    		int id = Main.getInstance().playerIDs.get(p.getUniqueId().toString());
		    	
		    	int x = p.getLocation().getBlockX(), y = p.getLocation().getBlockY(), z = p.getLocation().getBlockZ();
		    	Location loc = Main.getInstance().getMethods().getModuleFinish(Main.getInstance().map, id, module);
		    	
		    	if(loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z) {
		    		Main.getInstance().playerModules.remove(p.getUniqueId().toString());
		    		Main.getInstance().playerModules.put(p.getUniqueId().toString(), module+1);
		    		p.sendMessage(Main.prefix+"§aDu hast das §e"+module+". Modul §ageschafft!");
		    		if(Main.getInstance().playerModules.get(p.getUniqueId().toString()) == 11) {
		    			Main.getInstance().getMethods().broadcastJL(p.getPlayerListName()+" §3hat das Ziel erreicht!");
		    			if(Main.getInstance().gameValue > 11) {
		    				Main.getInstance().gameValue = 11;
		    			}
		    		}
		    	}
	    	}
	    }
	}
}
