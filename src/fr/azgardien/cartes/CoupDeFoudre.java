package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class CoupDeFoudre extends Carte
{
	public CoupDeFoudre(Valeur valeur, Couleur coul) 
    {
		this(valeur,coul,"Le joueur défausse une carte à un joueur de son
        choix. Il pioche soit au hasard dans la main, soit
        parmi les cartes posées sur la table.","CoupDeFoudre");
	}
	
	private CoupDeFoudre(Valeur val, Couleur coul, String description, String nom) 
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
