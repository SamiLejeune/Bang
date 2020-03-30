package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Volcanic extends Carte
{
    public Volcanic(Valeur val, Couleur coul)
    {
        this(val,coul,"Cette arme permet de jouer autant de cartes Bang que
        le joueur désire à une distance de 1.","Volcanic");
    }

    private Volcanic(Valeur val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur target)
    {
        //TODO Auto-generated method stub
        return null;
    }

}