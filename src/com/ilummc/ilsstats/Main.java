package com.ilummc.ilsstats;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.ilummc.ilsstats.event.AddWordEvent;
import com.ilummc.ilsstats.listener.AuthmeLoginListener;
import com.ilummc.ilsstats.listener.BoardUpdateListener;
import com.ilummc.ilsstats.listener.ChatListener;
import com.ilummc.ilsstats.listener.DoubleJumpListener;
import com.ilummc.ilsstats.listener.DpsCounterListener;
import com.ilummc.ilsstats.listener.LoginListener;
import com.ilummc.ilsstats.listener.PlayerDeathListener;
import com.ilummc.ilsstats.listener.SensitiveListener;
import com.ilummc.ilsstats.listener.ShootListener;
import com.ilummc.ilsstats.task.PlayerAttackTask;
import com.ilummc.ilsstats.task.PlayerBiomeTask;

public class Main extends JavaPlugin {
	public static FileConfiguration biome;
	public static FileConfiguration cfg;
	public static Main instance;

	@Override
	public void onEnable() {
		instance = this;
		this.initConfig();
		if (this.getServer().getPluginManager().getPlugin("AuthMe") != null) {
			this.getServer().getPluginManager().registerEvents(new AuthmeLoginListener(), this);
		} else {
			this.getServer().getPluginManager().registerEvents(new LoginListener(), this);
		}
		this.getServer().getPluginManager().registerEvents(new BoardUpdateListener(), this);
		this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
		this.getServer().getPluginManager().registerEvents(new DpsCounterListener(), this);
		this.getServer().getPluginManager().registerEvents(new SensitiveListener(), this);
		if (this.getConfig().getBoolean("use-nogravity"))
			this.getServer().getPluginManager().registerEvents(new ShootListener(), this);
		if (this.getConfig().getBoolean("use-multijump"))
			this.getServer().getPluginManager().registerEvents(new DoubleJumpListener(), this);
		this.getCommand("ilsstats").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
				if (arg3.length == 1 && arg3[0].equalsIgnoreCase("reload")) {
					Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("ItemLoreStats"),
							new Runnable() {
								@Override
								public void run() {
									Main instance = Main.instance;
									Bukkit.getPluginManager().disablePlugin(instance);
									Bukkit.getPluginManager().enablePlugin(instance);
								}
							}, 1L);
					arg0.sendMessage("¡ì3[ILSStats] Reloaded !");
					return true;
				}
				if (arg3.length == 2 && arg3[0].equalsIgnoreCase("add")) {
					Bukkit.getPluginManager().callEvent(new AddWordEvent(arg3[1], arg0));
					return true;
				}
				return true;
			}
		});
		Bukkit.getConsoleSender().sendMessage("¡ì3[ILSStats] ¡ìbRunning on Minecraft " + Bukkit.getBukkitVersion());
		Bukkit.getConsoleSender().sendMessage("¡ì3[ILSStats] Enabled!");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PlayerAttackTask(), 100L, 20L);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new PlayerBiomeTask(), 100L, 20L);
	}

	@Override
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("¡ì3[ILSStats] Disabled!");
	}

	public JavaPlugin getPlugin() {
		return this;
	}

	public void initConfig() {
		if (!new File(this.getDataFolder().getAbsolutePath() + "/words.txt").exists())
			this.saveResource("words.txt", true);
		if (!new File(this.getDataFolder().getAbsolutePath() + "/config.yml").exists())
			this.saveResource("config.yml", false);
		cfg = YamlConfiguration.loadConfiguration(new File(this.getDataFolder().getAbsolutePath() + "/config.yml"));
		if (!new File(this.getDataFolder().getAbsolutePath() + "/biome.yml").exists())
			this.saveResource("biome.yml", false);
		biome = YamlConfiguration.loadConfiguration(new File(this.getDataFolder().getAbsolutePath() + "/biome.yml"));
	}
}
