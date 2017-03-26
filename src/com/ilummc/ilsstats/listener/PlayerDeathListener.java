package com.ilummc.ilsstats.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import com.ilummc.ilsstats.task.PlayerRespawnTask;

public class PlayerDeathListener implements Listener {
	public static Map<String, Location> loc = new HashMap<>();

	@EventHandler
	public void onDeath(PlayerDeathEvent evt) {
		if (!evt.getEntity().hasMetadata("NPC")) {
			loc.put(evt.getEntity().getName(), evt.getEntity().getLocation());
			evt.getEntity().spigot().respawn();
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onRespawn(final PlayerRespawnEvent evt) {
		evt.setRespawnLocation(loc.get(evt.getPlayer().getName()));
		loc.remove(evt.getPlayer().getName());
		if (evt.getPlayer().getKiller() == null || evt.getPlayer().getKiller() == evt.getPlayer()) {
			evt.getPlayer().sendTitle("", "°Ïbƒ„À¿Õˆ¡À!!!");
		} else {
			evt.getPlayer().sendTitle("", "°Ïcƒ„±ª °Ïb" + evt.getPlayer().getKiller().getName() + " °Ïc…±À¿¡À!!!");
		}
		evt.getPlayer().setInvulnerable(true);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ILSStats"),
				new PlayerRespawnTask(evt.getPlayer()), 120L);
	}
}
