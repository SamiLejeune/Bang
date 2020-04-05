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
import fr.azgardien.roles.Jourdonnais;

public class Gatling extends Carte
{
    public Gatling(String val, Couleur coul)
    {
        this(val,coul,"Le joueur tire simultanément sur tous les autres joueurs, qui ont le droit d'esquiver.","Gatling");
    }

    private Gatling(String val, Couleur coul, String desc, String nom)
    {
        super(val, coul, desc, nom);
    }

    @Override
    public void appliquerEffet(Joueur source,Joueur target)
    {
//    	Bukkit.broadcastMessage("§a"+source.getPseudo() + " pose une gatling");
//    	Bang b = new Bang("", Couleur.Carreau);
//    	for (Joueur j : BangController.getInstance().getPlayers()) {
//    		if (j != source) {
//    			b.appliquerEffet(source, j);
//    		}
//    		
//    	}
    	
    	
    	if (target == null) {
    		Bukkit.broadcastMessage("§a"+source.getPseudo() + " pose une gatling");
    		for (Joueur j : BangController.getInstance().players) {
				if (source != j) {
					if (j.getPerso().getClass() == Jourdonnais.class) {
						BangController instance = BangController.getInstance();
						Carte cartePlanque = instance .getCarte();
						Bukkit.broadcastMessage("§b" + j.getPseudo() + " tire : " + cartePlanque.getNom() + " ["+cartePlanque.getVal() + " de " + cartePlanque.getCouleur() + "]");
						instance.defausse(cartePlanque);
						System.out.println("Apres action " + BangController.getInstance().allCarteSize());
						System.out.println(instance.defausses);
						if (cartePlanque.getCouleur() == Couleur.Coeur) {											
							Bukkit.broadcastMessage("§a"+j.getPseudo() + " évite la gatling");	
						} else {
							BangController controller = BangController.getInstance();
							j.actionRecu = this;
							source.joueurAttaque = j;
							j.sourceAction = source;
							Player cible = controller.getPlayerServer(j);
							Inventory action = controller.actionInventory(source, j, getNom());
							cible.openInventory(action);
						}
					} else if (j.aPlanque) {
						BangController instance = BangController.getInstance();
						Carte cartePlanque = instance .getCarte();
						Bukkit.broadcastMessage("§b" + j.getPseudo() + " tire : " + cartePlanque.getNom() + " ["+cartePlanque.getVal() + " de " + cartePlanque.getCouleur() + "]");
						instance.defausse(cartePlanque);
						System.out.println("Apres action " + BangController.getInstance().allCarteSize());
						System.out.println(instance.defausses);
						if (cartePlanque.getCouleur() == Couleur.Coeur) {											
							Bukkit.broadcastMessage("§a"+j.getPseudo() + " évite la gatling");	
						} else {
							BangController controller = BangController.getInstance();
							j.actionRecu = this;
							source.joueurAttaque = j;
							j.sourceAction = source;
							Player cible = controller.getPlayerServer(j);
							Inventory action = controller.actionInventory(source, j, getNom());
							cible.openInventory(action);
						}
					}	else {
						BangController controller = BangController.getInstance();
						j.actionRecu = this;
						source.joueurAttaque = j;
						j.sourceAction = source;
						Player cible = controller.getPlayerServer(j);
						Inventory action = controller.actionInventory(source, j, getNom());
						cible.openInventory(action);
					}
					
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
		ItemStack item = new ItemStack(Material.BOW);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Gatling");
		itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		itemD.setLore(Arrays.asList("§bValeur :§9 " + getVal(), "§bCouleur :§9 " + getCouleur()));
		itemD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemD);
		return item;
	}

	@Override
	public Carte carteQuiContre() {
		return BangController.getInstance().getType("Rate");
	}

}