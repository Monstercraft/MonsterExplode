package org.monstercraft.explode;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.monstercraft.explode.listeners.MonsterExplodeListener;
import org.monstercraft.explode.managers.CommandManager;
import org.monstercraft.explode.managers.SettingsManager;
import org.monstercraft.explode.util.Metrics;

public class MonsterExplode extends JavaPlugin {

	private MonsterExplodeListener listener;
	private static Logger logger = Logger.getLogger("Minecraft");
	private CommandManager commandManager;
	private static SettingsManager settings;

	public void onEnable() {
		settings = new SettingsManager(this);
		this.commandManager = new CommandManager();
		listener = new MonsterExplodeListener(this);
		getServer().getPluginManager().registerEvents(listener, this);
		try {
			new Metrics(this).start();
		} catch (final IOException e) {
			debug(e);
		}
		log("MonsterExplode has been enabled!");
	}

	public void onDisable() {
		log("MonsterExplode has been disabled.");
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		return commandManager.onGameCommand(sender, command, label, args);
	}

	/**
	 * Logs a message to the console.
	 * 
	 * @param msg
	 *            The message to print.
	 */
	public static void log(String msg) {
		logger.log(Level.INFO, "[MonsterExplode] " + msg);
	}

	/**
	 * Logs debugging messages to the console.
	 * 
	 * @param error
	 *            The message to print.
	 */
	protected static void debug(Exception error) {
		logger.log(Level.WARNING, "[MonsterExplode - Critical error detected!]");
		error.printStackTrace();
	}

	public SettingsManager getSettingsManager() {
		return settings;
	}
}