package com.ilummc.ilsstats.task;

import java.util.Set;

import org.bukkit.Bukkit;
import com.ilummc.ilsstats.UpdateBoard;

public class PlayerAttackTask implements Runnable {
	@SuppressWarnings("deprecation")
	@Override
	public synchronized void run() {
		Set<String> set = UpdateBoard.playeronAttack.keySet();
		for (String pname : set) {
			if ((System.currentTimeMillis() - UpdateBoard.playeronAttack.get(pname)) >= 10000L) {
				UpdateBoard.playeronAttack.remove(pname);
				if (Bukkit.getOfflinePlayer(pname).isOnline())
					UpdateBoard.updateStats(Bukkit.getPlayer(pname), null);
			}
		}
	}
}
