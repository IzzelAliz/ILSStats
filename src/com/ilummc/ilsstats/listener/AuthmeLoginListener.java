package com.ilummc.ilsstats.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.ilummc.ilsstats.UpdateBoard;

import fr.xephi.authme.events.LoginEvent;

public class AuthmeLoginListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
	public void onAuthmeLogin(LoginEvent evt) {
		UpdateBoard.updateStats(evt.getPlayer(), null);
	}
}
