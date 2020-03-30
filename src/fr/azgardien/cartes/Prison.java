package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Prison extends Carte
{
    public Prison(Valeur val, Couleur coul)
    {
        this(val,coul,"Lors de son prochain tour, le joueur visé par la prison doit piocher une carte. Si c'est un coeur, il peut jouer son tour normalement, sinon il passe son tour.","Prison");
    }

    private Prison(Valeur val, Couleur coul, String desc, String nom)
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