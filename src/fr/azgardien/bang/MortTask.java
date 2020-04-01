package fr.azgardien.bang;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MortTask extends BukkitRunnable {

	@Override
	public void run() {
		
		if (BangController.getInstance().startTask) {
			ArrayList<Joueur> list = BangController.getInstance().getPlayers();
			for (Joueur j : list) {
				if (j.getVie() == 0) {
					BangController.getInstance().exitPlayer(j);
				}
			}
		}
	}

}
