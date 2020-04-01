package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class SidKetchum extends Personnage {

	public SidKetchum() {
		this(4, "Sid Ketchum", "A tout moment, il peut d�fausser 2 cartes de sa main pour regagner 1 point de vie. S'il le d�sire et si c'est possible, il peut utiliser cette caract�ristique plusieurs fois d'affil�e.",0,0);
	}
	private SidKetchum(int vie, String nom, String description, int rangeLunette, int distance) {
		super(vie, nom, description, rangeLunette, distance);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String touche(Joueur victime, Joueur tireur) {
		victime.damage(1);
		return "�b" + victime.getPseudo() +" est touch�, il perd un point de vie";
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
