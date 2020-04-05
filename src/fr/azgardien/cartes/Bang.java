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
			if (cartePlanque.getCouleur() == Couleur.Coeur) {											
				Bukkit.broadcastMessage("§a"+target.getPseudo() + " évite le bang");	
			} else {
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
				Bukkit.broadcastMessage("§a"+target.getPseudo() + " reçoit un bang de " + source.getPseudo());
				Bukkit.broadcastMessage("§b" + target.getPseudo() + " utilise la capacité de Jourdonnais !");
				BangController instance = BangController.getInstance();
				Carte cartePlanque = instance .getCarte();
				Bukkit.broadcastMessage("§b" + target.getPseudo() + " tire : " + cartePlanque.getNom() + " ["+cartePlanque.getVal() + " de " + cartePlanque.getCouleur() + "]");
				instance.defausse(cartePlanque);
				if (cartePlanque.getCouleur() == Couleur.Coeur) {
					instance.currentNbBang++;
					Bukkit.broadcastMessage("§a"+target.getPseudo() + " évite le bang");
					evite = true;
				}
			} else {
				Bukkit.broadcastMessage("§a"+target.getPseudo() + " reçoit un bang de " + source.getPseudo());
			}
			if (!evite) {
				Bukkit.broadcastMessage("§b" + target.getPseudo() + " a une planque !");
				BangController instance = BangController.getInstance();
				Carte cartePlanque = instance .getCarte();
				Bukkit.broadcastMessage("§b" + target.getPseudo() + " tire : " + cartePlanque.getNom() + " ["+cartePlanque.getVal() + " de " + cartePlanque.getCouleur() + "]");
				instance.defausse(cartePlanque);
				if (cartePlanque.getCouleur() == Couleur.Coeur) {											
					Bukkit.broadcastMessage("§a"+target.getPseudo() + " évite le bang");	
					evite = true;
					instance.currentNbBang++;
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
    		Bukkit.broadcastMessage("§a"+target.getPseudo() + " reçoit un bang de " + source.getPseudo() + " et il lui faut 2 Raté");
    		Bukkit.broadcastMessage("§b" + target.getPseudo() + " a une planque !");
			BangController instance = BangController.getInstance();
			Carte cartePlanque = instance .getCarte();
			Bukkit.broadcastMessage("§b" + target.getPseudo() + " tire : " + cartePlanque.getNom() + " ["+cartePlanque.getVal() + " de " + cartePlanque.getCouleur() + "]");
			instance.defausse(cartePlanque);
			if (cartePlanque.getCouleur() == Couleur.Coeur) {		
				instance.currentNbBang++;
				Bukkit.broadcastMessage("§a"+target.getPseudo() + " a besoin que d'un seul Raté..");
				BangController controller = BangController.getInstance();
				target.actionRecu = this;
				source.joueurAttaque = target;
				target.sourceAction = source;
				Player cible = controller.getPlayerServer(target);
				Inventory action = controller.actionInventory(source, target, getNom());
				cible.openInventory(action);
			} else {
				Bukkit.broadcastMessage("§a"+target.getPseudo() + " n'a pas contré slab avec une planque, il lui faut 2 Raté");
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
    	} else {
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
		BangController instance = BangController.getInstance();
		if (target.finAction == false && target.contreAction == false && !instance.slabBang) {
			if (source.getPerso().getClass() == SlabLeFlingueur.class) {
				System.out.println("Bang d'un slab");
				if (target.getPerso().getClass() == Jourdonnais.class) {
					Bukkit.broadcastMessage("§a"+target.getPseudo() + " reçoit un bang de " + source.getPseudo());
					Bukkit.broadcastMessage("§b" + target.getPseudo() + " utilise la capacité de Jourdonnais !");
					Carte cartePlanque = instance .getCarte();
					Bukkit.broadcastMessage("§b" + target.getPseudo() + " tire : " + cartePlanque.getNom() + " ["+cartePlanque.getVal() + " de " + cartePlanque.getCouleur() + "]");
					instance.defausse(cartePlanque);
					if (cartePlanque.getCouleur() == Couleur.Coeur) {											
						Bukkit.broadcastMessage("§a"+target.getPseudo() + " a besoin que d'un seul Raté..");
						instance.currentNbBang++;
						if (target.aPlanque) {
				    		Bukkit.broadcastMessage("§b" + target.getPseudo() + " a une planque !");
							Carte cartePlanque1 = instance .getCarte();
							Bukkit.broadcastMessage("§b" + target.getPseudo() + " tire : " + cartePlanque1.getNom() + " ["+cartePlanque1.getVal() + " de " + cartePlanque1.getCouleur() + "]");
							instance.defausse(cartePlanque1);
							if (cartePlanque1.getCouleur() == Couleur.Coeur) {
								instance.currentNbBang++;
								Bukkit.broadcastMessage("§a"+target.getPseudo() + " a contré slab avec sa capacité de jourdonnais et sa planque");
							} else {
								Bukkit.broadcastMessage("§a"+target.getPseudo() + " n'a pas contré slab avec une planque");
								BangController controller = BangController.getInstance();
								target.actionRecu = this;
								source.joueurAttaque = target;
								target.sourceAction = source;
								Player cible = controller.getPlayerServer(target);
								Inventory action = controller.actionInventory(source, target, getNom());
								cible.openInventory(action);
							}
				    	} else {
				    		System.out.println("Pas Planque");
							BangController controller = BangController.getInstance();
							target.actionRecu = this;
							source.joueurAttaque = target;
							target.sourceAction = source;
							Player cible = controller.getPlayerServer(target);
							Inventory action = controller.actionInventory(source, target, getNom());
							cible.openInventory(action);
				    	}
					} else {
						Bukkit.broadcastMessage("§a"+target.getPseudo() + " n'a pas contré slab avec sa capacité de jourdonnais");
						slab(source, target);
					}
				} else {
					slab(source,target);
				}
				
			} else {
				horsSlabEffet(source, target);
			}
						
		} else if (target.finAction == true && target.contreAction == false) {
			
			target.bang(source);
			target.tueur = source;
			if (target.getPerso().getClass() == ElGringo.class) {
				Player player = instance.getPlayerServer(target);
				player.closeInventory();
				Bukkit.broadcastMessage("§6El Gringo active sa capacité et pioche dans la main du joueur");		
				Carte carte = source.getRandomFromMain();
				target.pioche(carte);
				player.sendMessage("§dVous récupérez : " + carte.getNom() + " ["+carte.getVal() + " de " + carte.getCouleur() + "]" );
			}
		} else {
			Bukkit.broadcastMessage("§a"+ target.getPseudo() + " défausse un BANG et ne perd pas de point de vie");
		}
		
	}

    
	public ItemStack representation() {
		ItemStack item = new ItemStack(Material.FIREBALL);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Bang");
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