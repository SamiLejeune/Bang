package fr.azgardien.cartes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.Joueur;

public class Prison extends Carte
{
    public Prison(String val, Couleur coul)
    {
        this(val,coul,"Lors de son prochain tour, le joueur visé par la prison doit piocher une carte. Si c'est un coeur, il peut jouer son tour normalement, sinon il passe son tour.","Prison");
    }

    private Prison(String val, Couleur coul, String desc, String nom)
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
		ItemStack item = new ItemStack(Material.IRON_BARDING);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§9Prison");
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