package com.gmail.absolutevanillahelp.WorldBorders;

import java.util.UUID;

import org.bukkit.*;
import org.bukkit.entity.Player;

public class KillPlayer implements Runnable {

	private WorldBorders plugin;

	private UUID playerId;

	public KillPlayer(WorldBorders instance, UUID id) {

		plugin = instance;
		playerId = id;

	}

	@Override
	public void run() {

		Player player = plugin.getServer().getPlayer(playerId);
		if (player != null) {

			if (!player.isDead()) {
				
				Location loc = player.getLocation();
				if (loc.getWorld().getEnvironment() == World.Environment.NORMAL) {

					Location spawn = loc.getWorld().getSpawnLocation();
					if (loc.getX() > spawn.getX() + 1000 || loc.getX() < spawn.getX() - 1000 || loc.getZ() > spawn.getZ() + 1000 || loc.getZ() < spawn.getZ() - 1000) {

						player.setHealth(0);
					}
				}
			}
		}
		else {
			OfflinePlayer offPlayer = plugin.getServer().getOfflinePlayer(playerId);
			if (offPlayer != null) {
				offPlayer.setWhitelisted(false);
			}
		}

		plugin.getPlayerChecker().getMarkedPlayers().remove(playerId);
	}

}
