package org.monstercraft.explode.managers;

import java.util.LinkedList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.monstercraft.explode.command.GameCommand;
import org.monstercraft.explode.command.commands.Toggle;

/**
 * This class manages all of the plugins commands.
 *
 * @author fletch_to_99 <fletchto99@hotmail.com>
 *
 */
public class CommandManager {

    private final LinkedList<GameCommand> gameCommands = new LinkedList<GameCommand>();

    public CommandManager() {
        try {
            gameCommands.add(new Toggle());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes a command that was ran in game or through the console.
     *
     * @param sender
     *            The command sender.
     * @param command
     *            The command.
     * @param label
     *            The commands label.
     * @param args
     *            The arguments in the command.
     * @return True if the command executed successfully; Otherwise false.
     */
    public boolean onGameCommand(final CommandSender sender,
            final Command command, final String label, final String[] args) {
        final String[] split = new String[args.length + 1];
        split[0] = label;
        for (int i = 0; i < args.length; i++) {
            split[i + 1] = args[i];
        }
        for (final GameCommand c : gameCommands) {
            if (c.canExecute(sender, split)) {
                try {
                    c.execute(sender, split);
                } catch (final Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return true;
    }
}