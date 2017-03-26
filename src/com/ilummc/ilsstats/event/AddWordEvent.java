package com.ilummc.ilsstats.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddWordEvent extends Event {
	public static final HandlerList HANDLERS = new HandlerList();
	public String word;
	public CommandSender p;

	public AddWordEvent(String word, CommandSender p) {
		this.word = word;
		this.p = p;
	}

	public String getWord() {
		return this.word;
	}

	public CommandSender getPlayer() {
		return this.p;
	}

	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
