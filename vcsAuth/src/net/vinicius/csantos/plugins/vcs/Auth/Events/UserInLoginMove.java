package net.vinicius.csantos.plugins.vcs.Auth.Events;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.vinicius.csantos.plugins.vcs.Auth.Main;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUser;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUserJoin;

public class UserInLoginMove implements Listener {

	FileConfiguration config = Main.getMain().getConfig();
	MethodUserJoin userJ = new MethodUserJoin();

	/**
	 * Método Responsavel por não permitir que o usuario mova pelo mundo
	 * @param e Evento que esta sendo utilizado (PlayerMoveEvent)
	 */
	
	@EventHandler
	public void onPlayerInLoginMove(PlayerMoveEvent e) {
		if (MethodUser.getInLogin().contains(e.getPlayer())) {
			if (e.getFrom().getX() != e.getTo().getX() || e.getFrom().getY() != e.getTo().getY()
					|| e.getFrom().getZ() != e.getTo().getZ()) {
				Location loc = e.getFrom();
				e.getPlayer().teleport(MethodUser.getLoginLocation().get(e.getPlayer()));
                e.getPlayer().teleport(loc.setDirection(e.getTo().getDirection()));
			}
		}
	}

}
