package fr.azgardien.cartes;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Duel extends Carte
{
    public Duel(String val, Couleur coul)
    {
        this(val,coul,"Le joueur regarde un autre joueur dans les yeux. Celui-ci doit jouer une carte Bang, A laquelle le joueur peut répondre par une autre carte Bang, et ainsi de suite. Le joueur qui ne peut plus jouer de carte Bang perd le duel et un point de vie.","Duel");
    }

    private Duel(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
        BangController controller = BangController.getInstance();
        controller.getPlayerServer(target).openInventory(controller.actionInventory(source, target, "Duel"));
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.WOOD_SWORD);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Duel");
		itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		itemD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemD);
		return item;
	}

	@Override
	public Carte carteQuiContre() {
		return BangController.getInstance().getType("Bang");
	}

}