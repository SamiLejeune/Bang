package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Braquage extends Carte
{
	public Braquage(Valeur valeur, Couleur coul) 
    {
		this(valeur,coul,"Le joueur vole une carte à un joueur situé à une
        distance de 1. Il pioche soit au hasard dans la main,
        soit parmi les cartes posées sur la table.","Braquage");
	}
	
	private Braquage(Valeur val, Couleur coul, String description, String nom) 
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
