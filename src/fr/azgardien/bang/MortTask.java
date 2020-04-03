package fr.azgardien.bang;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class MortTask extends BukkitRunnable {

	@Override
	public void run() {
		
		if (BangController.getInstance().startTask) {
			ArrayList<Joueur> list = BangController.getInstance().getPlayers();
			for (Joueur j : list) {
				if (j.getVie() <= 0) {
					BangController.getInstance().resetScoreBoard(BangController.getInstance().getPlayerServer(j));
					BangController.getInstance().exitPlayer(j);				
					if (j.getRole() == Role.HorsLaLoi) {
						Bukkit.broadcastMessage("§b"+j.tueur.getPseudo() + " gagne 3 cartes");
						j.tueur.pioche(BangController.getInstance().getCarte());
						j.tueur.pioche(BangController.getInstance().getCarte());
						j.tueur.pioche(BangController.getInstance().getCarte());
					} else if (j.getRole() == Role.Adjoint) {
						if (j.tueur.getRole() == Role.Sherif) {
							Bukkit.broadcastMessage("§bGrave erreur du shérif, il perd ses cartes");
							j.tueur.clear();
						}
					}
					System.out.println(BangController.getInstance().defausses);
					System.out.println("Apres mort " + BangController.getInstance().allCarteSize());
				}
			}
		}
	}

}
