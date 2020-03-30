package fr.azgardien.cartes;

import fr.azgardien.bang.Joueur;

public class Winchester extends Carte
{
    public Winchester(Valeur val, Couleur coul)
    {
        this(val,coul,"Le joueur a maintenant un champ de vision de 5.","Winchester");
    }

    private Winchester(Valeur val, Couleur coul, String desc, String nom)
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