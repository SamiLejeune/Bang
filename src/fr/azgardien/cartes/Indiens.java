package fr.azgardien.cartes;

import java.util.Arrays;

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
import fr.azgardien.roles.ElGringo;

public class Indiens extends Carte
{
    public Indiens(String val, Couleur coul)
    {
        this(val,coul,"Tous les autres joueurs doivent défausser une carte Bang ou perdre un point de vie.","Indiens");
    }

    private Indiens(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
    	if (target == null) {
    		Bukkit.broadcastMessage("§a"+source.getPseudo() + " pose un indien");
    		for (Joueur j : BangController.getInstance().players) {
				if (source != j) {
					BangController controller = BangController.getInstance();
					j.actionRecu = this;
					source.joueurAttaque = j;
					j.sourceAction = source;
					Player cible = controller.getPlayerServer(j);
					Inventory action = controller.actionInventory(source, j, getNom());
					cible.openInventory(action);
				}
			}
		} else if (target.finAction == true && target.contreAction == false) {
			target.bang(source);
			if (BangController.getInstance().currentNbBang > 0) BangController.getInstance().currentNbBang--;
			target.tueur = source;
			if (target.getPerso().getClass() == ElGringo.class) {
				Player player = BangController.getInstance().getPlayerServer(target);
				player.closeInventory();
				Bukkit.broadcastMessage("§6El Gringo active sa capacité et pioche dans la main du joueur");		
				Carte carte = source.getRandomFromMain();
				target.pioche(carte);
				player.sendMessage("§dVous récupérez : " + carte.getNom() + " ["+carte.getVal() + " de " + carte.getCouleur() + "]" );
			}
		} 
    }

	@Override
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.FEATHER);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Indiens");
		itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		itemD.setLore(Arrays.asList("§bValeur :§9 " + getVal(), "§bCouleur :§9 " + getCouleur()));
		itemD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemD);
		return item;
	}

	@Override
	public Carte carteQuiContre() {
		return BangController.getInstance().getType("Bang");
	}

}