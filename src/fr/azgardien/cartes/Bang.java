package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Bang extends Carte
{
    public Bang(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur tire sur un joueur de son choix présent dans son champ de vison.","Bang");
    }

    private Bang(Valeur val, Couleur coul, String desc, String nom)
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