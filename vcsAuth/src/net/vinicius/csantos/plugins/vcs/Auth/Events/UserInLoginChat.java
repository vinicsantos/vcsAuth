package net.vinicius.csantos.plugins.vcs.Auth.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUser;

public class UserInLoginChat implements Listener {
	
	@EventHandler
	public void onUserInLoginChat(AsyncPlayerChatEvent e) {
		if(MethodUser.getInLogin().contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

}
