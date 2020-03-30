package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

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
