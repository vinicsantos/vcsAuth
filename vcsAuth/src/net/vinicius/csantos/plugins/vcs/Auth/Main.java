package net.vinicius.csantos.plugins.vcs.Auth;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.plugin.java.JavaPlugin;

import net.vinicius.csantos.plugins.vcs.Auth.Archives.Configs;
import net.vinicius.csantos.plugins.vcs.Auth.Commands.AdminOpCommands;
import net.vinicius.csantos.plugins.vcs.Auth.Commands.EmailCommands;
import net.vinicius.csantos.plugins.vcs.Auth.Commands.PasswordCommands;
import net.vinicius.csantos.plugins.vcs.Auth.Events.UserInLoginChat;
import net.vinicius.csantos.plugins.vcs.Auth.Events.UserInLoginCommand;
import net.vinicius.csantos.plugins.vcs.Auth.Events.UserInLoginMove;
import net.vinicius.csantos.plugins.vcs.Auth.Events.UserJoin;
import net.vinicius.csantos.plugins.vcs.Auth.Events.UserLeave;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MySQLStorage;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.Tables;

public class Main extends JavaPlugin {

	private static Main plugin;
	FileConfiguration config = getConfig();
	FileConfiguration tableConfig = null;
	File tableFile = null;

	public void reloadTableConfig() throws UnsupportedEncodingException {
		if (tableFile == null) {
			tableFile = new File(getDataFolder(), "tables.yml");
		}
		tableConfig = YamlConfiguration.loadConfiguration(tableFile);

		Reader defTableConfigStream = new InputStreamReader(this.getResource("tables.yml"), "UTF8");
		if (defTableConfigStream != null) {
			YamlConfiguration defTableConfig = YamlConfiguration.loadConfiguration(defTableConfigStream);
			tableConfig.setDefaults(defTableConfig);
		}
	}

	public FileConfiguration getTableConfig() throws UnsupportedEncodingException {
		if (tableConfig == null) {
			this.reloadTableConfig();
		}
		return tableConfig;
	}

	public void saveTableConfig() {
		if (tableConfig == null || tableFile == null) {
			return;
		}
		try {
			this.getTableConfig().save(tableFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveDefaultTableConfig() {
		if (tableFile == null) {
			tableFile = new File(getDataFolder(), "tables.yml");
		}
		if (!tableFile.exists()) {
			plugin.saveResource("tables.yml", false);
		}
	}

	public void Config() throws UnsupportedEncodingException {
		this.saveDefaultConfig();
		this.saveDefaultTableConfig();
		tableConfig = this.getTableConfig();
		config.addDefault("MySQL.user", "root");
		config.addDefault("MySQL.pass", "");
		config.addDefault("MySQL.host", "localhost");
		config.addDefault("MySQL.port", 3306);
		config.addDefault("MySQL.database", "servidorm");
		ArrayList<String> tables = new ArrayList<>();
		Tables quer = new Tables();
		tables = quer.setTablesCreation();
		tableConfig.addDefault("Tables.Auth", tables);
		config.options().copyDefaults(true);
		tableConfig.options().copyDefaults(true);
		saveTableConfig();
		saveConfig();
	}

	@Override
	public void onEnable() {
		plugin = this;
		try {
			Config();
			new Configs();
			tableConfig = this.getTableConfig();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
		
		List<String> list = tableConfig.getStringList("Tables.Auth");
		for (int i = 0; i < list.size(); i++) {
			mysql.executeUpdate(list.get(i));
		}
		mysql.executeUpdate("UPDATE `vcsusers` SET `USERONLINE` = '0'");
		
		Bukkit.getServer().getPluginManager().registerEvents(new UserJoin(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new UserInLoginMove(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new UserInLoginChat(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new UserInLoginCommand(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new UserLeave(), this);
		getCommand("registrarEmail").setExecutor(new EmailCommands());
		getCommand("alterarEmail").setExecutor(new EmailCommands());
		getCommand("alterarSenha").setExecutor(new PasswordCommands());
		getCommand("UnRegistrar").setExecutor(new AdminOpCommands());

	}

	@Override
	public void onDisable() {
		plugin = null;
	}
	
	
	public static Main getMain() {
		return plugin;
	}

}
