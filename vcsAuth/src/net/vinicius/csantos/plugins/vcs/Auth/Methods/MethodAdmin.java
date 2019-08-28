package net.vinicius.csantos.plugins.vcs.Auth.Methods;

import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vinicius.csantos.plugins.vcs.Auth.Main;

public class MethodAdmin {

	Utils utils = new Utils();
	FileConfiguration config = Main.getMain().getConfig();

	/**
	 * Metodo responsavel por retirar o registro de um usuario por Administrador
	 * 
	 * @param player
	 * @param target
	 * @param pass
	 */
	public void unRegisterUser(Player player, String target, String pass) {
		MySQLStorage mysql = utils.getMysql();
		String sql = String.format(
				"SELECT `USERID`, `USERUUID`, `REALNAME`, `USERNAME`, `USEREMAIL`, `USERONLINE`, `STATUS` FROM `vcsusers` WHERE 1=1 AND `username` = '%1$s' AND `REALNAME` = '%2$s' AND `USERONLINE` = 1 AND `USERPASSWORD` = md5('%3$s') AND STATUS = 1",
				player.getName().toLowerCase(), player.getName(), pass);
		mysql.executeQuery(sql, rs ->{
			try {
				if(rs.next()) {
					String sqlQ = String.format(
							"SELECT `USERID`, `USERUUID`, `REALNAME`, `USERNAME`, `USEREMAIL`, `USERONLINE`, `STATUS` FROM `vcsusers` WHERE 1=1 AND `username` = '%1$s' AND `REALNAME` = '%2$s' AND (`USERONLINE` = 1 OR `USERONLINE` = 0) AND STATUS = 1",
							target.toLowerCase(), target);
					mysql.executeQuery(sqlQ, rsQ ->{
						try {
							if(rsQ.next()) {
								int idTarget = rsQ.getInt("USERID");
								String sqlU = String.format("UPDATE `vcsusers` SET `USERONLINE` = '2', `STATUS` = '9' WHERE`vcsusers`.`USERID` = %1$s", idTarget);
								mysql.executeUpdate(sqlU);
								player.sendMessage(config.getString("Auth.Messages.UserUnregistered").replace("&", "§").replace("%player%", target));
							}else {
								player.sendMessage(config.getString("Auth.Messages.UserNotRegistered").replace("&", "§"));
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					});
				}else {
					player.sendMessage(config.getString("Auth.Messages.WrongPassword").replace("&", "§"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

}
