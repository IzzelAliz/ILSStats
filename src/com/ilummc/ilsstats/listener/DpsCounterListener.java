package com.ilummc.ilsstats.listener;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.ilummc.ilsstats.UpdateBoard;

public class DpsCounterListener implements Listener {
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent evt) {
		if (evt.getDamager() instanceof Player) {
			Player damager = (Player) evt.getDamager();
			if (!damager.hasMetadata("NPC")) {
				if (UpdateBoard.dpscounter.containsKey(damager.getName())) {
					UpdateBoard.dpscounter.get(damager.getName()).put(System.currentTimeMillis(), evt.getDamage());
				} else {
					UpdateBoard.dpscounter.put(damager.getName(), new HashMap<Long, Double>());
					UpdateBoard.dpscounter.get(damager.getName()).put(System.currentTimeMillis(), evt.getDamage());
				}
			}
		}
	}
}
