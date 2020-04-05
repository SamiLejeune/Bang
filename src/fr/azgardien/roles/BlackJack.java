package fr.azgardien.roles;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;
import fr.azgardien.cartes.Carte;
import fr.azgardien.cartes.Couleur;

public class BlackJack extends Personnage {

	public BlackJack() {
		this(4,"Black Jack", "Durant la phase 1 de son tour, il doit montrer la seconde carte qu'il a piochée. Si c'est un coeur ou "
				+ "un carreau, il tire une carte supplémentaire (sans la révéler)" , 0 , 0);
	}
	private BlackJack(int vie, String nom, String description, int rangeLunette, int distance) {
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
		Bukkit.broadcastMessage("§6Black Jack pioche comme c'est son tour");
		ArrayList<Carte> pioche = new ArrayList<Carte>();
		Carte c1 = BangController.getInstance().getCarte();
		pioche.add(c1);
		Carte c2  = BangController.getInstance().getCarte();
		pioche.add(c2);
		Bukkit.broadcastMessage("§6Il tire : " + c2.getNom() + " ["+c2.getVal() + " de " + c2.getCouleur() + "]");
		if(c2.getCouleur() == Couleur.Coeur || c2.getCouleur() == Couleur.Carreau) {
			Bukkit.broadcastMessage("§6Il pioche une carte supplémentaire");
			Carte c3  = BangController.getInstance().getCarte();
			pioche.add(c3);
		}
		return pioche;
	}
	

}
