package com.gmail.absolutevanillahelp.WorldBorders;

import org.bukkit.World;

public class PlayerOfflineSaver implements Runnable {

	private WorldBorders plugin;

	public PlayerOfflineSaver(WorldBorders instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		if (plugin.getServer().getOnlinePlayers().length == 0) {
			plugin.getServer().getLogger().info("Saving World...");
			for (World world : plugin.getServer().getWorlds())
				world.save();
		}
	}
}
