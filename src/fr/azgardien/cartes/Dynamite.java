package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Dynamite extends Carte
{
    public Dynamite(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur pose la carte devant lui le temps d’un tour
        de table. Lorsque son tour revient, le joueur défausse
        la première carte de la pioche. Si elle est entre le 2 et
        le 9 de pique, la dynamite explose et le joueur perd 3
        points de vie. Si pas, le joueur passe la dynamite au
        joueur suivant.","Dynamite");
    }

    private Dynamite(Valeur val, Couleur coul, String desc, String nom)
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