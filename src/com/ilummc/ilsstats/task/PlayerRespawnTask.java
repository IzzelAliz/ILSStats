package com.ilummc.ilsstats.task;

import org.bukkit.entity.Player;

public class PlayerRespawnTask implements Runnable {
	Player p;

	public PlayerRespawnTask(Player p) {
		this.p = p;
	}

	@Override
	public void run() {
		p.setInvulnerable(false);
		p.chat("/spawn");
	}

}
