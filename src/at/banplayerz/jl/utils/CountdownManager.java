package at.banplayerz.jl.utils;

import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import at.banplayerz.jl.Main;

public class CountdownManager {

	public void startLobbyCD() {
		if(!Main.getInstance().lobbyCD) {
			Main.getInstance().lobbyCD = true;
			
			if(Main.getInstance().getMethods().getWartelobby() != null) {
				for(Player players : Bukkit.getOnlinePlayers()) {
					players.teleport(Main.getInstance().getMethods().getWartelobby());
				}
			}
			
			Main.getInstance().map = Main.getInstance().getConfig().getStringList("maps").
					get(new Random().nextInt(Main.getInstance().getConfig().getStringList("maps").size()));

			new BukkitRunnable() {
				
				int lobbyWarn = 0;
				
				@Override
				public void run() {
					if(Bukkit.getOnlinePlayers().size() >= Main.getInstance().getConfig().getInt("minPlayers")){
						if(Main.getInstance().lobbyValue == 60 || Main.getInstance().lobbyValue == 30 || Main.getInstance().lobbyValue == 20 ||
								Main.getInstance().lobbyValue == 10 || Main.getInstance().lobbyValue == 5 || Main.getInstance().lobbyValue == 4 ||
								Main.getInstance().lobbyValue == 3 || Main.getInstance().lobbyValue == 2) {
							Main.getInstance().getMethods().broadcastJL("§3Das Spiel startet in §e"+Main.getInstance().lobbyValue+" §3Sekunden!");
						} else if(Main.getInstance().lobbyValue == 1) {
							Main.getInstance().getMethods().broadcastJL("§3Das Spiel startet in §eeiner §3Sekunde!");
						} else if(Main.getInstance().lobbyValue == 0) {
							Main.getInstance().lobbyCD = false;
							startIngameCD();
						}
						
						if(Main.getInstance().lobbyValue > 0) {
							for(Player players : Bukkit.getOnlinePlayers()) {
								if(!Main.getInstance().spectators.contains(players.getUniqueId().toString())) {
									players.setLevel(Main.getInstance().lobbyValue);
									Main.getInstance().getMethods().sendScoreboard(players);
								}
							}
							Main.getInstance().lobbyValue--;
						}
					} else {
						Main.getInstance().lobbyValue = 60;
						lobbyWarn++;
					
						if(lobbyWarn == 30){
							lobbyWarn = 0;
							Main.getInstance().getMethods().broadcastJL("§cEs sind zu wenig Spieler online! "
									+ "Es werden insgesamt §e"+Main.getInstance().getConfig().getInt("minPlayers")+" §cSpieler benötigt!");
							
							for(Player players : Bukkit.getOnlinePlayers()){
								if(!Main.getInstance().spectators.contains(players.getUniqueId().toString())) {
									players.setLevel(0);
									Main.getInstance().getMethods().sendScoreboard(players);
								}
							}
						}
					}
					
	
					if(!Main.getInstance().lobbyCD) {
						cancel();
					}
				}
			}.runTaskTimer(Main.getInstance(), 0, 20);
		}
	}
	
	public void startIngameCD() {
		if(!Main.getInstance().ingameCD) {
			Main.getInstance().ingameCD = true;
			
			GameManager.setState(GameManager.INGAME);
			
			int i = 1;
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(!Main.getInstance().spectators.contains(players.getUniqueId().toString())) {
					Main.getInstance().playerIDs.put(players.getUniqueId().toString(), i);
					Main.getInstance().playerModules.put(players.getUniqueId().toString(), 1);
					players.teleport(Main.getInstance().getMethods().getSpawn(Main.getInstance().map, i));
					players.sendMessage(Main.prefix+"§eAlle Spieler werden auf ihre Plätze teleportiert!");
					i++;
				}
			}
					
			new BukkitRunnable() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					if(Main.getInstance().gameValue == 610) {
						Main.getInstance().dontMove = true;
						for(Player players : Bukkit.getOnlinePlayers()) {
							players.sendTitle("§610", "");
						}
					}
					if(Main.getInstance().gameValue <= 609 && Main.getInstance().gameValue >= 601) {
						for(Player players : Bukkit.getOnlinePlayers()) {
							players.sendTitle("§6"+(Main.getInstance().gameValue-600), "");
						}
					}
					if(Main.getInstance().gameValue == 600) {
						Main.getInstance().dontMove = false;
						for(Player players : Bukkit.getOnlinePlayers()) {
							players.sendTitle("§aLos!", "");
						}						
					}
					
					if(Main.getInstance().gameValue <= 10 && Main.getInstance().gameValue >= 2) {
						Main.getInstance().getMethods().broadcastJL("§3Das Deathmatch startet in §e"+Main.getInstance().gameValue+" §3Sekunden!");
					}
					
					if(Main.getInstance().gameValue == 1) {
						Main.getInstance().getMethods().broadcastJL("§3Das Deathmatch startet in §eeiner §3Sekunden!");
					}
					
					if(Main.getInstance().gameValue == 0) {
						Main.getInstance().ingameCD = false;
						startDeathmatchCD();
					}
					
					for(Player players : Bukkit.getOnlinePlayers()) {
						Main.getInstance().getMethods().sendScoreboard(players);
					}
					
					if(Main.getInstance().gameValue > 0) {
						Main.getInstance().gameValue--;
					}
					
					if(!Main.getInstance().ingameCD) {
						cancel();
					}
					
				}
			}.runTaskTimer(Main.getInstance(), 0, 20);
		}
	}
	
	public void startDeathmatchCD() {
		if(!Main.getInstance().deathmatchCD) {
			Main.getInstance().deathmatchCD = true;
	
			for(String uuid : Main.getInstance().playerIDs.keySet()) {
				if(!Main.getInstance().spectators.contains(uuid)) {
					Player target = Bukkit.getPlayer(UUID.fromString(uuid));
					
					if(target != null) {
						Main.getInstance().playerLives.put(uuid, 3);
						target.teleport(Main.getInstance().getMethods().getDeathmatchSpawn(Main.getInstance().map, Main.getInstance().playerIDs.get(uuid)));
						target.sendMessage(Main.prefix+"§eAlle Spieler werden auf ihre Deathmatch-Plätze teleportiert!");
					}
				}
			}
			
			new BukkitRunnable() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					if(Main.getInstance().deathmatchValue == 310) {
						Main.getInstance().dontMove = true;
						for(Player players : Bukkit.getOnlinePlayers()) {
							players.sendTitle("§610", "");
						}
					}
					if(Main.getInstance().deathmatchValue <= 309 && Main.getInstance().deathmatchValue >= 301) {
						for(Player players : Bukkit.getOnlinePlayers()) {
							players.sendTitle("§6"+(Main.getInstance().deathmatchValue-300), "");
						}
					}
					if(Main.getInstance().deathmatchValue == 300) {
						Main.getInstance().dontMove = false;
						for(Player players : Bukkit.getOnlinePlayers()) {
							players.sendTitle("§aLos!", "");
						}						
					}
					
					if(Main.getInstance().deathmatchValue <= 10 && Main.getInstance().deathmatchValue >= 2) {
						Main.getInstance().getMethods().broadcastJL("§3Das Spiel endet in §e"+Main.getInstance().deathmatchValue+" §3Sekunden!");
					}
					
					if(Main.getInstance().deathmatchValue == 1) {
						Main.getInstance().getMethods().broadcastJL("§3Das Spiel endet in §eeiner §3Sekunde!");
					}
					
					if(Main.getInstance().deathmatchValue == 0) {
						Main.getInstance().deathmatchCD = false;
						Main.getInstance().getMethods().checkendGame();
					}
					
					for(Player players : Bukkit.getOnlinePlayers()) {
						Main.getInstance().getMethods().sendScoreboard(players);
					}
					
					if(Main.getInstance().deathmatchValue > 0) {
						Main.getInstance().deathmatchValue--;
					}
					
					if(!Main.getInstance().deathmatchCD) {
						cancel();
					}
				}
				
			}.runTaskTimer(Main.getInstance(), 0, 20);
		}
	}
	
	public void startRestartCD() {
		if(!Main.getInstance().restartCD) {
			Main.getInstance().restartCD = true;
			
			if(Main.getInstance().getMethods().getWartelobby() != null) {
				for(Player players : Bukkit.getOnlinePlayers()) {
					players.teleport(Main.getInstance().getMethods().getWartelobby());
				}
			}
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if(Main.getInstance().restartValue == 15 || Main.getInstance().restartValue == 10 || Main.getInstance().restartValue == 5 ||
							Main.getInstance().restartValue == 4 || Main.getInstance().restartValue == 3 || Main.getInstance().restartValue == 2) {
						Main.getInstance().getMethods().broadcastJL("§3Der Server startet in §e"+Main.getInstance().restartValue+" §3Sekunden neu!");
					} else if(Main.getInstance().restartValue == 1) {
						Main.getInstance().getMethods().broadcastJL("§3Der Server startet in §eeiner §3Sekunde neu!");
					} else if(Main.getInstance().restartValue == 0) {
						Main.getInstance().restartCD = false;
	
						for(Player players : Bukkit.getOnlinePlayers()) {
							Main.getInstance().getMethods().connectToLobby(players);
						}
						
						new BukkitRunnable() {
							
							@Override
							public void run() {
								Bukkit.shutdown();								
							}
						}.runTaskLater(Main.getInstance(), 20);
					}
					
					if(Main.getInstance().restartValue > 0) {
						Main.getInstance().restartValue--;
					}
					
					for(Player players : Bukkit.getOnlinePlayers()) {
						Main.getInstance().getMethods().sendScoreboard(players);
					}
					
					if(!Main.getInstance().restartCD) {
						cancel();
					}
				}
				
			}.runTaskTimer(Main.getInstance(), 0, 20);
		}		
	}
	
}
