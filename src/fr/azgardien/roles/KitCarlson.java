package fr.azgardien.roles;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;
import fr.azgardien.cartes.Carte;
import fr.azgardien.cartes.Couleur;

public class KitCarlson extends Personnage {

	public KitCarlson() {
		this(4, "Kit Carlson", "Durant la phase 1 de son tour, il regarde les 3 premières cartes de la pioche, en choisit 2 qu'il garde et repose la troisème sur la pioche, face cachée",0,0);
	}
	private KitCarlson(int vie, String nom, String description, int rangeLunette, int distance) {
		super(vie, nom, description, rangeLunette, distance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String touche(Joueur victime, Joueur tireur) {
		victime.damage(1);
		return "§b" + victime.getPseudo() +" est touché, il perd un point de vie";
	}

	@Override
	public String pioche() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tire(Joueur source, Joueur cible) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mainVide() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String joueurMort(Joueur recup, Joueur mort) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<Carte> piocheTour() {
		Bukkit.broadcastMessage("§6Kit Carlson tire 3 cartes et choisi la carte à éliminer avec /carlson [1-2-3]");
		BangController instance = BangController.getInstance();
		Player player = instance.getPlayerServer(instance.getJoueurFromPerso(this));
		ArrayList<Carte> pioche = new ArrayList<Carte>();
		for (int i = 0 ; i < 3 ; i++) {
			Carte c = instance.getCarte();
			player.sendMessage("§6Carte : " + c.getNom() + " ["+c.getVal() + " de " + c.getCouleur() + "]" + " /carlson " + (i+1));
			pioche.add(c);
		}
		instance.kitCarlson = instance.getJoueurFromPerso(this);
		instance.kitChoix = pioche;
		return new ArrayList<Carte>();
	}

}
