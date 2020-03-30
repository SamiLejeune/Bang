package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Indiens extends Carte
{
    public Indiens(Valeur val, Couleur coul)
    {
        this(val,coul,"Tous les autres joueurs doivent défausser une carte
        Bang ou perdre un point de vie.","Indiens");
    }

    private Indiens(Valeur val, Couleur coul, String desc, String nom)
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