package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class Jourdonnais extends Personnage {

	public Jourdonnais() {
		this(4,"Jourdonnais", "On consid�re qu'il a une Planque � tout moment. Il peut d�gainer quand il est la cible d'un BANG!, et s'il tire un coeur, "
				+ "le tire l'a rat�. S'il a une vraie carte Planque en jeu, il peut l'utiliser �galement, ce qui lui donne deux chances d'annuler un BANG! avant d'avoir � jouer un Rat�!",0,0);
	}
	
	private Jourdonnais(int vie, String nom, String description, int rangeLunette, int distance) {
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
