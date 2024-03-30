package at.banplayerz.jl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import at.banplayerz.jl.commands.JumpLeague_Command;
import at.banplayerz.jl.commands.Lobby_Command;
import at.banplayerz.jl.commands.Start_Command;
import at.banplayerz.jl.events.PlayerDeathJL;
import at.banplayerz.jl.events.PlayerDenyJL;
import at.banplayerz.jl.events.PlayerJoinJL;
import at.banplayerz.jl.events.PlayerMoveJL;
import at.banplayerz.jl.events.PlayerQuitJL;
import at.banplayerz.jl.events.PlayerRepawnJL;
import at.banplayerz.jl.utils.CountdownManager;
import at.banplayerz.jl.utils.FileManager;
import at.banplayerz.jl.utils.GameManager;
import at.banplayerz.jl.utils.Methods;
import at.banplayerz.jl.utils.MySQL;

public class Main extends JavaPlugin {
	
	public static Main instance;
	public Methods methods;
	public FileManager fileManager;
	public CountdownManager countdownManager;
	
	public static String prefix;
	public String map = "";
	
	public boolean lobbyCD = false, ingameCD = false, restartCD = false, deathmatchCD = false;
	public int lobbyValue = 60, gameValue = 610, restartValue = 15, deathmatchValue = 310;
	
	public ArrayList<String> spectators = new ArrayList<>();
	public HashMap<String, Integer> playerIDs = new HashMap<>();
	public HashMap<String, Integer> playerModules = new HashMap<>();
	public HashMap<String, Integer> playerLives = new HashMap<>();
	public boolean dontMove = false;
	
	@Override
	public void onEnable() {
		instance = this;
		methods = new Methods();
		fileManager = new FileManager();
		countdownManager = new CountdownManager();
		
		getConfig().options().copyDefaults(true);
		getConfig().addDefault("prefix", "&7[&cJumpLeague&7] &r");
		getConfig().addDefault("minPlayers", 2);
		getConfig().addDefault("maxPlayers", 8);
		getConfig().addDefault("deathY", 4);
		List<String> maps = getConfig().getStringList("maps");
		maps.add("Map");
		getConfig().addDefault("maps", maps);
		List<String> lobbyServers = getConfig().getStringList("lobbyServers");
		lobbyServers.add("lobby");
		lobbyServers.add("lobby2");
		getConfig().addDefault("lobbyServers", lobbyServers);
		List<String> silentlobbyServers = getConfig().getStringList("silentlobbyServers");
		silentlobbyServers.add("silentlobby");
		silentlobbyServers.add("silentlobby2");
		getConfig().addDefault("silentlobbyServers", silentlobbyServers);
		getConfig().addDefault("host", "localhost");
		getConfig().addDefault("database", "database");
		getConfig().addDefault("user", "username");
		getConfig().addDefault("password", "password");
		saveConfig();
		
		prefix = ChatColor.translateAlternateColorCodes('&', getConfig().getString("prefix"));
		
		MySQL.connect();
		
		if(MySQL.isConnected()) {
			MySQL.update("CREATE TABLE IF NOT EXISTS JLPlayerStats (UUID varchar(64), Played INT, Wins INT, Kills INT, Deaths INT, Coins INT)");
			MySQL.update("CREATE TABLE IF NOT EXISTS JLAutoSilentLobby (UUID varchar(64))");
		}
		
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getPluginManager().registerEvents(new PlayerJoinJL(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuitJL(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveJL(), this);
		getServer().getPluginManager().registerEvents(new PlayerDenyJL(), this);
		getServer().getPluginManager().registerEvents(new PlayerRepawnJL(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathJL(), this);
		getCommand("jumpleague").setExecutor(new JumpLeague_Command());
		getCommand("start").setExecutor(new Start_Command());
		getCommand("lobby").setExecutor(new Lobby_Command());
		
		GameManager.setState(GameManager.LOBBY);
		
		if(Main.getInstance().getMethods().jumpLeagueIsOk()) {
			getCountdownManager().startLobbyCD();
		}
	}
	
	@Override
	public void onDisable() {
		MySQL.disconnect();
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public Methods getMethods() {
		return methods;
	}
	
	public FileManager getFileManager() {
		return fileManager;
	}
	
	public CountdownManager getCountdownManager() {
		return countdownManager;
	}
	
}
