package com.gmail.absolutevanillahelp.WorldBorders;

import org.bukkit.World;

public class PlayerOnlineSaver implements Runnable {

	private WorldBorders plugin;

	public PlayerOnlineSaver(WorldBorders instance) {
		plugin = instance;
	}

	@Override
	public void run() {
		if (plugin.getServer().getOnlinePlayers().length > 0) {
			plugin.getServer().broadcastMessage("Saving World...");
			for (World world : plugin.getServer().getWorlds())
				world.save();
		}
	}
}