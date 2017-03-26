package com.ilummc.ilsstats;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import com.github.supavitax.itemlorestats.CharacterSheet;
import com.github.supavitax.itemlorestats.GearStats;
import com.github.supavitax.itemlorestats.ItemLoreStats;
import com.github.supavitax.itemlorestats.Enchants.Vanilla_Power;
import com.github.supavitax.itemlorestats.Enchants.Vanilla_Sharpness;
import com.github.supavitax.itemlorestats.Util.Util_Colours;
import com.github.supavitax.itemlorestats.Util.Util_Format;
import com.github.supavitax.itemlorestats.Util.Util_Material;
import me.winterguardian.easyscoreboards.ScoreboardUtil;

public class UpdateBoard {
	public static Map<String, Long> playeronAttack = new HashMap<>();
	public static Map<String, Entity> playerAttackTarget = new HashMap<>();
	public static List<String> blocklist = new ArrayList<>();
	public static Map<String, Map<Long, Double>> dpscounter = new ConcurrentHashMap<>();

	public static void updateStats(final Player p, final Entity e) {
		if (p.hasPermission("ilsstats.show")) {
			if (blocklist.contains(p.getName()))
				return;
			Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ILSStats"),
					new Runnable() {
						@Override
						public void run() {
							try {
								if (!playeronAttack.containsKey(p.getName()))
									update(p);
								else if (e == null) {
									Entity e = playerAttackTarget.get(p.getName());
									updateFight(p, e);
								} else {
									updateFight(p, e);
								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}, 1L);
		} else {
			p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
		}
	}

	public static void update(Player p) {
		try {
			CharacterSheet cs = new CharacterSheet();
			ScoreboardUtil.unrankedSidebarDisplay(p, new String[] { p.getDisplayName(),
					"§3生命 " + calHealth(new Double(p.getHealth()).intValue(),
							new Double(ItemLoreStats.getPlugin().getHealthValue(p)).intValue()),
					"§bDPS §83s§b  " + new DecimalFormat("#.00").format(calDps(p)),
					"§e经验 " + new Integer(new Float(p.getExp() * p.getExpToLevel()).intValue()).toString() + " / "
							+ new Integer(p.getExpToLevel()).toString(),
					calDamage(p).trim(), cs.getArmourValue(p).trim(), cs.getHealthRegenValue(p).trim(),
					cs.getCritChanceValue(p).trim(), cs.getCritDamageValue(p).trim(), cs.getXPMultiplierValue(p).trim(),
					cs.getReflectValue(p).trim(), cs.getDodgeValue(p).trim(), cs.getMovementSpeedValue(p).trim(),
					cs.getLifeStealValue(p).trim() });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateFight(Player p, Entity e) {
		CharacterSheet cs = new CharacterSheet();
		List<String> msg = new ArrayList<>();
		if (e instanceof Player) {
			msg.add("§b== 攻击 §a" + e.getName() + " §b==");
			msg.add("§3[LV " + String.valueOf(((Player) e).getLevel()) + "]");
		} else {
			msg.add("§b== 攻击 §a" + e.getType().name() + " §b==");
		}
		msg.add(getEntityMsg(p, e));
		msg.add("§f§l=============");
		msg.add(p.getDisplayName());
		msg.add("§3生命 " + calHealth(new Double(p.getHealth()).intValue(),
				new Double(ItemLoreStats.getPlugin().getHealthValue(p)).intValue()));
		msg.add("§bDPS §83s§b  " + new DecimalFormat("#.00").format(calDps(p)));
		msg.add("§e经验 " + new Integer(new Float(p.getExp() * p.getExpToLevel()).intValue()).toString() + " / "
				+ new Integer(p.getExpToLevel()).toString());
		msg.add(calDamage(p).trim());
		msg.add(cs.getArmourValue(p).trim());
		ScoreboardUtil.unrankedSidebarDisplay(p, msg.toArray(new String[msg.size()]));
	}

	public static String getEntityMsg(Player p, Entity e) {
		try {
			if (e.isDead()) {
				String msg = "§c击杀 1x §a" + e.getType().name();
				playeronAttack.remove(p.getName());
				// UpdateBoard.blocklist.add(p.getName());
				// Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ILSStats"),
				// new BlockScoreboardTask(p.getName()), 700L);
				return msg;
			} else {
				return getHealthGraph(e);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	public static Double calDps(Player p) {
		if (!dpscounter.containsKey(p.getName())) {
			dpscounter.put(p.getName(), new HashMap<Long, Double>());
		}
		if (dpscounter.get(p.getName()).isEmpty()) {
			return 0D;
		}
		Set<Long> set = dpscounter.get(p.getName()).keySet();
		Long time = 0L;
		for (Long times : set) {
			if (times > time)
				time = times;
		}
		Double dps = 0D;
		for (Long times : set) {
			if (time - times > 3000L) {
				continue;
			}
			dps += dpscounter.get(p.getName()).get(times);
		}
		Set<Long> list = new HashSet<>();
		list.addAll(set);
		for (Long times : list) {
			if (time - times > 3000L) {
				dpscounter.get(p.getName()).remove(times);
			}
		}
		return dps;
	}

	public static String getHealthGraph(Entity e) {
		try {
			String re = "§c§l";
			double health = ((LivingEntity) e).getHealth();
			double maxhealth = ((LivingEntity) e).getMaxHealth();
			for (double h = health / maxhealth; h >= 0; h -= 0.05D) {
				re += ":";
			}
			re += "§f§l";
			for (double h = 1 - health / maxhealth; h >= 0; h -= 0.05D) {
				re += ":";
			}
			return re;
		} catch (Exception ex) {
			return "§f§l::::::::::::::::::::";
		}
	}

	public static String calFight(Player p) {
		return "";
	}

	public static String calHealth(Integer current, Integer total) {
		String re = "";
		if ((double) current / (double) total >= 0.500) {
			re += "§a";
		} else if ((double) current / (double) total <= 0.500 && (double) current / (double) total >= 0.250) {
			re += "§6";
		} else {
			re += "§c";
		}
		re += current.toString() + " §f/ §a" + total.toString();
		return re;
	}

	/**
	 * @author Supavitax
	 * @param player
	 * @return Copy from ItemLoreStats, just for damage counting
	 */
	public static String calDamage(Player player) {
		String damage = ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.colour")
				+ ItemLoreStats.plugin.getConfig().getString("primaryStats.damage.name");
		GearStats gearStats = new GearStats();
		Util_Colours util_Colours = new Util_Colours();
		Util_Material util_Material = new Util_Material();
		Util_Format util_Format = new Util_Format();
		Vanilla_Sharpness vanilla_Sharpness = new Vanilla_Sharpness();
		Vanilla_Power vanilla_Power = new Vanilla_Power();
		double minDamage = 0.0D;
		double maxDamage = 0.0D;

		minDamage = Double.parseDouble(gearStats.getDamageGear(player).split("-")[0]);
		maxDamage = Double.parseDouble(gearStats.getDamageGear(player).split("-")[1]);

		double valueMinMain = 0.0D;
		double valueMaxMain = 0.0D;
		double valueMinOff = 0.0D;
		double valueMaxOff = 0.0D;

		if (ItemLoreStats.plugin.isTool(player.getInventory().getItemInMainHand().getType())) {
			if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
				minDamage += util_Material.materialToDamage(player.getInventory().getItemInMainHand().getType());
				maxDamage += util_Material.materialToDamage(player.getInventory().getItemInMainHand().getType());
			}
			valueMinMain = Double.parseDouble(
					gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(player)).split("-")[0]);
			valueMaxMain = Double.parseDouble(
					gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInMainHand(player)).split("-")[1]);

			minDamage += valueMinMain;
			maxDamage += valueMaxMain;
		}
		if (ItemLoreStats.plugin.isTool(player.getInventory().getItemInOffHand().getType())) {
			if (ItemLoreStats.plugin.getConfig().getBoolean("vanilla.includeDamage")) {
				minDamage += util_Material.materialToDamage(player.getInventory().getItemInOffHand().getType());
				maxDamage += util_Material.materialToDamage(player.getInventory().getItemInOffHand().getType());
			}
			valueMinOff = Double.parseDouble(
					gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(player)).split("-")[0]);
			valueMaxOff = Double.parseDouble(
					gearStats.getDamageItemInHand(ItemLoreStats.plugin.itemInOffHand(player)).split("-")[1]);

			minDamage += valueMinOff;
			maxDamage += valueMaxOff;
		}
		if (vanilla_Sharpness.hasSharpness(player)) {
			int mainLevel = ItemLoreStats.plugin.itemInMainHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL);
			int offLevel = ItemLoreStats.plugin.itemInOffHand(player).getEnchantmentLevel(Enchantment.DAMAGE_ALL);

			minDamage = vanilla_Sharpness.calculateNewDamage(minDamage, mainLevel, offLevel);
			maxDamage = vanilla_Sharpness.calculateNewDamage(maxDamage, mainLevel, offLevel);
		} else if (vanilla_Power.hasPower(player)) {
			int mainLevel = ItemLoreStats.plugin.itemInMainHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
			int offLevel = ItemLoreStats.plugin.itemInOffHand(player).getEnchantmentLevel(Enchantment.ARROW_DAMAGE);

			minDamage = vanilla_Sharpness.calculateNewDamage(minDamage, mainLevel, offLevel);
			maxDamage = vanilla_Sharpness.calculateNewDamage(maxDamage, mainLevel, offLevel);
		}
		return util_Colours.replaceTooltipColour(damage) + ": " + ChatColor.LIGHT_PURPLE + util_Format.format(minDamage)
				+ util_Colours.replaceTooltipColour(util_Colours.extractTooltipColour(damage)) + " - "
				+ ChatColor.LIGHT_PURPLE + util_Format.format(maxDamage);
	}
}
