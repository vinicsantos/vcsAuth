package net.vinicius.csantos.plugins.vcs.Auth.Commands;

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
	 * @param sender A entidade que esta executando o comando
	 * @param cmd O comando que esta sendo executado
	 * @param arg Argumento
	 * @param args Lista de argumentos usados no comando
	 */
	 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("UnRegistrar")) {
				if(player.isOp() || player.hasPermission("vcsAuth.unRegisterUser")) {
					if(args.length == 2) {
						String target = args[0];
						System.out.println(args[0]);
						System.out.println(args[1]);
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
