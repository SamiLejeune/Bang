package fr.azgardien.cartes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.Joueur;

public class Planque extends Carte
{
    public Planque(String val, Couleur coul)
    {
        this(val,coul,"A chaque fois que le joueur est la cible d'un Bang, il pioche une carte de la pioche. Si cette carte est un coeur, il �vite le tire.","Planque");
    }

    private Planque(String val, Couleur coul, String desc, String nom)
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
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("�9Planque");
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