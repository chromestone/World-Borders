package com.gmail.absolutevanillahelp.WorldBorders;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class PlayerEventListener implements Listener {

	private WorldBorders plugin;

	public PlayerEventListener(WorldBorders instance) {

		plugin = instance;
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerTeleport(PlayerTeleportEvent event) {

		if (event.getCause() == TeleportCause.COMMAND) {

			Location from = event.getFrom();
			event.getPlayer().sendMessage(ChatColor.GRAY + "Teleported from: " + from.getBlockX() + " " + from.getBlockY() + " " + from.getBlockZ());
		}
	}

	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerDeath(PlayerDeathEvent event) {

		Location death = event.getEntity().getLocation();
		event.getEntity().sendMessage(ChatColor.GRAY + "You died at: " + death.getBlockX() + " " + death.getBlockY() + " " + death.getBlockZ());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (plugin.getVerifiedPlayers().containsKey(event.getPlayer().getUniqueId()) && !plugin.getVerifiedPlayers().get(event.getPlayer().getUniqueId())) {

			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLeave(PlayerQuitEvent event) {

		if (plugin.getVerifiedPlayers().containsKey(event.getPlayer().getUniqueId())) {

			plugin.getVerifiedPlayers().put(event.getPlayer().getUniqueId(), false);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageEvent event) {

		if (event.getEntity() instanceof Player) {

			if (plugin.getVerifiedPlayers().containsKey(event.getEntity().getUniqueId()) && !plugin.getVerifiedPlayers().get(event.getEntity().getUniqueId())) {

				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void on(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player) {

			if (plugin.getVerifiedPlayers().containsKey(event.getDamager().getUniqueId()) && !plugin.getVerifiedPlayers().get(event.getDamager().getUniqueId())) {

				event.setCancelled(true);
			}
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {

		if (plugin.getVerifiedPlayers().containsKey(event.getPlayer().getUniqueId()) && !plugin.getVerifiedPlayers().get(event.getPlayer().getUniqueId())) {

			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event) {

		if (plugin.getVerifiedPlayers().containsKey(event.getPlayer().getUniqueId()) && !plugin.getVerifiedPlayers().get(event.getPlayer().getUniqueId())) {

			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerOpenInventory(InventoryOpenEvent event) {

		if (plugin.getVerifiedPlayers().containsKey(event.getPlayer().getUniqueId()) && !plugin.getVerifiedPlayers().get(event.getPlayer().getUniqueId())) {

			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(final PlayerJoinEvent event) {

		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

			@Override
			public void run() {

				event.getPlayer().sendMessage(ChatColor.AQUA + "Remember, greifing is not permitted and only APPROVED X-Ray texture packs are allowed; www.tinyurl.com/lr98pbr.");
			}
		}, 20);
	}
}