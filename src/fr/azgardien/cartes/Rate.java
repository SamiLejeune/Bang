package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Rate extends Carte
{
    public Rate(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur évite un Bang à son encontre.","Rate");
    }

    private Rate(Valeur val, Couleur coul, String desc, String nom)
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