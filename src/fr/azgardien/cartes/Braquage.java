package fr.azgardien.cartes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.Joueur;

public class Braquage extends Carte
{
	public Braquage(String valeur, Couleur coul) 
    {
		this(valeur,coul,"Le joueur vole une carte à un joueur situé à une distance de 1. Il pioche soit au hasard dans la main,soit parmi les cartes posées sur la table.","Braquage");
	}
	
	private Braquage(String val, Couleur coul, String description, String nom) 
    {
		super(val, coul, description, nom);
	}

	@Override
	public void appliquerEffet(Joueur source, Joueur target) 
    {
		

	}

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.FISHING_ROD);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Braquage");
		itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		itemD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemD);
		return item;
	}

	@Override
	public Carte carteQuiContre() {
		// TODO Auto-generated method stub
		return null;
	}

}
