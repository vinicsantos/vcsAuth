package net.vinicius.csantos.plugins.vcs.Auth.Events;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.vinicius.csantos.plugins.vcs.Auth.Main;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUser;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUserJoin;

public class UserInLoginCommand implements Listener {

	FileConfiguration config = Main.getMain().getConfig();
	MethodUserJoin userJ = new MethodUserJoin();
	
	/**
	 * Método Responsavel por verificar os comandos digitados pelo usuario e chamada do metodo de autenticação/registro do usuário
	 * @param e
	 */
	
	@EventHandler
	public void onUserInLoginCommand(PlayerCommandPreprocessEvent e) {
		if (MethodUser.getInLogin().contains(e.getPlayer())) {
			if ((e.getMessage().startsWith("/registrar")) || (e.getMessage().startsWith("/logar"))) {
				String[] args = e.getMessage().split(" ");
				if ((args.length > 2 && args.length < 5) && e.getMessage().startsWith("/registrar") && args[0].equalsIgnoreCase("/registrar")) {
					if (args[1].equals(args[2])) {
						if (args[1].length() > 4 && args[1].length() < 16) {
							if (args.length == 4) {
								if(MethodUser.isRealEmail(args[3])) {
									userJ.playerRegister(e.getPlayer(), args[1], args[3]);	
								}else {
								e.getPlayer().sendMessage(config.getString("Auth.Messages.InvalidEmail").replace("&", "§"));
								}
							} else {
								userJ.playerRegister(e.getPlayer(), args[1], "");
							}
						} else {
							e.getPlayer().sendMessage(
									config.getString("Auth.Messages.PasswordPreRequisites").replace("&", "§"));
						}
					} else {
						e.getPlayer()
								.sendMessage(config.getString("Auth.Messages.PasswordDoesntMatch").replace("&", "§"));
					}
				} else if (args.length == 2 && e.getMessage().startsWith("/logar")  && args[0].equalsIgnoreCase("/logar")) {
					userJ.playerLogIn(e.getPlayer(), args[1]);
				} else {
					MethodUser.sendMessage(e.getPlayer());
				}
			}
			e.setCancelled(true);
			System.out.println(e.getMessage());
		}
	}

}
