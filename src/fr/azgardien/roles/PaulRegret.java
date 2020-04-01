package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class PaulRegret extends Personnage {

	public PaulRegret() {
		this(3,"Paul Regret", "On consid�re qu'il a un Mustang en jeu � tout moment : tous les autres joueurs doivent ajouter 1 � la distance qui les s�pare de lui. S'il a un autre Mustang r�el en jeu, il peut utiliser les 2, ce qui augmente la distance de 2 en tout",0,1);
	}
	
	private PaulRegret(int vie, String nom, String description, int rangeLunette, int distance) {
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
