package io.GitHub.AoHRuthless.command;

import io.GitHub.AoHRuthless.PlayerLauncher;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor 
{
	
	private static HashMap<String, CommandInterface> commands = new HashMap<String, CommandInterface>();
	
	public void register(String name, CommandInterface cmd) {
		commands.put(name, cmd);
	}

	public boolean exists(String name) {
		return commands.containsKey(name);
	}

	public CommandInterface getExecutor(String name) {
		return commands.get(name);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		if(Commands.isEnabled() == true) {
			if(Commands.isPlayer(sender)) {
				if(args.length == 0 || args[0].equalsIgnoreCase("self")) {
					getExecutor("launch").onCommand(sender, cmd, commandLabel, args);
					return true;
				}
				if(args.length > 0) {
					if(exists(args[0])){
						getExecutor(args[0]).onCommand(sender, cmd, commandLabel, args);
						return true;
					} else {
						sender.sendMessage(PlayerLauncher.prefix + PlayerLauncher.invalidargs);
						return true;
					}
				}
			} else {
				sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
				return true;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Sorry, this plugin is not enabled.");
			return true;
		}
		return false;
	}

}