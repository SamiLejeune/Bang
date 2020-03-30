package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Lunette extends Carte
{
    public Lunette(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur voit tous les autres joueurs à une distance réduite de 1.","Lunette");
    }

    private Lunette(Valeur val, Couleur coul, String desc, String nom)
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