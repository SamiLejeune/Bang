package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Mustang extends Carte
{
    public Mustang(Valeur val, Couleur coul)
    {
        this(val,coul,"Tous les autres joueurs voient le joueur à une distance augmentée de 1.","Mustang");
    }

    private Mustang(Valeur val, Couleur coul, String desc, String nom)
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