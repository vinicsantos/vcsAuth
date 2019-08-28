package net.vinicius.csantos.plugins.vcs.Auth.Methods;

import java.sql.SQLException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vinicius.csantos.plugins.vcs.Auth.Main;

public class MethodUserJoin {
	
	
	FileConfiguration config = Main.getMain().getConfig();
	Utils util = new Utils();
	
	/**
	 * Método responsavel para a verificação de registro do usuario
	 * @param player
	 */
	public void getUserRegistered(Player player) {
		MySQLStorage mysql = util.getMysql();
		String sql = String.format("SELECT `USERID`, `USERUUID`, `REALNAME`, `USERNAME`, `USEREMAIL`, `USERONLINE`, `STATUS` FROM `vcsusers` WHERE 1=1 AND `username` = '%1$s' AND `REALNAME` = '%2$s' AND STATUS = 1", player.getName().toLowerCase(), player.getName());
		mysql.executeQuery(sql, rs ->{
			try {
				if(!rs.next()) {
					player.sendMessage(config.getString("Auth.Messages.Register").replace("&", "§"));
					MethodUser.getRegistered().put(player, false);
				}else {
					player.sendMessage(config.getString("Auth.Messages.Login").replace("&", "§"));
					MethodUser.getRegistered().put(player, true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}
	
	/**
	 * Método responsavel pelo registro do usuario
	 * @param player
	 * @param password
	 * @param email
	 */
	public void playerRegister(Player player, String password, String email) {
		if(!MethodUser.getRegistered().get(player)) {
			MySQLStorage mysql = util.getMysql();
			String uuid = player.getUniqueId().toString();
			String username = player.getName().toLowerCase();
			String realname = player.getName();
			String pass = password;
			String emailF = email != "" ? "'" + email + "'" : "NULL";
			String userRegisterIP = player.getAddress().getHostString();
			String userLastIP = player.getAddress().getHostString();
			String userLoc = "'-'";
			String sql = String.format("INSERT INTO `vcsusers` (`USERUUID`, `USERNAME`, `REALNAME`, `USERPASSWORD`, `USEREMAIL`, `USERREGISTERIP`, `USERLASTIP`, `USERLASTLOGIN`, `USERDTREGISTER`, `USERONLINE`, `USERLOCATION`, `STATUS`) VALUES "
					+ "('%1$s', '%2$s', '%3$s', md5('%4$s'), %5$s, '%6$s', '%7$s', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', %8$s, '1')", uuid, username, realname, pass, emailF, userRegisterIP, userLastIP, userLoc);
			mysql.executeUpdate(sql);
			player.sendMessage(config.getString("Auth.Messages.HaveRegistered").replace("&", "§"));
			MethodUser.getInLogin().remove(player);
			MethodUser.getLoginLocation().remove(player);
			MethodUser.getRegistered().remove(player);
			MethodUser.getRegistered().put(player, true);
			sql = String.format("SELECT `USERID`, `USERUUID`, `REALNAME`, `USERNAME`, `USEREMAIL`, `USERONLINE`, `STATUS` FROM `vcsusers` WHERE 1=1 AND `username` = '%1$s' AND `REALNAME` = '%2$s' AND STATUS = 1", player.getName().toLowerCase(), player.getName());
			mysql.executeQuery(sql, rs ->{
				try {
					if(rs.next()) {
						MethodUser.getUserID().put(player, rs.getInt("USERID"));
						MethodUser.getUserIDString().put(player.getName(), rs.getInt("USERID"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}else {
			player.sendMessage(config.getString("Auth.Messages.Registered").replace("&", "§"));
		}
	}
	
	/**
	 * Método responsavel pela autenticação do usuario
	 * @param player
	 * @param password
	 */
	public void playerLogIn(Player player, String password) {
		if(MethodUser.getRegistered().get(player)) {
			MySQLStorage mysql = util.getMysql();
			String sql = String.format("SELECT `USERID`, `USERUUID`, `REALNAME`, `USERNAME`, `USEREMAIL`, `USERONLINE`, `STATUS` FROM `vcsusers` WHERE 1=1 AND `username` = '%1$s' AND `REALNAME` = '%2$s' AND `USERPASSWORD` = md5('%3$s') AND STATUS = 1", player.getName().toLowerCase(), player.getName(), password);
			mysql.executeQuery(sql, rs ->{
				try {
					if(!rs.next()) {
						player.sendMessage(config.getString("Auth.Messages.WrongPassword").replace("&", "§"));
					}else {
						player.sendMessage(config.getString("Auth.Messages.Logged").replace("&", "§"));
						String userLastIP = player.getAddress().getHostString();
						String sqlU = String.format("UPDATE `vcsusers` SET `USERONLINE` = '1', `USERLASTLOGIN` = CURRENT_TIMESTAMP, `USERLASTIP` = '%1$s' WHERE `USERID` = %2$s AND STATUS = 1", userLastIP, rs.getInt("USERID"));
						mysql.executeUpdate(sqlU);
						MethodUser.getUserID().put(player, rs.getInt("USERID"));
						MethodUser.getUserIDString().put(player.getName(), rs.getInt("USERID"));
						MethodUser.getInLogin().remove(player);
						MethodUser.getLoginLocation().remove(player);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}else {
			player.sendMessage(config.getString("Auth.Messages.NotRegistered").replace("&", "§"));
		}
	}
	
}
