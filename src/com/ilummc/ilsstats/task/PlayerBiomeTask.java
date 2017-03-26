package com.ilummc.ilsstats.task;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import com.ilummc.ilsstats.BiomeText;

public class PlayerBiomeTask implements Runnable {
	public static Map<String, Biome> biomemap = new HashMap<>();

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!p.hasPermission("ilsstats.biome"))
				continue;
			if (!biomemap.containsKey(p.getName())) {
				biomemap.put(p.getName(), p.getLocation().getBlock().getBiome());
				continue;
			}
			if (biomemap.get(p.getName()) == p.getLocation().getBlock().getBiome())
				continue;
			else {
				if (!BiomeText.getBiomeName(p.getLocation().getBlock().getBiome()).equalsIgnoreCase("null"))
					p.sendTitle("", "¡ì8[" + BiomeText.getBiomeName(p.getLocation().getBlock().getBiome()) + "¡ì8] ¡ìe"
							+ BiomeText.getBiomeMsg(p.getLocation().getBlock().getBiome()));
				biomemap.replace(p.getName(), p.getLocation().getBlock().getBiome());
			}
		}
	}

}
