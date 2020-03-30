package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class ElGringo extends Personnage{

	public ElGringo() {
		this(3,"El Gringo", "Chaque fois qu'il perd un point de vie à cause d'une carte jouée par un autre joueur, il tire une carte au hasard dans la main de ce dernier (une carte par point de vie). Si ce joueur n'a plus de cartes, dommage, "
				+ "il ne peut pas lui en prendre!",0,0);
	}
	
	private ElGringo(int vie, String nom, String description, int rangeLunette, int distance) {
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
