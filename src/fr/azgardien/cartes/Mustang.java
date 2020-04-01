package fr.azgardien.cartes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.Joueur;

public class Mustang extends Carte
{
    public Mustang(String val, Couleur coul)
    {
        this(val,coul,"Tous les autres joueurs voient le joueur à une distance augmentée de 1.","Mustang");
    }

    private Mustang(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
        //TODO Auto-generated method stub
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.RED_ROSE);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§9Mustang");
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