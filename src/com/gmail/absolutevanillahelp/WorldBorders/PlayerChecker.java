package com.gmail.absolutevanillahelp.WorldBorders;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.Player;

public class PlayerChecker extends TimerTask {

	private WorldBorders plugin;
	private ArrayList<UUID> markedPlayers;

	public PlayerChecker(WorldBorders instance) {

		plugin = instance;
		markedPlayers = new ArrayList<UUID>();
	}

	public ArrayList<UUID> getMarkedPlayers() {

		return markedPlayers;
	}

	@Override
	public void run() {

		for (Player player : plugin.getServer().getOnlinePlayers()) {

			if (!player.isDead()) {

				Location loc = player.getLocation();
				if (loc.getWorld().getEnvironment() == World.Environment.NORMAL) {

					Location spawn = loc.getWorld().getSpawnLocation();
					if (loc.getX() > spawn.getX() + 995 || loc.getX() < spawn.getX() - 995 || loc.getZ() > spawn.getZ() + 995 || loc.getZ() < spawn.getZ() - 995) {

						if (loc.getX() > spawn.getX() + 1000 || loc.getX() < spawn.getX() - 1000 || loc.getZ() > spawn.getZ() + 1000 || loc.getZ() < spawn.getZ() - 1000) {

							if (!plugin.getPlayerChecker().getMarkedPlayers().contains(player.getUniqueId())) {
								player.sendMessage(ChatColor.RED + "You will die in 5 seconds unless you return to legal area!");
								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new KillPlayer(plugin, player.getUniqueId()), 5*20);
								plugin.getPlayerChecker().getMarkedPlayers().add(player.getUniqueId());
							}
						}
						else {
							player.sendMessage(ChatColor.RED + "You are five blocks from border!");
						}
					}
				}
			}

		}
	}
}
