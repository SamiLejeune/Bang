package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Saloon extends Carte
{
    public Saloon(Valeur val, Couleur coul)
    {
        this(val,coul,"Tous les joueurs gagnent un point de vie (sans dépasser leur capital de départ).","Saloon");
    }

    private Saloon(Valeur val, Couleur coul, String desc, String nom)
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