package net.vinicius.csantos.plugins.vcs.Auth.Archives;


import org.bukkit.configuration.file.FileConfiguration;

import net.vinicius.csantos.plugins.vcs.Auth.Main;

public class Configs{

	FileConfiguration config = Main.getMain().getConfig();
	
	/**
	 * Método responsavel por definir as configurações do arquivo de configuração do plugin
	 */
	public Configs() {
		config.addDefault("Auth.Messages.Register", "&7Não encontramos seu registro no servidor.\nPor favor se registre usando &8/registrar <Senha> <ConfirmacaoSenha> <Email(Opcional)>");
		config.addDefault("Auth.Messages.ErrorOnRegister", "&7Houve um erro ao tentar realizar seu registro\nPor favor contacte um administrador pelo nosso discord\nDiscord: discord.gg/DHGYFG");
		config.addDefault("Auth.Messages.ErrorOnLogin", "&7Houve um erro ao tentar realizar seu login\nPor favor contacte um administrador pelo nosso discord\nDiscord: discord.gg/DHGYFG");
		config.addDefault("Auth.Messages.ErrorOnChangePassword", "&7Houve um erro ao tentar alterar sua senha\nPor favor contacte um administrador.");
		config.addDefault("Auth.Messages.Login", "&7Encontramos seu registro no servidor.\nPor favor logue usando &8/logar <Senha>");
		config.addDefault("Auth.Messages.PasswordDoesntMatch", "&7As senhas inseridas não batem.");
		config.addDefault("Auth.Messages.HaveRegistered", "&7Voce foi registrado com sucesso!");
		config.addDefault("Auth.Messages.PasswordPreRequisites", "&7Senha Incorreta! Sua senha deve conter no minimo &85&7 caracteres e no maximo &815&7 caracteres.");
		config.addDefault("Auth.Messages.LoginKick", "&cVoce foi kickado \n&7Voce passou do limite de espera para realizar o login.");
		config.addDefault("Auth.Messages.RegisterKick", "&cVoce foi kickado \n&7Voce passou do limite de espera para realizar o registro.");
		config.addDefault("Auth.Messages.Logged", "&7Voce foi logado com sucesso!");
		config.addDefault("Auth.Messages.WrongPassword", "&7Senha incorreta");
		config.addDefault("Auth.Messages.NotRegistered", "&7Não encontramos seu registro no servidor.\nPor favor se registre usando &8/registrar <Senha> <ConfirmacaoSenha> <Email(Opcional)>");
		config.addDefault("Auth.Messages.Registered", "&7Encontramos seu registro no servidor.\nPor favor logue usando &8/logar <Senha>");
		config.addDefault("Auth.Messages.InvalidEmail", "&7Email inválido!(example@example.example)");
		config.addDefault("Auth.Messages.AccountNotFound", "&7Não encontramos esta conta online.");
		config.addDefault("Auth.Messages.EmailAlreadHaveRegistered", "&7A sua conta ja tem um email registrado!\nPara trocar use o comando \"/alterarEmail\".");
		config.addDefault("Auth.Messages.EmailRegistered", "&7Email registrado com sucesso! \"%email%\".");
		config.addDefault("Auth.Messages.CorrectUsageEmailRegister", "&7Comando correto: \"/registrarEmail <Email> <Senha>\".");
		config.addDefault("Auth.Messages.EmailNotRegistered", "&7A sua não tem nenhuma email registrado!\nPara registrar use o comando \"/registrarEmail\".");
		config.addDefault("Auth.Messages.CorrectUsageEmailChange", "&7Comando correto: \"/alterarEmail <novoEmail> <Senha>\".");
		config.addDefault("Auth.Messages.EmailAlreadyRegistered", "&7Notamos que este email ja esta cadastrado em nossa base dados!\nPara alterar use o comando \"/alterarEmail\"");
		config.addDefault("Auth.Messages.EmailChanged", "&7Email alterado com sucesso!\nEmail antigo: \"%oldEmail%\"\nEmail novo: \"%newEmail%\"");
		config.addDefault("Auth.Messages.CorrectUsagePasswordChange", "&7Comando correto: \"/alterarSenha <senhaAtual> <novaSenha> <confirmNovaSenha>\".");
		config.addDefault("Auth.Messages.SamePasswordChange", "&7A nova senha tem que ser diferente da sua antiga senha.");
		config.addDefault("Auth.Messages.PasswordChanged", "&7Senha alterada com sucesso!");
		config.addDefault("Auth.Messages.CorrectAdminUnRegister", "&7Comando correto \"/UnRegistrar <Player> <suaSenha>");
		config.addDefault("Auth.Messages.UserNotFound", "&7Usuario não encontrado.");
		config.addDefault("Auth.Messages.UserNotRegistered", "&7Usuario não registrado.");
		config.addDefault("Auth.Messages.DontHavePermission", "&7Voce não tem permissao para executar este comando.");
		config.addDefault("Auth.Messages.ErrorOnEmailRegister", "&7Houve um erro e não foi possivel reigstrar um email\nContate um administrador para resolver o seu problema.");
		config.addDefault("Auth.Messages.ErrorOnEmailChange", "&7Houve um erro e não foi possivel alterar o email\nContate um administrador para resolver o seu problema.");
		config.addDefault("Auth.Messages.UserUnregistered", "&7Registro do usuario \"%player%\" removido com sucesso.");
		config.addDefault("Auth.Messages.ErrorOnUserUnregister", "&7Houve um erro ao remover o registro do usuario \"%player%\".");
		config.addDefault("Auth.Delays.LoginRegisterKick", 60);
		config.addDefault("Auth.Delays.LoginRegisterMessage", 30);
		Main.getMain().saveConfig();
		System.out.println("vcsAuth - Configurações carregadas.");
	}

}
