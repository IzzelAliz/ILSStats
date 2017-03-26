package com.ilummc.ilsstats.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent evt) {
		Integer combatLevel = evt.getPlayer().getLevel();
		if (combatLevel != null) {
			evt.setFormat("¡ì6[¡ìeLV. ¡ìa" + combatLevel + "¡ì6]" + ChatColor.RESET + evt.getFormat());
		}
	}
}
