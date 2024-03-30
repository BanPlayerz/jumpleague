package at.banplayerz.jl.utils;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import at.banplayerz.jl.Main;

public class FileManager {

	public File getLocationFile() {
		return new File("plugins/"+Main.getInstance().getDescription().getName(), "locations.yml");
	}
	
	public FileConfiguration getLocationFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getLocationFile());
	}
	
}
