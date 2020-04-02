package fr.azgardien.cartes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Magasin extends Carte
{
    public Magasin(String val, Couleur coul)
    {
        this(val,coul,"Le joueur pioche autant de cartes qu'il y a de joueurs. En commençant par le joueur et en faisant le tour de la table, chacun choisit une des cartes et l'intègredans sa main.","Magasin");
    }

    private Magasin(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
        BangController controller = BangController.getInstance();
        if (controller.magasins.size() == 0) {
        	controller.initMagasin();
        }
        controller.lanceurMagasin = source;
        controller.currentMagasin = source;
        
        Bukkit.broadcastMessage("§a" + source.getPseudo() + " pose un magasin");
        controller.getPlayerServer(controller.currentJoueur).closeInventory();
        controller.getPlayerServer(controller.currentJoueur).openInventory(controller.magasinInventory());
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.DIAMOND);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Magasin");
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