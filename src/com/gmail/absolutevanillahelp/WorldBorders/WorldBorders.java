package com.gmail.absolutevanillahelp.WorldBorders;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldBorders extends JavaPlugin{

	private PlayerChecker playerChecker;
	private HashMap<UUID, Boolean> verifiedPlayers;
	private WBConfiguration dataConfig;
	
	public WorldBorders() {
		
		playerChecker = new PlayerChecker(this);
		verifiedPlayers = new HashMap<UUID, Boolean>();
	}

	@Override
	public void onEnable() {

		getServer().getWorlds().get(0).setKeepSpawnInMemory(false);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, playerChecker, 60*20, 20);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerOfflineSaver(this), 60*60*20, 2*60*60*20);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new PlayerOnlineSaver(this), 5*60*20, 10*60*20);
		
		dataConfig = new WBConfiguration(this, "config.yml");
		if (dataConfig.getConfig().isSet("Verify")) {
			for (String name : dataConfig.getConfig().getConfigurationSection("Verify").getKeys(false)) {
				verifiedPlayers.put(UUID.fromString(name), false);
			}
		}
		
		getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
	}

	public PlayerChecker getPlayerChecker() {

		return playerChecker;
	}
	
	public HashMap<UUID, Boolean> getVerifiedPlayers() {
		
		return verifiedPlayers;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label , String[] args) {

		if (cmd.getName().equals("verifyc") && args.length > 0) {
			if (verifiedPlayers.containsKey(UUID.fromString(args[0]))) {
				if (verifiedPlayers.get(UUID.fromString(args[0]))) {
					sender.sendMessage(ChatColor.GREEN + args[0] + "'s identity is valid.");
				}
				else {
					sender.sendMessage(ChatColor.RED + args[0] + "'s identity hasn't been confirmed!");
				}
			}
			else {
				sender.sendMessage(ChatColor.RED + args[0] + " hasn't been listed to be verified!");
			}
			return true;
		}
		else if (sender instanceof Player) {

			Player player = (Player) sender;
			if (cmd.getName().equals("spawn")) {

				player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
				return true;
			}
			else if (cmd.getName().equals("unlockc") && args.length > 0) {

				UUID playerId = player.getUniqueId();
				if (verifiedPlayers.containsKey(playerId)) {
					if (!verifiedPlayers.get(playerId)) {
						if (args[0].equals(dataConfig.getConfig().getString("Verify." + playerId))) {
							verifiedPlayers.put(playerId, true);
							player.sendRawMessage(ChatColor.GREEN + "Your account has been successfully unlocked, have a nice day.");
						}
						else {
							player.sendRawMessage(ChatColor.RED + "Password not recognized, try again.");
						}
					}
					else {
						player.sendRawMessage(ChatColor.GREEN + "Your account is already unlocked.");
					}
				}
				else {
					player.sendRawMessage(ChatColor.GREEN + "Your account hasn't been listed to be verified, no worries! Have a nice day.");
				}
				return true;
			}
			else if (cmd.getName().equals("senditem") && args.length > 0) {

				Player receiver = getServer().getPlayer(args[0]);
				if (receiver != null) {

					ItemStack item = player.getItemInHand();
					if (item.getType() != Material.AIR) {

						player.setItemInHand(null);
						int distance = (int) (player.getLocation().distance(receiver.getLocation()) * 20);
						getServer().getScheduler().runTaskLater(this, new SendItem(this, player.getUniqueId(),receiver.getUniqueId(), item), distance);
						player.sendMessage(ChatColor.GREEN + args[0] + " will get your " + item.getType().toString() + " in " +  distance + " seconds!" + ChatColor.RED + " (If receiver leaves, whatever you do, do not leave or else...)");
						receiver.sendMessage(ChatColor.GOLD + player.getName() + " sent you a " + item.getType() + " and it will arive in " + distance + " seconds!" + ChatColor.RED + " (Whatever you do, do not leave or else...)");
					}
					else {

						player.sendMessage(ChatColor.RED + "You are not holding anything.");
					}
				}
				else {

					player.sendMessage(ChatColor.RED + args[0] + " is not currently online.");
				}
				return true;
			}
		}
		else if (cmd.getName().equals("glist")) {
			
			getLogger().info("");
			return true;
		}
		return false;
	}
	
	@Override
	public void onDisable() {
		
		playerChecker.getMarkedPlayers().clear();
		verifiedPlayers.clear();
	}
}
