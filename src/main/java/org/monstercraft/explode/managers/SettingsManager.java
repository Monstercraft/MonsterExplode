package org.monstercraft.explode.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.monstercraft.explode.MonsterExplode;
import org.monstercraft.explode.util.Variables;

/**
 * This class contains all of the plugins settings.
 * 
 * @author fletch_to_99 <fletchto99@hotmail.com>
 * 
 */
public class SettingsManager extends MonsterExplode {
	private MonsterExplode plugin = null;

	/**
	 * Creates an instance of the Settings class.
	 * 
	 * @param plugin
	 *            The parent plugin.
	 */
	public SettingsManager(MonsterExplode plugin) {
		this.plugin = plugin;
		load();
	}

	private void save(final FileConfiguration config, final File file) {
		try {
			config.save(file);
		} catch (IOException e) {
			debug(e);
		}
	}

	/**
	 * This method loads the plugins configuration file.
	 */
	public void load() {
		final FileConfiguration config = this.plugin.getConfig();
		final File CONFIGURATION_FILE = new File(plugin.getDataFolder(),
				"Settings.yml");
		boolean exists = CONFIGURATION_FILE.exists();
		log("Loading settings.yml file");
		if (exists) {
			try {
				log("Loading settings!");
				config.options().copyDefaults(true);
				config.load(CONFIGURATION_FILE);
			} catch (Exception e) {
				debug(e);
			}
		} else {
			log("Loading default settings!");
			config.options().copyDefaults(true);
		}
		try {
			Variables.randomnessFactor = config.getInt("EXPLODE.CHANCE",
					Variables.randomnessFactor);
			Variables.size = config.getInt("EXPLODE.SIZE", Variables.size);
			Variables.prevent_block_damage = config.getBoolean(
					"EXPLODE.PREVENT_BLOCK_DAMAGE",
					Variables.prevent_block_damage);
			save(config, CONFIGURATION_FILE);
		} catch (Exception e) {
			debug(e);
		}
	}
}
