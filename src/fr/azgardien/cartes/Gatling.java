package fr.azgardien.cartes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.Joueur;

public class Gatling extends Carte
{
    public Gatling(String val, Couleur coul)
    {
        this(val,coul,"Le joueur tire simultanément sur tous les autres joueurs, qui ont le droit d'esquiver.","Gatling");
    }

    private Gatling(String val, Couleur coul, String desc, String nom)
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
		ItemStack item = new ItemStack(Material.BOW);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Gatling");
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