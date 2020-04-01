package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class SlabLeFlingueur extends Personnage {

	public SlabLeFlingueur() {
		this(4,"Slab le flingueur", "Quand il joue une carte BANG! contre un joueur, celui-ci doit dépenser 2 cartes Raté! au lieu d'une pour l'annuler. L'effet de la Planque ne compte que pour une carte Raté!",0,0);
	}
	private SlabLeFlingueur(int vie, String nom, String description, int rangeLunette, int distance) {
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
