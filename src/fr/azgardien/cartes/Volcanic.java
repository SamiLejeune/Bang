package fr.azgardien.cartes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Volcanic extends Carte
{
    public Volcanic(String val, Couleur coul)
    {
        this(val,coul,"Cette arme permet de jouer autant de cartes Bang que le joueur désire à une distance de 1.","Volcanic");
    }

    private Volcanic(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
    	
		BangController.getInstance().getPlayerServer(source).closeInventory();
		if (source.poseArme(this)) {
			Bukkit.broadcastMessage("§a"+source.getPseudo() + " pose une volcanic.");
			source.getPerso().limiteBang = 10000;
		}
		BangController.getInstance().getPlayerServer(source).openInventory(BangController.getInstance().playerInventory(source));
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.DIAMOND_AXE);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§9Volcanic");
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