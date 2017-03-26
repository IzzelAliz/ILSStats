package com.ilummc.ilsstats.task;

import com.ilummc.ilsstats.UpdateBoard;

public class BlockScoreboardTask implements Runnable {
	String p;

	public BlockScoreboardTask(String p) {
		this.p = p;
	}

	@Override
	public void run() {
		UpdateBoard.blocklist.remove(p);
	}

}
