package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class CarteBiere extends Carte
{
	public CarteBiere(Valeur valeur, Couleur coul) 
    {
		this(valeur,coul,"Le joueur gagne un point de vie (sans dépasser son capital de départ). 
        Il peut aussi jouer cette carte hors de son tour de jeu s'il subit une blessure mortelle.","Bière");
	}
	
	private CarteBiere(Valeur val, Couleur coul, String description, String nom) 
    {
		super(val, coul, description, nom);
	}

	@Override
	public void appliquerEffet(Joueur target) 
    {
		// TODO Auto-generated method stub
		return null;
	}

}
