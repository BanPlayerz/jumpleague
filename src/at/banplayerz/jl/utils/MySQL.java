package at.banplayerz.jl.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.bukkit.Bukkit;

import at.banplayerz.jl.Main;

public class MySQL {

	public static Connection con;
	
	public static void connect() {
		if(!isConnected()) {
			String host = Main.getInstance().getConfig().getString("host");
			String database = Main.getInstance().getConfig().getString("database");
			String user = Main.getInstance().getConfig().getString("user");
			String password = Main.getInstance().getConfig().getString("password");
			
			try {
				con = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database, user, password);
				Bukkit.getConsoleSender().sendMessage(Main.prefix+"§aDie Verbindung zur Datenbank wurde erfolgreich hergestellt!");
			} catch (SQLException e) {
				Bukkit.getConsoleSender().sendMessage(Main.prefix+"§cEs konnte keine Verbindung zur Datenbank hergestellt werden!");
				Bukkit.getPluginManager().disablePlugin(Main.getInstance());
			}
		}
	}
	
	public static void disconnect() {
		if(isConnected()) {
			try {
				con.close();
				Bukkit.getConsoleSender().sendMessage(Main.prefix+"§cDie Verbindung zur Datenbank wurde getrennt!");
			} catch (SQLException e) {
			}
		}
	}
	
	public static boolean isConnected() {
		return con != null;
	}
	
	public static boolean update(String qry) {
		if(isConnected()) {
			try {
				con.createStatement().executeUpdate(qry);
				return true;
			} catch (SQLException e) {
			}
		}
		return false;
	}
	
	public static ResultSet getResult(String qry) {
		if(isConnected()) {
			try {
				return con.createStatement().executeQuery(qry);
			} catch (SQLException e) {
			}
		}
		return null;
	}
	
}
