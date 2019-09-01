package net.vinicius.csantos.plugins.vcs.Auth.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.vinicius.csantos.plugins.vcs.Auth.Main;
import net.vinicius.csantos.plugins.vcs.Auth.Methods.MethodUserPassword;

public class PasswordCommands implements CommandExecutor {

	FileConfiguration config = Main.getMain().getConfig();
	MethodUserPassword userP = new MethodUserPassword();

	/**
	 * Método responsavel pelo comando de alteração de senha
	 * @param sender A entidade que esta executando o comando
	 * @param cmd O comando que esta sendo executado
	 * @param arg Argumento
	 * @param args Lista de argumentos usados no comando
	 */
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("alterarSenha")) {
				if (args.length == 3) {
					if (args[1].equals(args[2])) {
						if (args[1].length() > 4 && args[1].length() < 16) {
							if(!(args[0].equals(args[1]))) {
								userP.changePassword(player, args[0], args[1]);
							}else {
								player.sendMessage(config.getString("Auth.Messages.SamePasswordChange").replace("&", "§"));
							}
						} else {
							player.sendMessage(
									config.getString("Auth.Messages.PasswordPreRequisites").replace("&", "§"));
						}
					} else {
						player.sendMessage(config.getString("Auth.Messages.PasswordDoesntMatch").replace("&", "§"));
					}
				} else {
					player.sendMessage(config.getString("Auth.Messages.CorrectUsagePasswordChange").replace("&", "§"));
				}
			}
		}
		return false;
	}

}
