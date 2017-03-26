package com.ilummc.ilsstats.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import com.ilummc.ilsstats.Main;

public class ShootListener implements Listener {
	String lore;

	public ShootListener() {
		lore = Main.cfg.getString("nogravity-lore");
	}

	@EventHandler
	public void onShoot(EntityShootBowEvent evt) {
		if (evt.getBow().getItemMeta().hasLore()) {
			for (String s : evt.getBow().getItemMeta().getLore()) {
				if (s.replaceAll("¡ì[0-9a-zA-z]", "").equals(lore)) {
					evt.getProjectile().setGravity(false);
				}
			}
		}
	}
}
