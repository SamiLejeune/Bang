package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class BartCassidy extends Personnage {

	public BartCassidy() {
		this(4,"Bart Cassidy" ,"Chaque fois qu'il perd un point de vie, il pioche immédiatement une carte", 0 , 0 );
	}
	private BartCassidy(int vie, String nom, String description, int rangeLunette, int distance) {
		super(vie, nom, description, rangeLunette, distance);
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
