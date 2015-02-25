package org.monstercraft.explode.command.commands;

import org.bukkit.command.CommandSender;
import org.monstercraft.explode.command.GameCommand;
import org.monstercraft.explode.util.Variables;

public class Toggle extends GameCommand {

    @Override
    public boolean canExecute(final CommandSender sender, final String[] split) {
        return sender.isOp() && split[1].equalsIgnoreCase("toggle");
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] split) {
        if (sender.hasPermission("monsterexplode.toggle")) {
            if (Variables.enabled) {
                Variables.enabled = false;
                sender.sendMessage("Successfully disabled.");
            } else {
                Variables.enabled = true;
                sender.sendMessage("Successfully enabled.");
            }
        } else {
            sender.sendMessage("You don't have permission.");
        }
        return true;
    }

}
