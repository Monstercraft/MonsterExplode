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

    /**
     * Logs debugging messages to the console.
     *
     * @param error
     *            The message to print.
     */
    protected static void debug(final Exception error) {
        MonsterExplode.logger.log(Level.WARNING,
                "[MonsterExplode - Critical error detected!]");
        error.printStackTrace();
    }

    /**
     * Logs a message to the console.
     *
     * @param msg
     *            The message to print.
     */
    public static void log(final String msg) {
        MonsterExplode.logger.log(Level.INFO, "[MonsterExplode] " + msg);
    }

    private MonsterExplodeListener listener;
    private static Logger logger = Logger.getLogger("Minecraft");

    private CommandManager commandManager;

    private static SettingsManager settings;

    public SettingsManager getSettingsManager() {
        return MonsterExplode.settings;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command,
            final String label, final String[] args) {
        return commandManager.onGameCommand(sender, command, label, args);
    }

    @Override
    public void onDisable() {
        MonsterExplode.log("MonsterExplode has been disabled.");
    }

    @Override
    public void onEnable() {
        MonsterExplode.settings = new SettingsManager(this);
        commandManager = new CommandManager();
        listener = new MonsterExplodeListener(this);
        this.getServer().getPluginManager().registerEvents(listener, this);
        try {
            new Metrics(this).start();
        } catch (final IOException e) {
            MonsterExplode.debug(e);
        }
        MonsterExplode.log("MonsterExplode has been enabled!");
    }
}