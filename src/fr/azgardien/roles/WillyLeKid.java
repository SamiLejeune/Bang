package fr.azgardien.roles;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class WillyLeKid extends Personnage {

	public WillyLeKid() {
		this(4,"Willy le Kid", "Il peut jouer autant de cartes BANG! qu'il le désire pendant son tour",0,0);
	}
	
	private WillyLeKid(int vie, String nom, String description, int rangeLunette, int distance) {
		super(vie, nom, description, rangeLunette, distance);
		// TODO Auto-generated constructor stub
		super.limiteBang = 10000;
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

	

}
