package net.vinicius.csantos.plugins.vcs.Auth.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vinicius.csantos.plugins.vcs.Auth.Main;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodAdmin;

public class AdminOpCommands implements CommandExecutor {
	
	FileConfiguration config = Main.getMain().getConfig();
	MethodAdmin admin = new MethodAdmin();

	/**
	 * Método responsavel pelos comandos de Administrador/OP
	 * @param sender
	 * @param cmd
	 * @param arg
	 * @param args
	 */
	 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("UnRegistrar")) {
				if(player.isOp() || player.hasPermission("vcsAuth.unRegisterUser")) {
					if(args.length == 2) {
						Player target = (Player) Bukkit.getServer().getPlayer(args[0]);
						System.out.println(args[0]);
						System.out.println(args[1]);
						System.out.println(target.getName());
							admin.unRegisterUser(player, target, args[1]);
					}else {
						player.sendMessage(config.getString("Auth.Messages.CorrectAdminUnRegister").replace("&", "§"));
					}
				}else {
					player.sendMessage(config.getString("Auth.Messages.DontHavePermission").replace("&", "§"));
				}
			}
		}
		return false;
	}

}
