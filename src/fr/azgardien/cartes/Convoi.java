package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Convoi extends Carte
{
    public Convoi(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur pioche 2 cartes.","Convoi");
    }

    private Convoi(Valeur val, Couleur coul, String desc, String nom)
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