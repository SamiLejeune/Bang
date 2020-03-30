package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Gatling extends Carte
{
    public Gatling(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur tire simultanément sur tous les autres
        joueurs, qui ont le droit d’esquiver.","Gatling");
    }

    private Gatling(Valeur val, Couleur coul, String desc, String nom)
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