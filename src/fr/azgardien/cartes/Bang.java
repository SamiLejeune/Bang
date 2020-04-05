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
import fr.azgardien.roles.Jourdonnais;
import fr.azgardien.roles.SlabLeFlingueur;

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

    private void horsSlabEffet(Joueur source, Joueur target) {
    	if (target.getPerso().getClass() == Jourdonnais.class && target.aPlanque == false) {
			BangController instance = BangController.getInstance();
			Carte cartePlanque = instance .getCarte();
			Bukkit.broadcastMessage("§a"+target.getPseudo() + " reçoit un bang de " + source.getPseudo());
			Bukkit.broadcastMessage("§b" + target.getPseudo() + " utilise la capacité de Jourdonnais !");
			Bukkit.broadcastMessage("§b" + target.getPseudo() + " tire : " + cartePlanque.getNom() + " ["+cartePlanque.getVal() + " de " + cartePlanque.getCouleur() + "]");
			instance.defausse(cartePlanque);
			System.out.println("Apres action " + BangController.getInstance().allCarteSize());
			System.out.println(instance.defausses);
			if (cartePlanque.getCouleur() == Couleur.Coeur) {											
				Bukkit.broadcastMessage("§a"+target.getPseudo() + " évite le bang");	
			} else {
				System.out.println("Alors icii??");
				Bukkit.broadcastMessage("§a"+target.getPseudo() + " n'évite pas le bang ");	
				BangController controller = BangController.getInstance();
				target.actionRecu = this;
				source.joueurAttaque = target;
				target.sourceAction = source;
				Player cible = controller.getPlayerServer(target);
				Inventory action = controller.actionInventory(source, target, getNom());
				cible.openInventory(action);
			}
		}else if (target.aPlanque == true) {
			
			boolean evite = false;
			
			if (target.getPerso().getClass() == Jourdonnais.class) {
				System.out.println("Il a jourdonnais");
				Bukkit.broadcastMessage("§a"+target.getPseudo() + " reçoit un bang de " + source.getPseudo());
				Bukkit.broadcastMessage("§b" + target.getPseudo() + " utilise la capacité de Jourdonnais !");
				BangController instance = BangController.getInstance();
				Carte cartePlanque = instance .getCarte();
				Bukkit.broadcastMessage("§b" + target.getPseudo() + " tire : " + cartePlanque.getNom() + " ["+cartePlanque.getVal() + " de " + cartePlanque.getCouleur() + "]");
				instance.defausse(cartePlanque);
				System.out.println("Apres action " + BangController.getInstance().allCarteSize());
				System.out.println(instance.defausses);
				if (cartePlanque.getCouleur() == Couleur.Coeur) {											
					Bukkit.broadcastMessage("§a"+target.getPseudo() + " évite le bang");
					evite = true;
				}
			} else {
				Bukkit.broadcastMessage("§a"+target.getPseudo() + " reçoit un bang de " + source.getPseudo());
			}
			if (!evite) {
				System.out.println("Il a une planque");
				Bukkit.broadcastMessage("§b" + target.getPseudo() + " a une planque !");
				BangController instance = BangController.getInstance();
				Carte cartePlanque = instance .getCarte();
				Bukkit.broadcastMessage("§b" + target.getPseudo() + " tire : " + cartePlanque.getNom() + " ["+cartePlanque.getVal() + " de " + cartePlanque.getCouleur() + "]");
				instance.defausse(cartePlanque);
				System.out.println("Apres action " + BangController.getInstance().allCarteSize());
				System.out.println(instance.defausses);
				if (cartePlanque.getCouleur() == Couleur.Coeur) {											
					Bukkit.broadcastMessage("§a"+target.getPseudo() + " évite le bang");	
					evite = true;
				} 
			}
			
			
			if (!evite) {
				Bukkit.broadcastMessage("§a"+source.getPseudo() + " a BANG " + target.getPseudo());	
				BangController controller = BangController.getInstance();
				target.actionRecu = this;
				source.joueurAttaque = target;
				target.sourceAction = source;
				Player cible = controller.getPlayerServer(target);
				Inventory action = controller.actionInventory(source, target, getNom());
				cible.openInventory(action);
			}
						
		} else {
			System.out.println("Bah en tout cas je pense que je suis ici");
			Bukkit.broadcastMessage("§a"+source.getPseudo() + " a BANG " + target.getPseudo());	
			BangController controller = BangController.getInstance();
			target.actionRecu = this;
			source.joueurAttaque = target;
			target.sourceAction = source;
			Player cible = controller.getPlayerServer(target);
			Inventory action = controller.actionInventory(source, target, getNom());
			cible.openInventory(action);
		}
    }
    
    public void slab(Joueur source, Joueur target) {
    	if (target.aPlanque) {
    		System.out.println("Planque");
    	} else {
    		System.out.println("Pas Planque");
    		Bukkit.broadcastMessage("§a"+target.getPseudo() + " reçoit un bang de " + source.getPseudo() + " et il lui faut 2 Raté");
			BangController controller = BangController.getInstance();
			target.actionRecu = this;
			source.joueurAttaque = target;
			target.sourceAction = source;
			Player cible = controller.getPlayerServer(target);
			Inventory action = controller.actionInventory(source, target, getNom());
			cible.openInventory(action);
			BangController.getInstance().slabBang = true;
			BangController.getInstance().consommeRate = false;
    	}
    }
    
	@Override
	public void appliquerEffet(Joueur source, Joueur target) {
		System.out.println("Target fin action : " + target.finAction );
		System.out.println("Target contre action : " + target.contreAction );
		BangController instance = BangController.getInstance();
		System.out.println("Slab bang : " +instance.slabBang);
		if (target.finAction == false && target.contreAction == false && !instance.slabBang) {
			System.out.println("Bah ici je pense sinon Colin salope bis");
			if (source.getPerso().getClass() == SlabLeFlingueur.class) {
				System.out.println("Bang d'un slab");
				if (target.getPerso().getClass() == Jourdonnais.class) {
					System.out.println("c'est un jourdonnais");
				} else {
					slab(source,target);
				}
				
			} else {
				System.out.println("Pas slab");
				horsSlabEffet(source, target);
			}
						
		} else if (target.finAction == true && target.contreAction == false) {
			target.bang(source);
			target.tueur = source;
		} else {
			Bukkit.broadcastMessage("§a"+ target.getPseudo() + " défausse un BANG et ne perd pas de point de vie");
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