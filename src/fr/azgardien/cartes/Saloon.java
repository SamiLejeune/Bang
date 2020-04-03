package fr.azgardien.cartes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.bang.BangController;
import fr.azgardien.bang.Joueur;

public class Saloon extends Carte
{
    public Saloon(String val, Couleur coul)
    {
        this(val,coul,"Tous les joueurs gagnent un point de vie (sans dépasser leur capital de départ).","Saloon");
    }

    private Saloon(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
    	if (BangController.getInstance().getPlayers().size() ==2) {
    		Player p = BangController.getInstance().getPlayerServer(source);
			p.sendMessage("§c Bière et saloon interdit en 1v1");
			source.pioche(this);
		} else {
			Bukkit.broadcastMessage("§a"+source.getPseudo()+" pose un saloon");
	        for (Joueur j : BangController.getInstance().getPlayers()) {
	        	if (j.biereSaloon()) {
	        		Bukkit.broadcastMessage("§a"+j.getPseudo() + " boit une bière pour regagner une vie");
	        	} else {
	        		BangController.getInstance().getPlayerServer(j).sendMessage("§cVous êtes au max de votre vie");
	        	}
	        }
		}	
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.GOLDEN_CARROT);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Saloon");
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