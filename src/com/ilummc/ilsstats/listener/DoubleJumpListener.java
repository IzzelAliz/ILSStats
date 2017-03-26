package com.ilummc.ilsstats.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;

import com.ilummc.ilsstats.Main;

public class DoubleJumpListener implements Listener {
	Map<Integer, Integer> counter = new ConcurrentHashMap<>();
	Map<Integer, Integer> jump = new ConcurrentHashMap<>();
	boolean useoffhand = Main.instance.getConfig().getBoolean("use-offhand");

	@EventHandler
	public void onJoin(PlayerJoinEvent evt) {
		evt.getPlayer().setInvulnerable(false);
	}

	@EventHandler
	public void onPlayerToggleFlight(PlayerToggleFlightEvent evt) {
		Player p = evt.getPlayer();
		if (!jump.containsKey(p.getEntityId())) {
			jump.put(p.getEntityId(), 0);
		}
		if (p.getGameMode().equals(GameMode.CREATIVE)) {
			return;
		}
		evt.setCancelled(true);
		jump.replace(p.getEntityId(), jump.get(p.getEntityId()) + 1);
		if (jump.get(p.getEntityId()) >= counter.get(p.getEntityId()))
			p.setAllowFlight(false);
		p.setFlying(false);
		p.setVelocity(p.getLocation().getDirection().multiply(p.getWalkSpeed()).setY(0.5));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent evt) {
		Player p = evt.getPlayer();
		if (!counter.containsKey(evt.getPlayer().getEntityId())) {
			counter.put(evt.getPlayer().getEntityId(), Main.cfg.getInt("default-multijump"));
		}
		if (p.getGameMode() != GameMode.CREATIVE && p.isOnGround()) {
			if (counter.get(p.getEntityId()) != 0)
				p.setAllowFlight(true);
			else
				p.setAllowFlight(false);
			jump.replace(p.getEntityId(), 0);
		}
	}

	@EventHandler
	public void onSwap(PlayerItemHeldEvent evt) {
		if (!counter.containsKey(evt.getPlayer().getEntityId())) {
			counter.put(evt.getPlayer().getEntityId(), Main.cfg.getInt("default-multijump"));
		}
		Player p = evt.getPlayer();
		this.updateDoubleJump(p);
	}

	@EventHandler
	public void onClick(InventoryClickEvent evt) {
		if (!(evt.getWhoClicked() instanceof Player)) {
			return;
		}
		Player p = (Player) evt.getWhoClicked();
		if (!counter.containsKey(p.getEntityId())) {
			counter.put(p.getEntityId(), Main.cfg.getInt("default-multijump"));
		}
		this.updateDoubleJump(p);
	}

	public void updateDoubleJump(Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ILSStats"),
				new UpdateJumpTask(p), 1L);

	}

	class UpdateJumpTask implements Runnable {
		Player p;

		@Override
		public void run() {
			int jump = 0;
			if (p.getEquipment().getArmorContents().length != 0)
				for (ItemStack item : p.getEquipment().getArmorContents()) {
					if (item == null || item.getType() == Material.AIR)
						continue;
					for (String s : item.getItemMeta().getLore()) {
						if (s.replaceAll("¡ì[0-9a-zA-Z]", "")
								.matches(Main.cfg.getString("multijump-lore") + ": [0-9]*")) {
							jump += Integer.parseInt(s.replaceAll("¡ì[0-9a-zA-Z]", "")
									.replaceAll(Main.cfg.getString("multijump-lore") + ": ", ""));
						}
					}
				}
			if (p.getEquipment().getItemInMainHand() != null
					&& p.getEquipment().getItemInMainHand().getType() != Material.AIR
					&& p.getEquipment().getItemInMainHand().getItemMeta().hasLore())
				for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
					if (s.replaceAll("¡ì[0-9a-zA-Z]", "").matches(Main.cfg.getString("multijump-lore") + ": [0-9]*")) {
						jump += Integer.parseInt(s.replaceAll("¡ì[0-9a-zA-Z]", "")
								.replaceAll(Main.cfg.getString("multijump-lore") + ": ", ""));
					}
				}
			if (useoffhand)
				if (p.getEquipment().getItemInOffHand() != null
						&& p.getEquipment().getItemInOffHand().getType() != Material.AIR
						&& p.getEquipment().getItemInOffHand().getItemMeta().hasLore())
					for (String s : p.getEquipment().getItemInOffHand().getItemMeta().getLore()) {
						if (s.replaceAll("¡ì[0-9a-zA-Z]", "")
								.matches(Main.cfg.getString("multijump-lore") + ": [0-9]*")) {
							jump += Integer.parseInt(s.replaceAll("¡ì[0-9a-zA-Z]", "")
									.replaceAll(Main.cfg.getString("multijump-lore") + ": ", ""));
						}
					}
			counter.replace(p.getEntityId(), jump);
		}

		public UpdateJumpTask(Player p) {
			this.p = p;
		}
	}
}
