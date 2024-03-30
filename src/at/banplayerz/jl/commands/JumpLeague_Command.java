package at.banplayerz.jl.commands;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.banplayerz.jl.Main;

public class JumpLeague_Command implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("jumpleague")) {
			if(sender instanceof Player) {
				Player p = (Player) sender;
				
				if(args.length == 0) {
					if(p.hasPermission("jl.admin")) {
						Main.getInstance().getMethods().sendPluginInfo(p, true);
					} else {
						Main.getInstance().getMethods().sendPluginInfo(p, false);
					}
				} else if(args.length == 1) {
					if(p.hasPermission("jl.admin")) {
						if(args[0].equalsIgnoreCase("setwartelobby")) {
							Main.getInstance().getMethods().setWartelobby(p);
						} else if(args[0].equalsIgnoreCase("check")) {
							HashMap<String, Boolean> mapStatus = new HashMap<>();
							
							for(String map : Main.getInstance().getConfig().getStringList("maps")) {
								if(Main.getInstance().getMethods().mapExists(map)) {
									if(Main.getInstance().getMethods().mapIsOk(map)) {
										mapStatus.put(map, true);
									} else {
										mapStatus.put(map, false);
									}
								} else {
									mapStatus.put(map, false);
								}
							}
							
							boolean ok = true;
							
							if(Main.getInstance().getMethods().getWartelobby() == null) {
								ok = false;
							}
							
							for(String m : mapStatus.keySet()) {
								if(!mapStatus.get(m)) {
									ok = false;
								}
							}
							
							if(ok) {
								p.sendMessage(Main.prefix+"§aJumpLeague Check§7: §aOK");
							} else {
								p.sendMessage(Main.prefix+"§aJumpLeague Check§7: §cX");
							}
							
							if(Main.getInstance().getMethods().getWartelobby() == null) {
								p.sendMessage("§eWartelobby§7: §cX");
							} else {
								p.sendMessage("§eWartelobby§7: §aOK");
							}
							
							for(String m : mapStatus.keySet()) {
								if(mapStatus.get(m)) {
									p.sendMessage("§e"+m+"§7: §aOK");
								} else {
									p.sendMessage("§e"+m+"§7: §cX");
								}
							}
						} else if(args[0].equalsIgnoreCase("help")) {
							Main.getInstance().getMethods().sendPluginHelp(p);
						} else {
							Main.getInstance().getMethods().sendPluginInfo(p, true);
						}
					} else {
						Main.getInstance().getMethods().sendPluginInfo(p, false);
					}
				} else if(args.length == 2) {
					if(p.hasPermission("jl.admin")) {
						if(args[0].equalsIgnoreCase("check")) {
							if(Main.getInstance().getMethods().mapExists(args[1])) {
								if(Main.getInstance().getMethods().mapIsOk(args[1])) {
									p.sendMessage(Main.prefix+"§aJumpLeague Map Check§7: §e"+args[1]+" §aOK");
								} else {
									p.sendMessage(Main.prefix+"§aJumpLeague Map Check§7: §e"+args[1]+" §cX");
								}
								
								boolean moduleFinishError = false;
								int mfeID = 1, mfeModule = 1;
								for(int id = 1; id <= Main.getInstance().getConfig().getInt("maxPlayers"); id++) {
									for(int module = 1; module <= 10; module++) {
										if(Main.getInstance().getMethods().getModuleFinish(args[1], id, module) == null) {
											if(!moduleFinishError) {
												moduleFinishError = true;
												mfeID = id;
												mfeModule = module;
											}
											break;
										}
									}
								}
								
								if(moduleFinishError) {
									p.sendMessage("§eModule Finish§7: §cX §7(§eID: "+mfeID+" Modul: "+mfeModule+" nicht gesetzt)");
								} else {
									p.sendMessage("§eModule Finish§7: §aOK");
								}
								
								boolean spawnError = false;
								int spawnID = 1;
								for(int id = 1; id <= Main.getInstance().getConfig().getInt("maxPlayers"); id++) {
									if(Main.getInstance().getMethods().getSpawn(args[1], id) == null) {
										spawnError = true;
										spawnID = id;
										break;
									}
								}
								
								if(spawnError) {
									p.sendMessage("§eSpawns§7: §cX §7(§eID: "+spawnID+" nicht gesetzt)");
								} else {
									p.sendMessage("§eSpawns§7: §aOK");
								}
								
								boolean deathmatchSpawn = false;
								int deathmatchID = 1;
								for(int id = 1; id <= Main.getInstance().getConfig().getInt("maxPlayers"); id++) {
									if(Main.getInstance().getMethods().getDeathmatchSpawn(args[1], id) == null) {
										deathmatchSpawn = true;
										deathmatchID = id;
										break;
									}
								}
								
								if(deathmatchSpawn) {
									p.sendMessage("§eDeathmatch-Spawns§7: §cX §7(§eID: "+deathmatchID+" nicht gesetzt)");
								} else {
									p.sendMessage("§eDeathmatch-Spawns§7: §aOK");
								}
							} else {
								p.sendMessage(Main.prefix+"§cMap §e"+args[1]+" §cexistiert nicht!");
							}
						} else {
							Main.getInstance().getMethods().sendPluginInfo(p, true);
						}
					} else {
						Main.getInstance().getMethods().sendPluginInfo(p, false);
					}
				} else if(args.length == 3) {
					if(p.hasPermission("jl.admin")) {
						if(args[0].equalsIgnoreCase("setspawn")) {
							try {
								Main.getInstance().getMethods().setSpawn(p, args[1], Integer.valueOf(args[2]));
							} catch(NumberFormatException ex) {
								p.sendMessage(Main.prefix+"§c/jumpleague setspawn <Map> <ID>");
							}
						} else if(args[0].equalsIgnoreCase("setdeathmatchspawn")) {
							try {
								Main.getInstance().getMethods().setDeathmatchSpawn(p, args[1], Integer.valueOf(args[2]));
							} catch(NumberFormatException ex) {
								p.sendMessage(Main.prefix+"§c/jumpleague setdeathmatchspawn <Map> <ID>");
							}
						} else {
							Main.getInstance().getMethods().sendPluginInfo(p, true);
						}
					} else {
						Main.getInstance().getMethods().sendPluginInfo(p, false);
					}
				} else if(args.length == 4) {
					if(p.hasPermission("jl.admin")) {
						if(args[0].equalsIgnoreCase("setmodulefinish")) {
							try {
								Main.getInstance().getMethods().setModuleFinish(p, args[1], Integer.valueOf(args[2]), Integer.valueOf(args[3]));
							} catch(NumberFormatException ex) {
								p.sendMessage(Main.prefix+"§c/jl setmodulefinish <Map> <ID> <Modul>");
							}
						} else {
							Main.getInstance().getMethods().sendPluginInfo(p, true);
						}
					} else {
						Main.getInstance().getMethods().sendPluginInfo(p, false);
					}
				} else {
					if(p.hasPermission("jl.admin")) {
						Main.getInstance().getMethods().sendPluginInfo(p, true);
					} else {
						Main.getInstance().getMethods().sendPluginInfo(p, false);
					}
				}
			}
		}
		return false;
	}

}
