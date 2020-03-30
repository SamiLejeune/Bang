package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class PaulRegret extends Personnage {

	public PaulRegret() {
		this(3,"Paul Regret", "On considère qu'il a un Mustang en jeu à tout moment : tous les autres joueurs doivent ajouter 1 à la distance qui les sépare de lui. S'il a un autre Mustang réel en jeu, il peut utiliser les 2, ce qui augmente la distance de 2 en tout",0,1);
	}
	
	private PaulRegret(int vie, String nom, String description, int rangeLunette, int distance) {
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
