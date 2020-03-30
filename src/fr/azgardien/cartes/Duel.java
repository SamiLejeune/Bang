package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Duel extends Carte
{
    public Duel(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur regarde un autre joueur dans les yeux.
        Celui-ci doit jouer une carte Bang, à laquelle le
        joueur peut répondre par une autre carte Bang, et
        ainsi de suite. Le joueur qui ne peut plus jouer de
        carte Bang perd le duel et un point de vie.","Duel");
    }

    private Duel(Valeur val, Couleur coul, String desc, String nom)
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