package net.vinicius.csantos.plugins.vcs.Auth.Events;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.vinicius.csantos.plugins.vcs.Auth.Main;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUser;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUserJoin;

public class UserJoin implements Listener {

	FileConfiguration config = Main.getMain().getConfig();
	MethodUserJoin userJ = new MethodUserJoin();

	@EventHandler
	public void onUserJoinTheServer(PlayerJoinEvent e) {
		userJ.getUserRegistered(e.getPlayer());
		ArrayList<Player> inLogin = MethodUser.getInLogin();
		HashMap<Player, Location> loginLocation = MethodUser.getLoginLocation();
		inLogin.add(e.getPlayer());
		loginLocation.put(e.getPlayer(), e.getPlayer().getLocation());
		MethodUser.setInLogin(inLogin);
		MethodUser.setLoginLocation(loginLocation);

		new BukkitRunnable() {
			@Override
			public void run() {
				if (MethodUser.getInLogin().contains(e.getPlayer())) {
					e.getPlayer().kickPlayer(config.getString("Auth.Messages.LoginKick").replace("&", "§"));
				}
			}
		}.runTaskLater(Main.getMain(), 20L * (config.getInt("Auth.Delays.LoginRegisterKick")));
		new BukkitRunnable() {
			@Override
			public void run() {
				if (MethodUser.getInLogin().contains(e.getPlayer())) {
					MethodUser.sendMessage(e.getPlayer());
				}
			}
		}.runTaskLater(Main.getMain(), 20L * (config.getInt("Auth.Delays.LoginRegisterMessage")));

	}

}
