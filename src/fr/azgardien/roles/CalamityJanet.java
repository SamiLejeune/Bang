package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class CalamityJanet extends Personnage {

	public CalamityJanet() {
		this(4,"Calamity Janet", "Elle peut utiliser les cartes BANG! comme des cartes Rat�! et vice-versa. "
				+ "Si elle joue un Rat�! � la place d'un BANG!, elle ne peut pas jouer d'autre carte BANG! durant son tour.", 0,0);
	}
	private CalamityJanet(int vie, String nom, String description, int rangeLunette, int distance) {
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
