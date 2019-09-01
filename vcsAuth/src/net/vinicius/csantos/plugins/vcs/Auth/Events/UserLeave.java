package net.vinicius.csantos.plugins.vcs.Auth.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.vinicius.csantos.plugins.vcs.Auth.Main;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUser;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUserLeave;

public class UserLeave implements Listener {
	
	FileConfiguration config = Main.getMain().getConfig();
	MethodUserLeave userL = new MethodUserLeave();
	
	/**
	 * Método Responsavel por fazer a verificação de quando o player sair do servidor.
	 * @param e Evento que esta sendo utilizado (PlayerQuitEvent)
	 */
	@EventHandler
	public void onUserLeave(PlayerQuitEvent e) {
		if(!(MethodUser.getInLogin().contains(e.getPlayer()))) {
			userL.userUpdateQuit(e.getPlayer());
		}
	}

}
