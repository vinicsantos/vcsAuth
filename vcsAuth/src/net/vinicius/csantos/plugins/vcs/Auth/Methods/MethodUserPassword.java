package net.vinicius.csantos.plugins.vcs.Auth.Methods;

import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vinicius.csantos.plugins.vcs.Auth.Main;

public class MethodUserPassword {

	FileConfiguration config = Main.getMain().getConfig();
	Utils utils = new Utils();

	/**
	 * M�todo responsavel pela altera��o da senha do usu�rio
	 * @param player
	 * @param oldPass
	 * @param newPass
	 */
	public void changePassword(Player player, String oldPass, String newPass) {
		MySQLStorage mysql = utils.getMysql();
		String sql = String.format(
				"SELECT `USERID`, `USERUUID`, `REALNAME`, `USERNAME`, `USEREMAIL`, `USERONLINE`, `STATUS` FROM `vcsusers` WHERE 1=1 AND `username` = '%1$s' AND `REALNAME` = '%2$s' AND `USERONLINE` = 1 AND `USERPASSWORD` = md5('%3$s') AND STATUS = 1",
				player.getName().toLowerCase(), player.getName(), oldPass);
		mysql.executeQuery(sql, rs -> {
			try {
				if (rs.next()) {
					int userID = MethodUser.getUserID().get(player);
					String sqlU = String.format(
							"UPDATE `vcsusers` SET `USERPASSWORD` = md5('%1$s') WHERE `USERID` = %2$s;", newPass,
							userID);
					mysql.executeUpdate(sqlU);
					player.sendMessage(config.getString("Auth.Messages.PasswordChanged").replace("&", "�"));
				} else {
					player.sendMessage(config.getString("Auth.Messages.WrongPassword").replace("&", "�"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

}
