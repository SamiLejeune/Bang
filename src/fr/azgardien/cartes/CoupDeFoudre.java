package fr.azgardien.cartes;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.Joueur;

public class CoupDeFoudre extends Carte
{
	public CoupDeFoudre(String valeur, Couleur coul) 
    {
		this(valeur,coul,"Le joueur défausse une carte à  un joueur de son choix. Il pioche soit au hasard dans la main, soit parmi les cartes posées sur la table.","Coup de foudre");
	}
	
	private CoupDeFoudre(String val, Couleur coul, String description, String nom) 
    {
		super(val, coul, description, nom);
	}

	@Override
	public void appliquerEffet(Joueur source,Joueur target) 
    {
		// TODO Auto-generated method stub
	}

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.LAVA_BUCKET);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Coup de foudre");
		itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		itemD.setLore(Arrays.asList("§bValeur :§9 " + getVal(), "§bCouleur :§9 " + getCouleur()));
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
