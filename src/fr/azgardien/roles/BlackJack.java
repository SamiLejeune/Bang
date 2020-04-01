package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

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
	

}
