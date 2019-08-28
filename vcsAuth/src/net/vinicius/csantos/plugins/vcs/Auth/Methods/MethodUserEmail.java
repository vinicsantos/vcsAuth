package net.vinicius.csantos.plugins.vcs.Auth.Methods;

import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vinicius.csantos.plugins.vcs.Auth.Main;

public class MethodUserEmail {

	FileConfiguration config = Main.getMain().getConfig();
	Utils utils = new Utils();
	boolean response = false;
	
	/**
	 * Método responsavel pelo registro de um novo email do usuario
	 * @param player
	 * @param email
	 * @param password
	 */
	public void registerEmail(Player player, String email, String password) {
		MySQLStorage mysql = utils.getMysql();
		String sql = String.format(
				"SELECT `USERID`, `USERUUID`, `REALNAME`, `USERNAME`, `USEREMAIL`, `USERONLINE`, `STATUS` FROM `vcsusers` WHERE 1=1 AND `username` = '%1$s' AND `REALNAME` = '%2$s' AND `USERONLINE` = 1 AND STATUS = 1",
				player.getName().toLowerCase(), player.getName());
		mysql.executeQuery(sql, rs -> {
			try {
				if (rs.next()) {
					if (rs.getString("USEREMAIL") == null) {
						if (MethodUser.isRealEmail(email)) {
							int userID = MethodUser.getUserID().get(player);
							String sqlU = String.format(
									"UPDATE `vcsusers` SET `USEREMAIL` = '%1$s' WHERE `USERID` = %2$s AND `USERPASSWORD` = md5('%3$s');",
									email, userID, password);
							mysql.executeUpdate(sqlU);
							player.sendMessage(config.getString("Auth.Messages.EmailRegistered").replace("&", "§")
									.replace("%email%", email));
						} else {
							player.sendMessage(config.getString("Auth.Messages.InvalidEmail").replace("&", "§"));
						}

					} else {
						player.sendMessage(
								config.getString("Auth.Messages.EmailAlreadHaveRegistered").replace("&", "§"));
					}
				} else {
					player.sendMessage(config.getString("Auth.Messages.AccountNotFound").replace("&", "§"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Método responsavel pela verificação de email ja existente no banco de dados
	 * @param email
	 * @return
	 */
	public Boolean getEmailAlreadyRegistered(String email) {
		MySQLStorage mysql = utils.getMysql();
		String sql = String.format("SELECT `USERID`, `USERUUID`, `REALNAME`, `USERNAME`, `USEREMAIL`, `USERONLINE`, `STATUS` FROM `vcsusers` WHERE 1=1 AND `USEREMAIL` = '%1$s' AND STATUS = 1", email);
		mysql.executeQuery(sql, rs ->{
			try {
				if(rs.next()) {
					response = true;
				}else {
					response = false;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return response;
	}

	/**
	 * Método responsavel pela alteração do email do usuario
	 * @param player
	 * @param email
	 * @param password
	 */
	public void changeEmail(Player player, String email, String password) {
		MySQLStorage mysql = utils.getMysql();
		String sql = String.format(
				"SELECT `USERID`, `USERUUID`, `REALNAME`, `USERNAME`, `USEREMAIL`, `USERONLINE`, `STATUS` FROM `vcsusers` WHERE 1=1 AND `username` = '%1$s' AND `REALNAME` = '%2$s' AND `USERONLINE` = 1 AND STATUS = 1",
				player.getName().toLowerCase(), player.getName());
		mysql.executeQuery(sql, rs -> {
			try {
				if (rs.next()) {
					if (rs.getString("USEREMAIL") != null) {
						if (MethodUser.isRealEmail(email)) {
							String oldEmail = rs.getString("USEREMAIL");
							int userID = MethodUser.getUserID().get(player);
							String sqlU = String.format(
									"UPDATE `vcsusers` SET `USEREMAIL` = '%1$s' WHERE `USERID` = %2$s AND `USERPASSWORD` = md5('%3$s');",
									email, userID, password);
							mysql.executeUpdate(sqlU);
							player.sendMessage(config.getString("Auth.Messages.EmailChanged").replace("&", "§")
									.replace("%oldEmail%", oldEmail).replace("%newEmail%", email));
						} else {
							player.sendMessage(config.getString("Auth.Messages.InvalidEmail").replace("&", "§"));
						}
					} else {
						player.sendMessage(config.getString("Auth.Messages.EmailNotRegistered").replace("&", "§"));
					}
				} else {
					player.sendMessage(config.getString("Auth.Messages.AccountNotFound").replace("&", "§"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

}
