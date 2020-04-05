package fr.azgardien.cartes;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.Joueur;

public class Rate extends Carte
{
    public Rate(String val, Couleur coul)
    {
        this(val,coul,"Le joueur évite un Bang à son encontre.","Rate");
    }

    private Rate(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
       Bukkit.broadcastMessage("§a"+target.getPseudo() + " évite le BANG en posant un Raté");
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.OBSIDIAN);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Raté");
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