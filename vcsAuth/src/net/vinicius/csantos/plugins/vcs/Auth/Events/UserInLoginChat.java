package net.vinicius.csantos.plugins.vcs.Auth.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUser;

public class UserInLoginChat implements Listener {
	
	/**
	 * Metodo responsavel por realizar a verificação se o player esta logado ou nao no servidor
	 * @param e Evento que esta sendo utilizado (AsyncPlayerChatEvent)
	 */
	@EventHandler
	public void onUserInLoginChat(AsyncPlayerChatEvent e) {
		if(MethodUser.getInLogin().contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

}
