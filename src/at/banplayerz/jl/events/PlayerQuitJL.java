package at.banplayerz.jl.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import at.banplayerz.jl.Main;
import at.banplayerz.jl.utils.GameManager;

public class PlayerQuitJL implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e){
		Player p = e.getPlayer();
		e.setQuitMessage(null);
		if(GameManager.isState(GameManager.INGAME)) {
			Main.getInstance().getMethods().leaveJL(p);
			if(Main.getInstance().spectators.contains(p.getUniqueId().toString())){
				Main.getInstance().spectators.remove(p.getUniqueId().toString());
			}
			Main.getInstance().getMethods().checkendGame();
		} else if(GameManager.isState(GameManager.LOBBY)) {
			Main.getInstance().getMethods().leaveJL(p);
			if(Main.getInstance().spectators.contains(p.getUniqueId().toString())){
				Main.getInstance().spectators.remove(p.getUniqueId().toString());
			}
		} else if(GameManager.isState(GameManager.RESTART)) {
			Main.getInstance().getMethods().leaveJL(p);
			if(Main.getInstance().spectators.contains(p.getUniqueId().toString())){
				Main.getInstance().spectators.remove(p.getUniqueId().toString());
			}
		}
		
	}
	
}
