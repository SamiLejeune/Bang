package fr.azgardien.cartes;

public abstract class Carte 
{
    private int num;
	private Couleur couleur;
	private String description;
	private String nom;
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