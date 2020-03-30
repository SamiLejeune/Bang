package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Planque extends Carte
{
    public Planque(Valeur val, Couleur coul)
    {
        this(val,coul,"A chaque fois que le joueur est la cible d'un Bang, il pioche une carte de la pioche. Si cette carte est un coeur, il évite le tire.","Planque");
    }

    private Planque(Valeur val, Couleur coul, String desc, String nom)
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