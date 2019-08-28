package net.vinicius.csantos.plugins.vcs.Auth.Methods;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vinicius.csantos.plugins.vcs.Auth.Main;

public class MethodUser {
	
	private static ArrayList<Player> inLogin = new ArrayList<>();
	private static HashMap<Player, Location> loginLocation = new HashMap<>();
	private static HashMap<Player, Integer> userID = new HashMap<>();
	private static HashMap<Player, Boolean> registered = new HashMap<>();
	static FileConfiguration config = Main.getMain().getConfig();
	
	
	public static void sendMessage(Player player){
		if(MethodUser.getRegistered().get(player)) {
			player.sendMessage(config.getString("Auth.Messages.Login").replace("&", "§"));
		}else {
			player.sendMessage(config.getString("Auth.Messages.Register").replace("&", "§"));
		}
	}
	
	public static Boolean isRealEmail(String email) {
		boolean isTrue = false;
		if(email.contains("@") && email.split("@")[1].contains(".")) {
			isTrue = true;
		}
		return isTrue;
	}
	
	public static ArrayList<Player> getInLogin() {
		return inLogin;
	}
	public static void setInLogin(ArrayList<Player> inLogin) {
		MethodUser.inLogin = inLogin;
	}
	public static HashMap<Player, Location> getLoginLocation() {
		return loginLocation;
	}
	public static void setLoginLocation(HashMap<Player, Location> loginLocation) {
		MethodUser.loginLocation = loginLocation;
	}
	public static HashMap<Player, Boolean> getRegistered() {
		return registered;
	}
	public static void setRegistered(HashMap<Player, Boolean> registered) {
		MethodUser.registered = registered;
	}

	public static HashMap<Player, Integer> getUserID() {
		return userID;
	}

	public static void setUserID(HashMap<Player, Integer> userID) {
		MethodUser.userID = userID;
	}
}
