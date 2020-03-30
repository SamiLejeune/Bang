package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class JesseJones extends Personnage {

	public JesseJones() {
		this(4,"Jesse Jones", "Durant la phase 1 de son tour, il peut choisir soit de piocher la première carte de la pioche, soit de prendre 1 carte au hasard dans la main d'un autre joueur. "
				+ "Il pioche ensuite sa deuxième carte dans la pioche",0,0);
	}
	private JesseJones(int vie, String nom, String description, int rangeLunette, int distance) {
		super(vie, nom, description, rangeLunette, distance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String touche(Joueur victime, Joueur tireur) {
		// TODO Auto-generated method stub
		return null;
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
