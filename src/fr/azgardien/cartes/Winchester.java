package fr.azgardien.cartes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Winchester extends Carte
{
    public Winchester(String val, Couleur coul)
    {
        this(val,coul,"Le joueur a maintenant un champ de vision de 5.","Winchester");
    }

    private Winchester(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
        super.ameliorerDistance(4);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
    	
		BangController.getInstance().getPlayerServer(source).closeInventory();
		if (source.poseArme(this)) {
			Bukkit.broadcastMessage("§a"+source.getPseudo() + " pose une winchester.");
		}
		
		BangController.getInstance().getPlayerServer(source).openInventory(BangController.getInstance().playerInventory(source));
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§9Winchester");
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