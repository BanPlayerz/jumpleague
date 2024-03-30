package at.banplayerz.jl.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import at.banplayerz.jl.Main;

public class Methods {

	public void setWartelobby(Player p) {
		FileConfiguration cfg = Main.getInstance().getFileManager().getLocationFileConfiguration();
		
		cfg.set("wartelobby.world", p.getWorld().getName());
		cfg.set("wartelobby.X", p.getLocation().getX());
		cfg.set("wartelobby.Y", p.getLocation().getY());
		cfg.set("wartelobby.Z", p.getLocation().getZ());
		cfg.set("wartelobby.Yaw", p.getLocation().getYaw());
		cfg.set("wartelobby.Pitch", p.getLocation().getPitch());
		
		try {
			cfg.save(Main.getInstance().getFileManager().getLocationFile());
			p.sendMessage(Main.prefix+"§aDie Wartelobby wurde erfolgreich gesetzt!");
		} catch (IOException e) {
			p.sendMessage(Main.prefix+"§cDie Wartelobby konnte nicht gesetzt werden!");
		}
	}
	
	public Location getWartelobby() {
		FileConfiguration cfg = Main.getInstance().getFileManager().getLocationFileConfiguration();
		
		if(cfg.contains("wartelobby")) {
			String world = cfg.getString("wartelobby.world");
			double x = cfg.getDouble("wartelobby.X");
			double y = cfg.getDouble("wartelobby.Y");
			double z = cfg.getDouble("wartelobby.Z");
			float yaw = (float) cfg.getDouble("wartelobby.Yaw");
			float pitch = (float) cfg.getDouble("wartelobby.Pitch");
			
			return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		}
		return null;
	}
	
	public void setModuleFinish(Player p, String map, int id, int module) {
		if(mapExists(map)) {
			FileConfiguration cfg = Main.getInstance().getFileManager().getLocationFileConfiguration();
			
			cfg.set(map+"."+id+"."+module+".world", p.getWorld().getName());
			cfg.set(map+"."+id+"."+module+".X", p.getLocation().getX());
			cfg.set(map+"."+id+"."+module+".Y", p.getLocation().getY());	
			cfg.set(map+"."+id+"."+module+".Z", p.getLocation().getZ());
			cfg.set(map+"."+id+"."+module+".Yaw", p.getLocation().getYaw());
			cfg.set(map+"."+id+"."+module+".Pitch", p.getLocation().getPitch());
			
			try {
				cfg.save(Main.getInstance().getFileManager().getLocationFile());
				p.sendMessage(Main.prefix+"§aModule-Finish wurde gesetzt!");
				p.sendMessage(Main.prefix+"§aMap§7: §e"+map);
				p.sendMessage(Main.prefix+"§aID§7: §e"+id);
				p.sendMessage(Main.prefix+"§aModul§7: §e"+module);
			} catch (IOException e) {
				p.sendMessage(Main.prefix+"§cModule-Finish konnte nicht gesetzt werden!");
			}
		} else {
			p.sendMessage(Main.prefix+"§cMap §e"+map+" §cexistiert nicht!");
		}
	}
	
	public Location getModuleFinish(String map, int id, int module) {
		if(mapExists(map)) {
			FileConfiguration cfg = Main.getInstance().getFileManager().getLocationFileConfiguration();
			
			if(cfg.contains(map+"."+id+"."+module)) {
				String world = cfg.getString(map+"."+id+"."+module+".world");
				double x = cfg.getDouble(map+"."+id+"."+module+".X");
				double y = cfg.getDouble(map+"."+id+"."+module+".Y");
				double z = cfg.getDouble(map+"."+id+"."+module+".Z");
				float yaw = (float) cfg.getDouble(map+"."+id+"."+module+".Yaw");
				float pitch = (float) cfg.getDouble(map+"."+id+"."+module+".Pitch");
				
				return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			}
		}
		return null;
	}
	
	public void setSpawn(Player p, String map, int id) {
		if(mapExists(map)) {
			FileConfiguration cfg = Main.getInstance().getFileManager().getLocationFileConfiguration();
			
			cfg.set(map+"."+id+".spawn.world", p.getWorld().getName());
			cfg.set(map+"."+id+".spawn.X", p.getLocation().getX());
			cfg.set(map+"."+id+".spawn.Y", p.getLocation().getY());
			cfg.set(map+"."+id+".spawn.Z", p.getLocation().getZ());
			cfg.set(map+"."+id+".spawn.Yaw", p.getLocation().getYaw());
			cfg.set(map+"."+id+".spawn.Pitch", p.getLocation().getPitch());
			
			try {
				cfg.save(Main.getInstance().getFileManager().getLocationFile());
				p.sendMessage(Main.prefix+"§aSpawn wurde erfolgreich gesetzt!");
				p.sendMessage(Main.prefix+"§aMap§7: §e"+map);
				p.sendMessage(Main.prefix+"§aID§7: §e"+id);
			} catch (IOException e) {
				p.sendMessage(Main.prefix+"§cSpawn konnte nicht gesetzt werden!");
			}
		} else {
			p.sendMessage(Main.prefix+"§cMap §e"+map+" §cexistiert nicht!");
		}
	}
	
	public Location getSpawn(String map, int id) {
		if(mapExists(map)) {
			FileConfiguration cfg = Main.getInstance().getFileManager().getLocationFileConfiguration();
			
			if(cfg.contains(map+"."+id+".spawn")) {
				String world = cfg.getString(map+"."+id+".spawn.world");
				double x = cfg.getDouble(map+"."+id+".spawn.X");
				double y = cfg.getDouble(map+"."+id+".spawn.Y");
				double z = cfg.getDouble(map+"."+id+".spawn.Z");
				float yaw = (float) cfg.getDouble(map+"."+id+".spawn.Yaw");
				float pitch = (float) cfg.getDouble(map+"."+id+".spawn.Pitch");
				
				return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			}
		}
		return null;
	}
	
	public void setDeathmatchSpawn(Player p, String map, int id) {
		if(mapExists(map)) {
			FileConfiguration cfg = Main.getInstance().getFileManager().getLocationFileConfiguration();
			
			cfg.set(map+"."+id+".deathmatch.world", p.getWorld().getName());
			cfg.set(map+"."+id+".deathmatch.X", p.getLocation().getX());
			cfg.set(map+"."+id+".deathmatch.Y", p.getLocation().getY());
			cfg.set(map+"."+id+".deathmatch.Z", p.getLocation().getZ());
			cfg.set(map+"."+id+".deathmatch.Yaw", p.getLocation().getYaw());
			cfg.set(map+"."+id+".deathmatch.Pitch", p.getLocation().getPitch());
			
			try {
				cfg.save(Main.getInstance().getFileManager().getLocationFile());
				p.sendMessage(Main.prefix+"§aDeathmatch-Spawn wurde erfolgreich gesetzt!");
				p.sendMessage(Main.prefix+"§aMap§7: §e"+map);
				p.sendMessage(Main.prefix+"§aID§7: §e"+id);
			} catch (IOException e) {
				p.sendMessage(Main.prefix+"§cDeathmatch-Spawn konnte nicht gesetzt werden!");
			}
		} else {
			p.sendMessage(Main.prefix+"§cMap §e"+map+" §cexistiert nicht!");
		}
	}
	
	public Location getDeathmatchSpawn(String map, int id) {
		if(mapExists(map)) {
			FileConfiguration cfg = Main.getInstance().getFileManager().getLocationFileConfiguration();
			
			if(cfg.contains(map+"."+id+".deathmatch")) {
				String world = cfg.getString(map+"."+id+".deathmatch.world");
				double x = cfg.getDouble(map+"."+id+".deathmatch.X");
				double y = cfg.getDouble(map+"."+id+".deathmatch.Y");
				double z = cfg.getDouble(map+"."+id+".deathmatch.Z");
				float yaw = (float) cfg.getDouble(map+"."+id+".deathmatch.Yaw");
				float pitch = (float) cfg.getDouble(map+"."+id+".deathmatch.Pitch");
				
				return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
			}
		}
		return null;
	}
	
	public boolean mapExists(String map) {
		if(Main.getInstance().getConfig().getStringList("maps").contains(map)) {
			return true;
		}
		return false;
	}
	
	public boolean mapIsOk(String map) {
		boolean mapOk = true;

		for(int id = 1; id <= Main.getInstance().getConfig().getInt("maxPlayers"); id++) {
			for(int module = 1; module <= 10; module++) {
				if(Main.getInstance().getMethods().getModuleFinish(map, id, module) == null) {
					mapOk = false;
				}
			}	
		}

		for(int id = 1; id <= Main.getInstance().getConfig().getInt("maxPlayers"); id++) {
			if(Main.getInstance().getMethods().getSpawn(map, id) == null) {
				mapOk = false;
			}
		}
		
		for(int id = 1; id <= Main.getInstance().getConfig().getInt("maxPlayers"); id++) {
			if(Main.getInstance().getMethods().getDeathmatchSpawn(map, id) == null) {
				mapOk = false;
			}
		}
		
		return mapOk;
	}
	
	public boolean jumpLeagueIsOk() {
		boolean ok = true;
		
		if(Main.getInstance().getConfig().getStringList("maps").size() < 1) {
			ok = false;
		}
		
		if(getWartelobby() == null) {
			ok = false;
		}
		
		for(String map : Main.getInstance().getConfig().getStringList("maps")) {
			if(Main.getInstance().getMethods().mapExists(map)) {
				if(!Main.getInstance().getMethods().mapIsOk(map)) {
					ok = false;
				}
			} else {
				ok = false;
			}
		}
		
		return ok;
	}
	
	public void leaveJL(Player p) {
		if(!Main.getInstance().spectators.contains(p.getUniqueId().toString())) {
			int playerCount = 0;
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(!Main.getInstance().spectators.contains(players.getUniqueId().toString())) {
					playerCount++;
				}
			}
			
			if(GameManager.isState(GameManager.LOBBY)){
				broadcastJL(p.getPlayerListName()+" §3hat das Spiel §cverlassen! §7[§e"+(playerCount-1)+"§7/§e"+Main.getInstance().getConfig().getInt("maxPlayers")+"§7]");
			} else if(GameManager.isState(GameManager.INGAME)) {
				broadcastJL(p.getPlayerListName()+" §3hat das Spiel verlassen!");
				broadcastJL("§3Verbleibende Spieler§7: §e"+(playerCount-1)+"§7/§e"+Main.getInstance().getConfig().getInt("maxPlayers"));
			} else if(GameManager.isState(GameManager.RESTART)) {
				broadcastJL(p.getPlayerListName()+" §3hat das Spiel verlassen!");
			}
		}
		if(Main.getInstance().playerIDs.containsKey(p.getUniqueId().toString())) {
			Main.getInstance().playerIDs.remove(p.getUniqueId().toString());
		}
		
		if(Main.getInstance().playerModules.containsKey(p.getUniqueId().toString())) {
			Main.getInstance().playerModules.remove(p.getUniqueId().toString());
		}
	}
	
	@SuppressWarnings("deprecation")
	public void checkendGame() {
		if(GameManager.isState(GameManager.INGAME)) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if(Main.getInstance().deathmatchValue > 0) { //eventuell hier if(Main.getInstance().deathmatchCD) {
//						ArrayList<String> pl = new ArrayList<>();
//						for(Player players : Bukkit.getOnlinePlayers()) {
//							if(!Main.getInstance().spectators.contains(players.getUniqueId().toString())) {
//								pl.add(players.getUniqueId().toString());
//							}
//						}
						ArrayList<String> pl = new ArrayList<>();
						for(String uuid : Main.getInstance().playerIDs.keySet()) {
							if(!Main.getInstance().spectators.contains(uuid)) {
								pl.add(uuid);
							}
						}
						
						if(!pl.isEmpty()) { //empty eventuell entfernen
							if(pl.size() <= 1) {
								String l = pl.get(0);
								UUID u = UUID.fromString(l);
								String name = UUIDFetcher.getName(u);
								
								Player last = Bukkit.getPlayer(name);
								if(last != null) {
									Main.getInstance().ingameCD = false;
									Main.getInstance().deathmatchCD = false;
									
									broadcastJL(last.getPlayerListName()+" §3hat das Spiel gewonnen!");
//									StatsManager.addWins(last.getUniqueId().toString(), 1);
//									StatsManager.addCoins(last.getUniqueId().toString(), 500);
								
									for(Player all : Bukkit.getOnlinePlayers()){
										all.sendTitle(last.getPlayerListName(), "§3hat das Spiel gewonnen!");
									}
									
									/*for(Player all : Bukkit.getOnlinePlayers()) {
										if(at.banplayerz.bcnick.Main.getInstance().nicked.containsKey(all.getUniqueId().toString())) {
											at.banplayerz.bcnick.Main.getInstance().getMethods().unnickPlayer(all, "unnick");
										}
									}*/
								
									Main.getInstance().getCountdownManager().startRestartCD();
								} else {
									Main.getInstance().ingameCD = false;
									Main.getInstance().deathmatchCD = false;
									
									broadcastJL("§cNiemand konnte sich als Sieger dieser Runde auszeichnen!");	
									
									/*for(Player all : Bukkit.getOnlinePlayers()) {
										if(at.banplayerz.bcnick.Main.getInstance().nicked.containsKey(all.getUniqueId().toString())) {
											at.banplayerz.bcnick.Main.getInstance().getMethods().unnickPlayer(all, "unnick");
										}
									}*/
									
									Main.getInstance().getCountdownManager().startRestartCD();
								}
							}
						} else {
							Main.getInstance().ingameCD = false;
							Main.getInstance().deathmatchCD = false;
							
							broadcastJL("§cNiemand konnte sich als Sieger dieser Runde auszeichnen!");	
							
							/*for(Player all : Bukkit.getOnlinePlayers()) {
								if(at.banplayerz.bcnick.Main.getInstance().nicked.containsKey(all.getUniqueId().toString())) {
									at.banplayerz.bcnick.Main.getInstance().getMethods().unnickPlayer(all, "unnick");
								}
							}*/
							
							Main.getInstance().getCountdownManager().startRestartCD();
						}
					} else {
						Main.getInstance().ingameCD = false;
						Main.getInstance().deathmatchCD = false;
						
						broadcastJL("§cNiemand konnte sich als Sieger dieser Runde auszeichnen!");	
						
						/*for(Player all : Bukkit.getOnlinePlayers()) {
							if(at.banplayerz.bcnick.Main.getInstance().nicked.containsKey(all.getUniqueId().toString())) {
								at.banplayerz.bcnick.Main.getInstance().getMethods().unnickPlayer(all, "unnick");
							}
						}*/
						
						Main.getInstance().getCountdownManager().startRestartCD();
					}
					cancel();
					
				}
			}.runTaskLater(Main.getInstance(), 5);
		}
	}
	
	public void sendScoreboard(Player p) {
		try {
			ScoreboardManager sm = Bukkit.getScoreboardManager();
			Scoreboard board = sm.getNewScoreboard();
			Objective score = board.registerNewObjective("aaa", Criteria.DUMMY, "bbb");
			
			score.setDisplayName("§cJumpLeague");
			score.setDisplaySlot(DisplaySlot.SIDEBAR);
			
			if(GameManager.isState(GameManager.LOBBY)) {
				score.getScore(" ").setScore(110);
				score.getScore("§fSpielstart§7:").setScore(109);
				int min = Main.getInstance().lobbyValue / 60;
				int sec = Main.getInstance().lobbyValue % 60;
				if(sec < 10){
					score.getScore("§7» §e"+min+"§7:§e0"+sec).setScore(108);
				} else {
					score.getScore("§7» §e"+min+"§7:§e"+sec).setScore(108);
				}
				score.getScore("  ").setScore(107);
				
				p.setScoreboard(board);
			} else if(GameManager.isState(GameManager.INGAME)) {
				if(Main.getInstance().ingameCD) {
					score.getScore(" ").setScore(110);
					score.getScore("§fDeathmatch§7:").setScore(109);
					int v = Main.getInstance().gameValue;
					if(v > 600) {
						v = 600;
					}
					int min = v / 60;
					int sec = v % 60;
					if(sec < 10){
						score.getScore("§7» §e"+min+"§7:§e0"+sec).setScore(108);
					} else {
						score.getScore("§7» §e"+min+"§7:§e"+sec).setScore(108);
					}
					score.getScore("  ").setScore(107);
					
					for(String uuid : Main.getInstance().playerIDs.keySet()) {
						if(Bukkit.getPlayer(UUID.fromString(uuid)) != null) {
							Player target = Bukkit.getPlayer(UUID.fromString(uuid));
							
							if(target != null) {
								int id = Main.getInstance().playerIDs.get(uuid);
								
								Location spawn = Main.getInstance().getMethods().getSpawn(Main.getInstance().map, id);
								Location end = Main.getInstance().getMethods().getModuleFinish(Main.getInstance().map, id, 10);
								
								int s = (int) ((spawn.distance(target.getLocation()) / spawn.distance(end)) * 100);
								if(s < 0) {
									s = 0;
								}
								if(s > 100) {
									s = 100;
								}
								score.getScore(target.getPlayerListName()).setScore(s);
							}
						}
					}
					
					p.setScoreboard(board);	
				} else if(Main.getInstance().deathmatchCD) {
					score.getScore(" ").setScore(110);
					score.getScore("§fSpielende§7:").setScore(109);
					int v = Main.getInstance().deathmatchValue;
					if(v > 300) {
						v = 300;
					}
					int min = v / 60;
					int sec = v % 60;
					if(sec < 10){
						score.getScore("§7» §e"+min+"§7:§e0"+sec).setScore(108);
					} else {
						score.getScore("§7» §e"+min+"§7:§e"+sec).setScore(108);
					}
					score.getScore("  ").setScore(107);
					
					for(String uuid : Main.getInstance().playerLives.keySet()) {
						if(Bukkit.getPlayer(UUID.fromString(uuid)) != null) {
							Player target = Bukkit.getPlayer(UUID.fromString(uuid));

							if(target != null) {
								score.getScore(target.getPlayerListName()).setScore(Main.getInstance().playerLives.get(p.getUniqueId().toString()));
							}
						}
					}
					
					p.setScoreboard(board);	
				}
			} else {
				p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			}
		} catch(Exception ex) {
			
		}
		
	}
	
	public void connectToLobby(Player p){
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			
			if(!autoSilentLobbyContains(p.getUniqueId().toString())) {
				out.writeUTF(Main.getInstance().getConfig().getStringList("lobbyServers").
						get(new Random().nextInt(Main.getInstance().getConfig().getStringList("lobbyServers").size())));
			} else {
				out.writeUTF(Main.getInstance().getConfig().getStringList("silentlobbyServers").
						get(new Random().nextInt(Main.getInstance().getConfig().getStringList("silentlobbyServers").size())));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.sendPluginMessage(Main.getInstance(), "BungeeCord", b.toByteArray());
		
	}
	
	public boolean autoSilentLobbyContains(String uuid) {
		ResultSet rs = MySQL.getResult("SELECT * FROM JLAutoSilentLobby WHERE UUID='"+uuid+"'");
		
		try {
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {}
		return false;
	}
	
	public void addAutoSilentLobby(String uuid) {
		if(!autoSilentLobbyContains(uuid)) {
			MySQL.update("INSERT INTO JLAutoSilentLobby (UUID) VALUES ('"+uuid+"')");
		}
	}
	
	public void removeAutoSilentLobby(String uuid) {
		if(autoSilentLobbyContains(uuid)) {
			MySQL.update("DELETE FROM JLAutoSilentLobby WHERE UUID='"+uuid+"'");
		}
	}

	public void broadcastJL(String nachricht){
		for(Player all : Bukkit.getOnlinePlayers()){
			all.sendMessage(Main.prefix+nachricht);
		}
	}
	
	public void sendPluginInfo(CommandSender sender, boolean help) {
		PluginDescriptionFile plugin = Main.getInstance().getDescription();
		sender.sendMessage(Main.prefix+"§a"+plugin.getName()+" v."+plugin.getVersion()+" by "+plugin.getAuthors().get(0));
		if(help) {
			sender.sendMessage(Main.prefix+"§aFür Hilfe benutze §e/jumpleague help");
		}
	}
	
	public void sendPluginHelp(CommandSender sender) {
		sender.sendMessage(Main.prefix+"§7--- §aJumpLeague Hilfe §7---");
		sender.sendMessage("§e/jumpleague setwartelobby §7Setze die Wartelobby");
		sender.sendMessage("§e/jumpleague setmodulefinish <Map> <ID> <Modul> §7Setze das Ende eines Moduls");
		sender.sendMessage("§e/jumpleague setspawn <Map> <ID> §7Setze den Beginn des ersten Moduls");
		sender.sendMessage("§e/jumpleague setdeathmatchspawn <Map> <ID> §7Setze den Deathmatch-Spawn für Spieler");
		sender.sendMessage("§e/jumpleague check | §e/jumpleague check <Map> §7Überprüfe die Spielbarkeit von Maps");
	}
	
}
