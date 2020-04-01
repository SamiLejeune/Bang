package fr.azgardien.cartes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Diligence extends Carte
{
    public Diligence(String val, Couleur coul)
    {
        this(val,coul,"Le joueur pioche 3 cartes.","Diligence");
    }

    private Diligence(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
    	
		source.pioche(BangController.getInstance().getCarte());
		source.pioche(BangController.getInstance().getCarte());
		source.pioche(BangController.getInstance().getCarte());
		
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.STORAGE_MINECART);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("�6Diligence");
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