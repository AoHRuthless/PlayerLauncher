/*
 * PlayerLauncher Plugin built for Minecraft, Bukkit Servers
 * Copyright (C) 2013 Anand Kumar <http://dev.bukkit.org/bukkit-plugins/playerlauncher/>
 * 
 * This file, CommandHandler.java, is part of the plugin PlayerLauncher.
 * 
 * PlayerLauncher is a free software: You can redistribute it or modify it
 * under the terms of the GNU General Public License published by the Free
 * Software Foundation, either version 3 of the license of any later version.
 * 
 * PlayerLauncher is distributed in the intent of being useful. However, there
 * is NO WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You can view a copy of the GNU General Public License at 
 * <http://www.gnu.org/licenses/> if you have not received a copy.
 */
package io.GitHub.AoHRuthless.command;

import io.GitHub.AoHRuthless.PlayerLauncher;
import io.GitHub.AoHRuthless.framework.Launch;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor 
{
	private PlayerLauncher plugin;
	private Launch launch;
	private Map<String, CommandInterface> commands;
	
	public CommandHandler(PlayerLauncher plugin) {
		this.plugin = plugin;
		this.launch = plugin.getLaunchHandler();
		this.commands = new HashMap<String, CommandInterface>();
	}
	
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
		if(Commands.isEnabled(launch)) {
			if(Commands.isPlayer(sender)) {
				if(args.length == 0 || args[0].equalsIgnoreCase("self")) {
					getExecutor("launch").execute(sender, cmd, launch, args);
					return true;
				}
				if(args.length > 0) {
					if(exists(args[0])){
						getExecutor(args[0]).execute(sender, cmd, launch, args);
						return true;
					} else {
						sender.sendMessage(PlayerLauncher.PREFIX + PlayerLauncher.INVALIDARGS);
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

	protected PlayerLauncher getPlugin() {
		return plugin;
	}
	
	protected Launch getLauncher() {
		return launch;
	}
}
