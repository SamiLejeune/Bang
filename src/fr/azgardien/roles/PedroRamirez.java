package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class PedroRamirez extends Personnage {

	public PedroRamirez() {
		this(4,"Pedro Ramirez", "Durant la phase 1 de son tour, il peut choisir de piocher la première carte de la défausse au lieu de la prendre dans la pioche. Il pioche sa seconde carte normalement, dans la pioche.",0,0);
	}
	
	private PedroRamirez(int vie, String nom, String description, int rangeLunette, int distance) {
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
