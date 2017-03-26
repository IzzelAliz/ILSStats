package com.ilummc.ilsstats;

import java.util.List;
import java.util.Random;

import org.bukkit.block.Biome;

public class BiomeText {
	public static String getBiomeMsg(Biome b) {
		List<String> biomemsg = Main.biome.getStringList(b.name());
		Random d = new Random();
		return biomemsg.get(d.nextInt(biomemsg.size()));
	}

	public static String getBiomeName(Biome b) {
		String biome = b.name();
		switch (biome) {
		case "OCEAN":
			return "§b海洋";
		case "PLAINS":
			return "§a平原";
		case "DESERT":
			return "§e沙漠";
		case "EXTREME_HILLS":
			return "§2极峰";
		case "FOREST":
			return "§a森林";
		case "TAIGA":
			return "§2针叶林";
		case "SWAMPLAND":
			return "§2沼泽";
		case "RIVER":
			return "§b河流";
		case "HELL":
			return "§4地狱";
		case "SKY":
			return "§d末路之地";
		case "FROZEN_OCEAN":
			return "§3冰冻之海";
		case "FROZEN_RIVER":
			return "§3冰冻之河";
		case "ICE_FLATS":
			return "§9冰原";
		case "ICE_MOUNTAINS":
			return "§9冰山";
		case "MUSHROOM_ISLAND":
			return "§6蘑菇岛";
		case "BEACHES":
			return "§e沙滩";
		case "SMALLER_EXTREME_HILLS":
			return "§2高山";
		case "JUNGLE":
			return "§a雨林";
		case "DEEP_OCEAN":
			return "§9大洋";
		case "STONE_BEACH":
			return "§8石滩";
		case "BIRCH_FOREST":
			return "§7白桦林";
		case "ROOFED_FOREST":
			return "§2黑森林";
		case "SAVANNA":
			return "§6热带草原";
		case "MESA":
			return "§8平顶山";
		case "VOID":
			return "§8虚空";
		default:
			return "null";
		}
	}
}
