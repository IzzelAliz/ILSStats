package com.ilummc.ilsstats.listener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ilummc.ilsstats.Main;
import com.ilummc.ilsstats.event.AddWordEvent;

public class SensitiveListener implements Listener {
	Set<String> lib;
	Random a = new Random();
	String[] keys = new String[] { "开心", "高兴", "快乐" };

	public SensitiveListener() {
		lib = new HashSet<>();
		File file = new File(Main.instance.getDataFolder().getAbsolutePath() + "/words.txt");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String s = null;
			Bukkit.getConsoleSender().sendMessage("§aReading sensitive words from file");
			while ((s = br.readLine()) != null) {
				if (s.equals(""))
					continue;
				StringBuilder result = new StringBuilder();
				for (int i = 0; i <s.length(); i++) {
					result.append(s.charAt(i)).append("[a-zA-Z0-9\\p{Punct}]{0,3}");
				}
				lib.add(result.toString());
			}
			br.close();
			Bukkit.getConsoleSender().sendMessage("§aRead complete");
		} catch (FileNotFoundException e) {
			Bukkit.getConsoleSender().sendMessage("§cError: " + e.toString());
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("§cError: " + e.toString());
		}
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent evt) {
		String res = evt.getMessage();
		for (String s : lib) {
			res = res.replaceAll(s, getChar());
		}
		evt.setMessage(res);
	}

	@EventHandler
	public void onAdd(AddWordEvent evt) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < evt.getWord().length(); i++) {
			result.append(evt.getWord().charAt(i)).append("[a-zA-Z\\p{Punct}]{0,3}");
		}
		lib.add(result.toString());
		this.writeNewWord(evt.getWord());
		evt.getPlayer().sendMessage("§3[ILSStats] §a成功添加敏感词  §c"+evt.getWord());
	}

	public void writeNewWord(String s) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(new File(Main.instance.getDataFolder().getAbsolutePath() + "/words.txt"),true),
					"UTF-8"));
			bw.newLine();
			bw.write(s);
			bw.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getChar() {
		return keys[a.nextInt(keys.length)];
	}
}
