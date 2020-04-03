package fr.azgardien.cartes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Dynamite extends Carte
{
    public Dynamite(String val, Couleur coul)
    {
        this(val,coul,"Le joueur pose la carte devant lui le temps d'un tour de table. Lorsque son tour revient, le joueur défausse la première carte de la pioche. Si elle est entre le 2 et le 9 de pique, la dynamite explose et le joueur perd 3 points de vie. Si pas, le joueur passe la dynamite au joueur suivant.","Dynamite");
    }

    private Dynamite(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
    	BangController.getInstance().getPlayerServer(source).closeInventory();
    	Bukkit.broadcastMessage("§a" + source.getPseudo() + " pose une dynamite");
    	BangController.getInstance().setDynamite(source);
    	source.aDynamite = true;
    	source.pose(this);
    	BangController.getInstance().getPlayerServer(source).openInventory(BangController.getInstance().playerInventory(source));
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.TNT);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§9Dynamite");
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