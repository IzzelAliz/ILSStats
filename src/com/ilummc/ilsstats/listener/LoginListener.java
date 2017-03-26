package com.ilummc.ilsstats.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ilummc.ilsstats.UpdateBoard;

public class LoginListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onAuthmeLogin(PlayerJoinEvent evt){
		UpdateBoard.updateStats(evt.getPlayer(),null);
	}
}
