package fr.azgardien.cartes;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Biere extends Carte
{
	public Biere(String valeur, Couleur coul) 
    {
		this(valeur,coul,"Le joueur gagne un point de vie (sans dépasser son capital de départ). Il peut aussi jouer cette carte hors de son tour de jeu s'il subit une blessure mortelle.","Bière");
	}
	
	private Biere(String val, Couleur coul, String description, String nom) 
    {
		super(val, coul, description, nom);
	}

	@Override
	public void appliquerEffet(Joueur source, Joueur target) {
		if (source.biere(this)) {
			if (BangController.getInstance().getPlayers().size() ==2) {
				Player p = BangController.getInstance().getPlayerServer(source);
				p.sendMessage("§c Bière et saloon interdit en 1v1");
				source.pioche(this);
				source.damage(1);
			} else {
				BangController.getInstance().getPlayerServer(source).closeInventory();	
				Bukkit.broadcastMessage("§a"+source.getPseudo() + " boit une bière pour regagner une vie");
				BangController.getInstance().getPlayerServer(source).openInventory(BangController.getInstance().playerInventory(source));
			}	
		} else {
			if (BangController.getInstance().getPlayers().size() ==2) {
				Player p = BangController.getInstance().getPlayerServer(source);
				p.sendMessage("§c Bière et saloon interdit en 1v1");
			} else {
				BangController.getInstance().getPlayerServer(source).sendMessage("§cVous êtes au max de votre vie");
			}			
		}
	
	}

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.GOLDEN_APPLE);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Bière");
		itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		itemD.setLore(Arrays.asList("§bValeur :§9 " + getVal(), "§bCouleur :§9 " + getCouleur()));
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
