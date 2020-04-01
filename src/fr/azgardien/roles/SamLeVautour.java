package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class SamLeVautour extends Personnage {

	public SamLeVautour() {
		this(4,"Sam le vautour" , "Dès qu'un personnage est éliminé de la partie, Sam prend toutes les cartes que ce joueur avait en main et en jeu, et il les ajoute à sa propre main.",0,0);
	}
	private SamLeVautour(int vie, String nom, String description, int rangeLunette, int distance) {
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
