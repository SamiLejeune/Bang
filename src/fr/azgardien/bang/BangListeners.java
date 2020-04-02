package fr.azgardien.bang;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import fr.azgardien.cartes.Bang;
import fr.azgardien.cartes.Biere;
import fr.azgardien.cartes.Carte;
import fr.azgardien.cartes.Lunette;
import fr.azgardien.cartes.Magasin;
import fr.azgardien.cartes.Rate;
import fr.azgardien.roles.Personnage;

public class BangListeners implements Listener {

	private BangController controller;
	public BangListeners(BangController controller, JavaPlugin plugin) {
		this.controller = controller;
		this.controller.setPlugin(plugin);
	}

	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage("§a"+player.getName() + " s'est connecté !");
	}
	
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		Action action = event.getAction();
		ItemStack current = event.getItem();
		if(current == null) return;
		if (BangController.getInstance().getPlayers() == null) return;
		if (current.getType() == Material.COMPASS) {
			if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§bInteraction")) {
				Inventory interaction = this.controller.playerInventory(this.controller.getJoueur(player));
				player.openInventory(interaction);
			}
		} else if (current.getType() == Material.ARROW) {
			Carte c = BangController.getInstance().getJoueur(player).currentAction;
			if(c!= null) {
				Joueur source = BangController.getInstance().getJoueur(player);
				Joueur cible = BangController.getInstance().getJoueur(getTargetPlayer(player));
				if (cible != null) {
					if (c.getClass() == Bang.class) {
						if (BangController.getInstance().reach(source, cible)) {
							c.appliquerEffet(source,cible);				
						} else {
							source.pioche(c);
							player.sendMessage("§cVous n'avez pas la portée nécessaire pour Bang ce joueur");
						}
					}
					player.getInventory().remove(current);
					
				}	
			}
		}
		
	}
	
	private Inventory defausseInventory(Player player) {
		Inventory def = Bukkit.createInventory(null, 27, "§8Défausser");
		Joueur joueur = BangController.getInstance().getJoueur(player);
		int i = 0 ;
		for (Carte c : joueur.getMains()) {
			def.setItem(i++, c.representation());
		}
		for (Carte c : joueur.getPoses()) {
			if (!BangController.getInstance().estArme(c)) {
				def.setItem(i++, c.representation());
			}		
		}
		
		ItemStack retour = new ItemStack(Material.ARROW);
		ItemMeta retourD = retour.getItemMeta();
		retourD.setDisplayName("§6Retour");
		retourD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		retourD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		retour.setItemMeta(retourD);
		
		def.setItem(26, retour);
		return def;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getInventory();
		ItemStack current = event.getCurrentItem();
		
		if(current == null) return;
		BangController instance = BangController.getInstance();
		if (instance.getPlayers() == null) return;
		
		Joueur joueur = instance.getJoueur(player);
		
		if(instance.lanceurMagasin == null) {
			if (instance.currentJoueur != joueur && !inv.getName().equalsIgnoreCase("§8Action")) {
				player.sendMessage("§cCe n'est pas votre tour de jouer");
				player.closeInventory();
				event.setCancelled(true);
				return;
			}
		}
		
		Material type = current.getType();
		
		if (inv.getName().equalsIgnoreCase("§8Action")) {
			
			if(type == Material.BARRIER) {
				player.closeInventory();
			} else if (type == Material.PAPER) {
				
			} else {
				ItemStack item = inv.getItem(17);
				String actionCarte = item.getItemMeta().getDisplayName().substring(2);
				Carte sourceAction = instance.getType(actionCarte);
				System.out.println(sourceAction);
				Carte c = instance.isGameMaterial(type);
				System.out.println(c);
				if (sourceAction.carteQuiContre().getNom().equals(c.getNom())) {
					// on utilise la carte
					joueur.contreAction = true;
					joueur.finAction = true;
					c.appliquerEffet(null, joueur);	
					if (sourceAction.getClass() == Bang.class) {
						instance.currentNbBang++;
					}	
					joueur.defausse(c);			
					player.closeInventory();
					joueur.contreAction = false;
					joueur.finAction = false;
					joueur.currentAction = null;
					joueur.sourceAction = null;
					joueur.joueurAttaque = null;
				}
			}
			
			event.setCancelled(true);
		}
		
		if (inv.getName().equalsIgnoreCase("§8Interaction")) {
			
			//BLOQUER COMPLETEMENT ACTION SI PAS FINI MAIS PAS SI YA MAGASIN
			
			
				for (Joueur j : instance.getPlayers()) {
					if (j != joueur) {
						if (j.sourceAction != null) {
							player.sendMessage("§cAttendez les actions des autres joueurs");
							event.setCancelled(true);
							return;
						}
					}
				}
			
				
			if (BangController.getInstance().lanceurMagasin != null) {
				player.sendMessage("§cAttendez les actions des autres joueurs");
				event.setCancelled(true);
				return;
			}
			
			
			if (type == Material.PAPER || type == Material.DEAD_BUSH || type == Material.STAINED_GLASS_PANE) {
				//Nothing
			} else if (type == Material.ENDER_PORTAL_FRAME) {
				Inventory def = defausseInventory(player);
				player.openInventory(def);
				
			} else if (type == Material.BED) {
				if (joueur.getMains().size() > joueur.getVie()) {
					player.sendMessage("§cVous ne pouvez pas passer votre tour, régulez votre nombre de cartes");
				} else {
					instance.resetPartialPlateforme(joueur.getLocation());
					instance.currentJoueur = instance.nextJoueur();
					instance.currentJoueur.piocheTour();
					if (instance.doitMelanger()) {
						instance.melanger();						
					}
					instance.currentNbBang = 0;
					instance.affichageCurrent();
					player.closeInventory();
				}
				
			} else {
				Carte c = instance.isGameMaterial(type);
				if (c != null) {
					if (instance.isTargettableCarte(c)) {
						if (c.getClass() == Bang.class) {
							System.out.println(joueur.peutTirer());
							if (!joueur.peutTirer()) {
								player.sendMessage("§cVous êtes limité à un seul Bang par tour");
								event.setCancelled(true);
								return;
							}
						}
						ItemStack item = new ItemStack(Material.ARROW);
						ItemMeta itemD = item.getItemMeta();
						itemD.setDisplayName("§6Choix victime");
						itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
						itemD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						item.setItemMeta(itemD);
						player.getInventory().setItem(8, item);
						player.closeInventory();
						instance.getJoueur(player).currentAction = c;
					} else if (c.getClass() == Rate.class) {
						//Nothing
					}  else {
						c.appliquerEffet(instance.getJoueur(player),null);
					}
					if (instance.estArme(c) || c.getClass() == Biere.class || c.getClass() == Lunette.class ) {
						
					} else if (c.getClass() == Magasin.class) {
						joueur.defausse(c);
						
					} else {
						if (!instance.isTargettableCarte(c)) {
							player.closeInventory();
							joueur.defausse(c);
							player.openInventory(instance.playerInventory(joueur));
						} else {
							if (c.getClass() == Bang.class) {
								joueur.defausse(c);
							}
						}
					}
				}
			}
			
			
			
			event.setCancelled(true);
		}
		
		if (inv.getName().equalsIgnoreCase("§8Magasin")) {
			Carte c = instance.carteMagasin(type);
			if (c!= null) {
					Bukkit.broadcastMessage("§b"+joueur.getPseudo() + " prend " + c.getNom());
					joueur.pioche(c);
					instance.removeCarteMagasin(c);
					joueur.choixMagasin = true;
					if (instance.nextJoueurMagasin() != instance.lanceurMagasin) {
						player.closeInventory();
						instance.currentMagasin = instance.nextJoueurMagasin();
						instance.getPlayerServer(instance.currentMagasin).openInventory(instance.magasinInventory());
						Bukkit.broadcastMessage("§aAu tour de " + instance.currentMagasin.getPseudo() + " de choisir sa carte dans le magasin.");
					} else {
						instance.currentMagasin = null;
						instance.lanceurMagasin = null;
						
						instance.clearMagasin();
						System.out.println("On fait le tour du magasin");
						player.closeInventory();
					}
					
					
			}
			event.setCancelled(true);
		}
		
		if (inv.getName().equalsIgnoreCase("§8Défausser")) {
			
			if (type == Material.ARROW) {
				player.closeInventory();
				player.openInventory(instance.playerInventory(joueur));
			} else {
				Carte c = instance.isGameMaterial(type);
				if (c!= null) {
					if (joueur.getMains().size() <= joueur.getVie()) {
						player.sendMessage("§cVous ne pouvez pas défaussez");
					} else {
						Bukkit.broadcastMessage("§b"+joueur.getPseudo() + " défausse une carte " + c.getNom());
						joueur.defausse(c);
						player.closeInventory();
						player.openInventory(defausseInventory(player));		
					}
						
				}
				
			}
			event.setCancelled(true);
		}	
	}
	
	@EventHandler
	public void closeInventory(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		Inventory inv = event.getInventory();
		BangController instance = BangController.getInstance();
		Joueur j = instance.getJoueur(player);
		if (j != null) {
			if (inv.getName().equalsIgnoreCase("§8Action") && !j.finAction ) {
				j.finAction = true;
				j.contreAction = false;
				j.actionRecu.appliquerEffet(j.sourceAction, j);
				j.contreAction = false;
				j.finAction = false;
				j.currentAction = null;
				j.sourceAction = null;
				j.joueurAttaque = null;
			}
			
			if (inv.getName().equalsIgnoreCase("§8Magasin") && j.choixMagasin == false) {
				Carte random = instance.randomCarteMagasin();
				Bukkit.broadcastMessage("§b"+j.getPseudo() + " prend " + random.getNom());
				j.pioche(random);
				instance.removeCarteMagasin(random);
				if (instance.nextJoueurMagasin() != instance.lanceurMagasin) {
					instance.currentMagasin = instance.nextJoueurMagasin();
					instance.getPlayerServer(instance.currentMagasin).openInventory(instance.magasinInventory());
					Bukkit.broadcastMessage("§aAu tour de " + instance.currentMagasin.getPseudo() + " de choisir sa carte dans le magasin.");
				} else {
					instance.currentMagasin = null;
					instance.lanceurMagasin = null;
					
					instance.clearMagasin();
					System.out.println("On fait le tour du magasin");
					player.closeInventory();
				}
			}
		}
	}
	
    public static Player getTargetPlayer(final Player player) {
        return getTarget(player, player.getWorld().getPlayers());
    }
	
    public static <T extends Entity> T getTarget(final Entity entity,
            final Iterable<T> entities) {
        if (entity == null)
            return null;
        T target = null;
        final double threshold = 1;
        for (final T other : entities) {
            final Vector n = other.getLocation().toVector()
                    .subtract(entity.getLocation().toVector());
            if (entity.getLocation().getDirection().normalize().crossProduct(n)
                    .lengthSquared() < threshold
                    && n.normalize().dot(
                            entity.getLocation().getDirection().normalize()) >= 0) {
                if (target == null
                        || target.getLocation().distanceSquared(
                                entity.getLocation()) > other.getLocation()
                                .distanceSquared(entity.getLocation()))
                    target = other;
            }
        }
        return target;
    }
    
 
    @EventHandler
    public void disconnect(PlayerQuitEvent event) {
    	Player player = event.getPlayer();
    	
    	if (BangController.getInstance().getJoueur(player) != null && BangController.getInstance().startTask) {
    		Bukkit.broadcastMessage("§c"+player.getName() + " a quitté la partie ! Fin du jeu");
    		BangController.getInstance().startTask = false;
    		BangController.getInstance().tpFinish();
    	}
    }
	
}
