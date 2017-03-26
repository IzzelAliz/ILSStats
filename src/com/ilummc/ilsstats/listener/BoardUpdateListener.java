package com.ilummc.ilsstats.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.ilummc.ilsstats.UpdateBoard;

public class BoardUpdateListener implements Listener {
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onRegenHealth(EntityRegainHealthEvent evt) {
		if (evt.getEntity() instanceof Player) {
			UpdateBoard.updateStats((Player) evt.getEntity(), null);
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onExpChange(PlayerExpChangeEvent evt) {
		if (evt.getPlayer().hasMetadata("NPC")) {
			return;
		}
		UpdateBoard.updateStats(evt.getPlayer(), null);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void enchantTableUse(EnchantItemEvent evt) {
		UpdateBoard.updateStats(evt.getEnchanter(), null);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onPlayerHeldItemChange(PlayerItemHeldEvent evt) {
		UpdateBoard.updateStats(evt.getPlayer(), null);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onDropItemEvent(PlayerDropItemEvent evt) {
		UpdateBoard.updateStats(evt.getPlayer(), null);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onPickupCustomItem(PlayerPickupItemEvent evt) {
		UpdateBoard.updateStats(evt.getPlayer(), null);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onInventoryDrag(InventoryDragEvent evt) {
		if (evt.getWhoClicked() instanceof Player)
			UpdateBoard.updateStats((Player) evt.getWhoClicked(), null);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onInventoryClick(InventoryClickEvent evt) {
		if (evt.getWhoClicked() instanceof Player)
			UpdateBoard.updateStats((Player) evt.getWhoClicked(), null);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onPlayerChangeWorld(PlayerChangedWorldEvent evt) {
		UpdateBoard.updateStats(evt.getPlayer(), null);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onPlayerDamage(EntityDamageEvent evt) {
		if (evt.getEntity() instanceof Player && !evt.getEntity().hasMetadata("NPC"))
			UpdateBoard.updateStats((Player) evt.getEntity(), null);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onPlayerDamage(PlayerRespawnEvent evt) {
		UpdateBoard.updateStats(evt.getPlayer(), null);
	}

	/*
	 * @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	 * public void onPlayerInteract(PlayerInteractEvent evt) {
	 * UpdateBoard.updateStats(evt.getPlayer(), null); }
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	public void onAttack(EntityDamageByEntityEvent evt) {
		if (evt.getDamager() instanceof Player) {
			UpdateBoard.playeronAttack.put(((Player) evt.getDamager()).getName(), System.currentTimeMillis());
			UpdateBoard.playerAttackTarget.put(((Player) evt.getDamager()).getName(), evt.getEntity());
			UpdateBoard.updateStats((Player) evt.getDamager(), evt.getEntity());
		}
	}

	/*
	 * @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
	 * public void onEntityDeath(EntityDeathEvent evt) { if
	 * (evt.getEntity().getKiller() instanceof Player) {
	 * UpdateBoard.playeronAttack.remove(evt.getEntity().getKiller().getName());
	 * UpdateBoard.playerAttackTarget.remove(evt.getEntity().getKiller().getName
	 * ()); UpdateBoard.updateStats(evt.getEntity().getKiller(), null); } }
	 */
}
