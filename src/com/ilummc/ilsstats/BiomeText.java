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
			return "��b����";
		case "PLAINS":
			return "��aƽԭ";
		case "DESERT":
			return "��eɳĮ";
		case "EXTREME_HILLS":
			return "��2����";
		case "FOREST":
			return "��aɭ��";
		case "TAIGA":
			return "��2��Ҷ��";
		case "SWAMPLAND":
			return "��2����";
		case "RIVER":
			return "��b����";
		case "HELL":
			return "��4����";
		case "SKY":
			return "��dĩ·֮��";
		case "FROZEN_OCEAN":
			return "��3����֮��";
		case "FROZEN_RIVER":
			return "��3����֮��";
		case "ICE_FLATS":
			return "��9��ԭ";
		case "ICE_MOUNTAINS":
			return "��9��ɽ";
		case "MUSHROOM_ISLAND":
			return "��6Ģ����";
		case "BEACHES":
			return "��eɳ̲";
		case "SMALLER_EXTREME_HILLS":
			return "��2��ɽ";
		case "JUNGLE":
			return "��a����";
		case "DEEP_OCEAN":
			return "��9����";
		case "STONE_BEACH":
			return "��8ʯ̲";
		case "BIRCH_FOREST":
			return "��7������";
		case "ROOFED_FOREST":
			return "��2��ɭ��";
		case "SAVANNA":
			return "��6�ȴ���ԭ";
		case "MESA":
			return "��8ƽ��ɽ";
		case "VOID":
			return "��8���";
		default:
			return "null";
		}
	}
}
