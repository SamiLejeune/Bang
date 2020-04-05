package fr.azgardien.bang;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class MortTask extends BukkitRunnable {

	@Override
	public void run() {
		
		BangController instance = BangController.getInstance();
		if (instance.startTask) {
			ArrayList<Joueur> list = instance.getPlayers();
			for (Joueur j : list) {
				if (j.getVie() <= 0) {
					instance.resetScoreBoard(instance.getPlayerServer(j));
					instance.resetAllPlateforme(instance.currentJoueur.getLocation());
					System.out.println("(1)Au tour de : " + instance.currentJoueur);
					if (instance.currentJoueur == j) {
						instance.currentJoueur = instance.nextJoueur();
						System.out.println("Au tour de : " + instance.currentJoueur);
						instance.currentJoueur.piocheTour();
					}
					instance.currentNbBang = 0;
					
					instance.affichageCurrent();
					instance.exitPlayer(j);
					if (instance.doitMelanger()) {
						instance.melanger();						
					}
					if (!j.aDynamite) {
						
						if (j.getRole() == Role.HorsLaLoi) {
							Bukkit.broadcastMessage("§b"+j.tueur.getPseudo() + " gagne 3 cartes");
							j.tueur.pioche(instance.getCarte());
							j.tueur.pioche(instance.getCarte());
							j.tueur.pioche(instance.getCarte());
						} else if (j.getRole() == Role.Adjoint) {
							if (j.tueur.getRole() == Role.Sherif) {
								Bukkit.broadcastMessage("§bGrave erreur du shérif, il perd ses cartes");
								j.tueur.clear();
							}
						}
						System.out.println(instance.defausses);
						System.out.println("Apres mort " + instance.allCarteSize());
					}
				}
			}
		}
	}

}
