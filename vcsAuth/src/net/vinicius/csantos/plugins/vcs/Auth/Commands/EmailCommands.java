package net.vinicius.csantos.plugins.vcs.Auth.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vinicius.csantos.plugins.vcs.Auth.Main;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUserEmail;

public class EmailCommands implements CommandExecutor{

	MethodUserEmail userE = new MethodUserEmail();
	FileConfiguration config = Main.getMain().getConfig();
	
	/**
	 * Método responsavel pelo registro e alteração de email do usuario
	 * @param sender
	 * @param cmd
	 * @param arg
	 * @param args
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("registrarEmail")) {
				if(args.length == 2) {
					if(!userE.getEmailAlreadyRegistered(args[0])) {
						userE.registerEmail(player, args[0], args[1]);
					}else {
						player.sendMessage(config.getString("Auth.Messages.EmailAlreadyRegistered").replace("&", "§"));
					}
				}else {
					player.sendMessage(config.getString("Auth.Messages.CorrectUsageEmailRegister").replace("&", "§"));
				}
			}else if (cmd.getName().equalsIgnoreCase("alterarEmail")) {
				if(args.length == 2) {
					if(!userE.getEmailAlreadyRegistered(args[0])) {
						userE.changeEmail(player, args[0], args[1]);
					}else {
						player.sendMessage(config.getString("Auth.Messages.EmailAlreadyRegistered").replace("&", "§"));
					}
				}else {
					player.sendMessage(config.getString("Auth.Messages.CorrectUsageEmailChange").replace("&", "§"));
				}
			}
		}
		return false;
	}

}
