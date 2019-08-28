package net.vinicius.csantos.plugins.vcs.Auth.Methods;

import java.sql.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import org.bukkit.plugin.java.JavaPlugin;

public class MySQLStorage extends Thread {

	private BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<>(200, true);
	private JavaPlugin plugin;
	private String ip, user, password, database;
	private int port;
	

	public MySQLStorage(JavaPlugin plugin, String ip, String user, String password, String database, int port) {
			//super("MySQL Thread Connector");
			this.plugin = plugin;
			this.ip = ip;
			this.user = user;
			this.password = password;
			this.database = database;
			this.port = port;		
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (Exception e) {
				plugin.getLogger().severe("Erro ao inicializar o Driver MySQL");
				e.printStackTrace();
			}
		}

	public void executeQuery(String cmd) {
		executeQuery(cmd, null, null);
	}

	public void executeQuery(String cmd, Consumer<ResultSet> result) {
		executeQuery(cmd, null, result);
	}

	public void executeQuery(String cmd, Object[] params, Consumer<ResultSet> result) {
		if (cmd == null)
			throw new IllegalArgumentException("O comando sql não pode ser nulo");

		tasks.add(() -> {
			Connection cn = null;
			PreparedStatement ps = null;
			try {
				cn = getConnection();
				ps = cn.prepareStatement(cmd);
				ResultSet r = ps.executeQuery();
				// Caso o retorno não seja necessario, entao não agenda tarefa no server
				// thread.
				if (result != null) {
					// Como isso é executado em um segundo thread, isso vai ser executado e
					// aguardado enquanto é executado
					plugin.getServer().getScheduler().callSyncMethod(plugin, () -> {
						result.accept(r);
						return null;
					}).get();
				}
			} catch (Exception e) {
				plugin.getServer().getLogger().severe("Erro ao tentar executar comando SQL.");
				plugin.getServer().getLogger().severe("Comando: " + cmd);
				e.printStackTrace();
			} finally {
				if (cn != null) {
					try {
						cn.close();
					} catch (SQLException e) {
						plugin.getServer().getLogger().severe("Erro ao fechar uma conexao SQL");
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	
	public void executeUpdate(String cmd) {
		if (cmd == null)
			throw new IllegalArgumentException("O comando sql não pode ser nulo");

		tasks.add(() -> {
			Connection c = null;
			PreparedStatement p = null;
			try {
				c = getConnection();
				p = c.prepareStatement(cmd);
				p.executeUpdate();
				
				// Caso o retorno não seja necessario, entao não agenda tarefa no server
				// thread.
			} catch (Exception e) {
				plugin.getServer().getLogger().severe("Erro ao tentar executar comando SQL.");
				plugin.getServer().getLogger().severe("Comando: " + cmd);
				e.printStackTrace();
			} finally {
				if (c != null) {
					try {
						c.close();
					} catch (SQLException e) {
						plugin.getServer().getLogger().severe("Erro ao fechar uma conexao SQL");
						e.printStackTrace();
					}
				}
			}
		});
	}

	public Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://" + ip + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=utf-8",
					user, password);
		} catch (Exception e) {
			plugin.getLogger().severe("Erro ao criar uma conexão com MySQL");
			e.printStackTrace();
		}
		return null;
	}	

	@Override
	public void run() {
		while (true) {
			try {
				Runnable r = tasks.take();
				if (r != null)
					r.run();
			} catch (InterruptedException e) {
				plugin.getLogger().severe("Erro ao executar a tarefa.");
				e.printStackTrace();
			}
		}
	}
}
