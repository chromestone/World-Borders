package com.gmail.absolutevanillahelp.WorldBorders;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SendItem implements Runnable {

	private WorldBorders plugin;
	private UUID senderId;
	private UUID receiverId;
	private ItemStack item;

	public SendItem(WorldBorders instance, UUID sId, UUID rId, ItemStack i) {

		plugin = instance;
		senderId = sId;
		receiverId = rId;
		item = i;
	}

	@Override
	public void run() {

		Player player = plugin.getServer().getPlayer(receiverId);
		if (player != null) {

			HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(new ItemStack[]{item});
			for (ItemStack item : leftOver.values())
				player.getLocation().getWorld().dropItem(player.getLocation(), item);
		}
		else {

			Player sender = plugin.getServer().getPlayer(senderId);
			if (sender != null) {

				HashMap<Integer, ItemStack> leftOver = sender.getInventory().addItem(new ItemStack[]{item});
				for (ItemStack item : leftOver.values())
					sender.getLocation().getWorld().dropItem(sender.getLocation(), item);
			}
			else {

				plugin.getServer().broadcastMessage(ChatColor.RED + "LOL, BOTH THE SENDER AND RECEIVER WERE OFFLINE, THUS UNABLE TO OBTAIN TRANSFERRED ITEM!");
			}
		}
	}
}
