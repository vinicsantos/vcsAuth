package net.vinicius.csantos.plugins.vcs.Auth.Methods;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vinicius.csantos.plugins.vcs.Auth.Main;

public class MethodUserLeave {
	
	FileConfiguration config = Main.getMain().getConfig();
	Utils util = new Utils();
	
	/**
	 * Método responsavel pela alteração do status de logado do usuario
	 * @param player
	 */
	public void userUpdateQuit(Player player) {
		int idPlayer = MethodUser.getUserID().get(player);
		MySQLStorage mysql = util.getMysql();
		String sql = String.format("UPDATE `vcsusers` SET `USERONLINE` = '0' WHERE `USERID` = %1$s AND STATUS = 1;", idPlayer);
		mysql.executeUpdate(sql);
		MethodUser.getUserID().remove(player);
		MethodUser.getUserIDString().remove(player.getName());
	}

}
