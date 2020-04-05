package fr.azgardien.cartes;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Schofield extends Carte
{
    public Schofield(String val, Couleur coul)
    {
        this(val,coul,"Le joueur a maintenant un champ de vision de 2.","Schofield");
    }

    private Schofield(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
        super.ameliorerDistance(1);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
    	
		BangController.getInstance().getPlayerServer(source).closeInventory();
		if (source.poseArme(this)) {
			Bukkit.broadcastMessage("§a"+source.getPseudo() + " pose une schofield.");
		}
		BangController.getInstance().getPlayerServer(source).openInventory(BangController.getInstance().playerInventory(source));
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.STONE_SWORD);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§9Schofield");
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