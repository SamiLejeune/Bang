package fr.azgardien.roles;

import java.util.ArrayList;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;
import fr.azgardien.cartes.Carte;

public abstract class Personnage {

	private int vie;
	private String nom;
	private String description;
	private int rangeLunette, distance;
	public int limiteBang;
	
	public int getLimiteBang() {
		return limiteBang;
	}



	public int getRangeLunette() {
		return rangeLunette;
	}


	
	public int getDistance() {
		return distance;
	}


	/**
	 * 
	 * @param vie : vie de base du perso
	 * @param nom : son nom 
	 * @param description : sa description
	 * @param rangeLunette : amélioration la range de la lunette (1 si lunette ,0 par défault) 
 	 * @param distance : amélioration la distance qu'il a contre les autres joueurs (1 si mustang , 0 par default) 
	 */
	public Personnage(int vie, String nom, String description , int rangeLunette, int distance) {
		this.vie = vie;
		this.nom = nom;
		this.description = description;
		this.rangeLunette = rangeLunette;
		this.distance = distance;
		this.limiteBang = 1;
	}
	
	public String getNom() {
		return nom;
	}


	public String getDescription() {
		return description;
	}


	public int getVie() {
		return vie;
	}
	
	public abstract String touche(Joueur victime , Joueur tireur);
	public abstract String pioche();
	public ArrayList<Carte> piocheTour() {
		ArrayList<Carte> pioche = new ArrayList<Carte>();
		for (int i = 0 ; i < 2 ; i++) {
			Carte c = BangController.getInstance().getCarte();
			pioche.add(c);
		}
		return pioche;
	}
	public abstract String tire(Joueur source , Joueur cible);
	public abstract String mainVide();
	public abstract String joueurMort(Joueur recup, Joueur mort);
	
	
	public String toString() {
		return this.getNom();
	}
}
