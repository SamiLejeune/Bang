package fr.azgardien.roles;

import fr.azgardien.bang.Joueur;

public class RoseDoolan extends Personnage {

	public RoseDoolan() {
		this(4,"Rose Doolan", "On consid�re qu'elle a une Lunette en jeu � tout moment : la distance de tous les autres joueurs est r�duite de 1 pour elle. Si elle a une autre Lunette r�elle en jeu, elle peut utiliser les 2, ce qui r�duit la distance de tous les autres joueurs de 2 en tout.",1,0);
	}
	private RoseDoolan(int vie, String nom, String description, int rangeLunette, int distance) {
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
