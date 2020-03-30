package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Diligence extends Carte
{
    public Diligence(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur pioche 3 cartes.","Diligence");
    }

    private Diligence(Valeur val, Couleur coul, String desc, String nom)
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