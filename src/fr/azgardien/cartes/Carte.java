package fr.azgardien.cartes;

public abstract class Carte 
{
    private Valeur val;
	private Couleur couleur;
	private String description;
	private String nom;
}

public Carte(Valeur valeur, Couleur coul, String desc, String nom)
{
    this.val = valeur;
    this.couleur = coul;
    this.description = desc;
    this.nom = nom;
}

public int getNum()
{
    return this.num;
}

public int getCouleur()
{
    return this.couleur;
}

public int getDesc()
{
    return this.description;
}

public int getNom()
{
    return this.nom;
}

public abstract void appliquerEffet(Joueur target){}