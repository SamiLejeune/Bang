package fr.azgardien.cartes;

import org.bukkit.inventory.ItemStack;

import fr.azgardien.bang.Joueur;

public abstract class Carte  implements ContreAttaque
{
	@Override
	public String toString() {
		return "Carte [val=" + val + ", couleur=" + couleur + ", nom=" + nom + "]";
	}


	private String val;
	private Couleur couleur;
	private String description;
	private String nom;
	private int distance;



	public Carte(String valeur, Couleur coul, String desc, String nom)
	{
		this.val = valeur;
		this.couleur = coul;
		this.description = desc;
		this.nom = nom;
		this.distance = 0;
	}

	public String getNom()
	{
		return this.nom;
	}
	public int getDistance() {
		return distance;
	}
	
	public void ameliorerDistance(int distance) {
		this.distance = distance;
	}

	public Couleur getCouleur()
	{
		return this.couleur;
	}

	public String getDesc()
	{
		return this.description;
	}


	public abstract void appliquerEffet(Joueur source, Joueur target);
	public abstract ItemStack representation();
}
