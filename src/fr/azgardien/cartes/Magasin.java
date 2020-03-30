package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Magasin extends Carte
{
    public Magasin(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur pioche autant de cartes qu’il y a de joueurs.
        En commençant par le joueur et en faisant le tour de
        la table, chacun choisit une des cartes et l’intègre
        dans sa main.","Magasin");
    }

    private Magasin(Valeur val, Couleur coul, String desc, String nom)
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