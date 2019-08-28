package net.vinicius.csantos.plugins.vcs.Auth.Methods;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.vinicius.csantos.plugins.vcs.Auth.Main;

public class Utils{
	
	FileConfiguration config = Main.getMain().getConfig();

	public MySQLStorage getMysql() {
		String ip = config.getString("MySQL.host");
		String user = config.getString("MySQL.user");
		String database = config.getString("MySQL.database");
		String password = config.getString("MySQL.pass");
		int port = config.getInt("MySQL.port");
		JavaPlugin plugin = (JavaPlugin) Bukkit.getServer().getPluginManager().getPlugin("vcsAuth");
		MySQLStorage mysql = new MySQLStorage(plugin, ip, user, password, database, port);
		if (!mysql.isAlive()) {
			mysql.start();
		}
		
		return mysql;
	}
}
