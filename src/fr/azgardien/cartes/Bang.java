package fr.azgardien.cartes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Bang extends Carte 
{
    public Bang(String val, Couleur coul)
    {
        this(val,coul,"Le joueur tire sur un joueur de son choix présent dans son champ de vision.","Bang");
    }

    private Bang(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

	@Override
	public void appliquerEffet(Joueur source, Joueur target) {
		if (target.finAction == false && target.contreAction == false) {
			Bukkit.broadcastMessage("§a"+source.getPseudo() + " a BANG " + target.getPseudo());	
			BangController controller = BangController.getInstance();
			target.actionRecu = this;
			source.joueurAttaque = target;
			target.sourceAction = source;
			Player cible = controller.getPlayerServer(target);
			Inventory action = controller.actionInventory(source, target, getNom());
			cible.openInventory(action);
		} else if (target.finAction == true && target.contreAction == false) {
			target.bang(source);
			target.tueur = source;
		}
		
	}

    
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.FIREBALL);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Bang");
		itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		itemD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemD);
		return item;
	}

	@Override
	public Carte carteQuiContre() {
		return BangController.getInstance().getType("Rate");
	}

}