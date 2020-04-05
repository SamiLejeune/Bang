package fr.azgardien.bang;

import java.util.Random;

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
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.azgardien.cartes.Bang;
import fr.azgardien.cartes.Biere;
import fr.azgardien.cartes.Braquage;
import fr.azgardien.cartes.Carte;
import fr.azgardien.cartes.Couleur;
import fr.azgardien.cartes.CoupDeFoudre;
import fr.azgardien.cartes.Duel;
import fr.azgardien.cartes.Dynamite;
import fr.azgardien.cartes.Lunette;
import fr.azgardien.cartes.Magasin;
import fr.azgardien.cartes.Mustang;
import fr.azgardien.cartes.Planque;
import fr.azgardien.cartes.Prison;
import fr.azgardien.cartes.Rate;
import fr.azgardien.cartes.Gatling;
import fr.azgardien.cartes.Indiens;
import fr.azgardien.roles.CalamityJanet;
import fr.azgardien.roles.SlabLeFlingueur;

public class BangListeners implements Listener {

	private BangController controller;
	private JavaPlugin plugin;
	public BangListeners(BangController controller, JavaPlugin plugin) {
		this.controller = controller;
		this.controller.setPlugin(plugin);
		this.plugin = plugin;
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
		BangController instance = BangController.getInstance();
		if (instance.getPlayers() == null) return;
		if (current.getType() == Material.COMPASS) {
			if (current.getItemMeta().getDisplayName().equalsIgnoreCase("§bInteraction")) {
				Inventory interaction = this.controller.playerInventory(this.controller.getJoueur(player));
				player.openInventory(interaction);
			}
		} else if (current.getType() == Material.ARROW) {
			Carte c = instance.getJoueur(player).currentAction;
			if(c!= null) {
				Joueur source = instance.getJoueur(player);
				Joueur cible = instance.getJoueur(getTargetPlayer(player));
				if (cible != null) {
					if (source.getPerso().getClass() == CalamityJanet.class) {
						if (c.getClass() == Bang.class || c.getClass() == Rate.class) {
							Bang bang = new Bang("", Couleur.Trèfle);
							if (instance.reach(source, cible)) {
								bang.appliquerEffet(source,cible);				
							} else {
								source.pioche(c);
								player.sendMessage("§cVous n'avez pas la portée nécessaire pour Bang ce joueur");
								instance.supprimeDernierCarteDefausse();
							}
						}
					}else {
						if (c.getClass() == Bang.class) {
							if (instance.reach(source, cible)) {
								c.appliquerEffet(source,cible);				
							} else {
								source.pioche(c);
								player.sendMessage("§cVous n'avez pas la portée nécessaire pour Bang ce joueur");
								instance.supprimeDernierCarteDefausse();
							}
						}
					}
					if (c.getClass() == Braquage.class) {

						if (instance.peutBraquage(source, cible)) {
							player.openInventory(instance.visionJoueur(cible,"Braquage"));
							instance.defausse(c);
						} else {
							player.sendMessage("§cVous ne pouvez pas le braquer");
						}

					} else if (c.getClass() == CoupDeFoudre.class) {
						player.openInventory(instance.visionJoueur(cible,"Coup de foudre"));
						instance.defausse(c);
					} else if (c.getClass() == Duel.class) {
						Bukkit.broadcastMessage("§a"+source.getPseudo() + " duel " + cible.getPseudo());
						c.appliquerEffet(source, cible);
						instance.duelCurrentJoueur = cible;
						source.defausse(c);
						source.sourceAction = cible;
						instance.duelFin = false;
					} else if (c.getClass() == Prison.class){
						c.appliquerEffet(source, cible);
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
		System.out.println(instance.defausses);
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
			ItemStack actionSource = inv.getItem(16);
			String nomSource = actionSource.getItemMeta().getDisplayName().substring(2);
			ItemStack item = inv.getItem(17);
			String actionCarte = item.getItemMeta().getDisplayName().substring(2);

			if(type == Material.BARRIER) {
				BangController.getInstance().duelPremature = true;
				player.closeInventory();
				

			} else if (type == Material.PAPER) {

			} else {

				Joueur j = BangController.getInstance().getJoueur(nomSource);
				Carte sourceAction = instance.getType(actionCarte);
				Carte c = instance.isGameMaterial(type);

				boolean correct = false;		
				if (joueur.getPerso().getClass() == CalamityJanet.class) {
					if (sourceAction.getClass() == Bang.class ) {
						if (c.getNom().equals("Bang")) {
							correct = true;
						} else {
							correct = sourceAction.carteQuiContre().getNom().equals(c.getNom());
						}
					} else if (sourceAction.getClass() == Duel.class) {
						if (c.getNom().equals("Rate")) {
							correct = true;
						} else {
							correct = sourceAction.carteQuiContre().getNom().equals(c.getNom());
						}
					} else if (sourceAction.getClass() == Indiens.class) {
						if (c.getNom().equals("Rate")) {
							correct = true;
						} else {
							correct = sourceAction.carteQuiContre().getNom().equals(c.getNom());
						}
					} else if (sourceAction.getClass() == Gatling.class) {
						if (c.getNom().equals("Bang")) {
							correct = true;
						} else {
							correct = sourceAction.carteQuiContre().getNom().equals(c.getNom());
						}
					} 
					
					else {
						correct = sourceAction.carteQuiContre().getNom().equals(c.getNom());
					}
				} else {
					correct = sourceAction.carteQuiContre().getNom().equals(c.getNom());
				}
				if (correct) {
					if (sourceAction.getClass() == Duel.class) {
						Player joueurFace = BangController.getInstance().getPlayerServer(j);
						Bukkit.broadcastMessage("§b"+joueur.getPseudo() + " pose un bang");
						joueurFace.openInventory(BangController.getInstance().actionInventory(joueur, j, "Duel"));
						instance.duelCurrentJoueur = j;
						joueur.defausse(c);
						player.closeInventory();
					} else {
						// on utilise la carte
						joueur.contreAction = true;
						joueur.finAction = true;
						if (instance.consommeRate == true) {
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
						} else {
							if (sourceAction.getClass() == Bang.class) {
								instance.currentNbBang++;
							}
							Bukkit.broadcastMessage("§b" + j.getPseudo() + " défausse un Raté..");
							instance.quitVolontaire = true;
							joueur.defausse(c);			
							instance.slabContre++;
							player.closeInventory();
							joueur.contreAction = false;
							joueur.finAction = false;
							joueur.currentAction = null;
							joueur.sourceAction = null;
							joueur.joueurAttaque = null;
							if (instance.dernierSlabRate == false) {
								instance.dernierSlabRate = true;
							}
							if (instance.slabBang == true) {
								//AFFICHAGE 2 FOIS	
								Inventory action = controller.actionInventory(instance.getJoueur(nomSource), joueur, actionCarte);
								player.openInventory(action);
								instance.slabBang = false;	
							}

						}



					}

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
					if (instance.currentJoueur.aDynamite == true) {
						Bukkit.broadcastMessage("§bPioche d'une carte pour la dynamite..");
						Carte carteDynamite = instance.getCarte();
						Bukkit.broadcastMessage("§b" + instance.currentJoueur.getPseudo() + " tire : " + carteDynamite.getNom() + " ["+carteDynamite.getVal() + " de " + carteDynamite.getCouleur() + "]");
						instance.defausse(carteDynamite);
						Dynamite dyn = new Dynamite("", Couleur.Carreau);
						Carte poseDynamite = instance.currentJoueur.getFromPose(dyn.representation().getType());
						instance.resetPartialPlateforme(instance.currentJoueur.getLocation());
						if (instance.faitExploser(carteDynamite)) {
							instance.defausse(poseDynamite);
							Bukkit.broadcastMessage("§bLa dynamite explose et le joueur perd 3 points de vie");
							instance.currentJoueur.damage(3);
							instance.removeDynamite(instance.currentJoueur);
							instance.currentJoueur.aDynamite = false;

						} else {
							Bukkit.broadcastMessage("§bLa dynamite n'explose pas et passe au prochain joueur");
							instance.removeDynamite(instance.currentJoueur);
							instance.setDynamite(instance.nextDynamite());
							instance.nextDynamite().posePrison(poseDynamite);
							instance.nextDynamite().aDynamite = true;
						}
					}
					if (!instance.currentJoueur.estEnPrison) {
						instance.currentJoueur.piocheTour();
					} else {
						do {
							Bukkit.broadcastMessage("§b" + instance.currentJoueur.getPseudo() + " est en prison !");
							Carte cartePrison = instance.getCarte();
							Bukkit.broadcastMessage("§b" + instance.currentJoueur.getPseudo() + " tire : " + cartePrison.getNom() + " ["+cartePrison.getVal() + " de " + cartePrison.getCouleur() + "]");
							Prison prison = new Prison("", Couleur.Carreau);
							Carte posePrison = instance.currentJoueur.getFromPose(prison.representation().getType());
							instance.defausse(posePrison);
							instance.defausse(cartePrison);
							instance.resetPartialPlateforme(instance.currentJoueur.getLocation());
							instance.resetPrison(instance.currentJoueur.getLocation());
							instance.currentJoueur.estEnPrison = false;
							if (cartePrison.getCouleur() != Couleur.Coeur) {											
								instance.currentJoueur = instance.nextJoueur();
								if (!instance.currentJoueur.estEnPrison) {
									instance.currentJoueur.piocheTour();
								}
							} else {
								Bukkit.broadcastMessage("§b" + instance.currentJoueur.getPseudo() + " sort de prison !");
								instance.currentJoueur.piocheTour();
							}
							if (instance.currentJoueur.aDynamite == true) {
								Bukkit.broadcastMessage("§bPioche d'une carte pour la dynamite..");
								Carte carteDynamite = instance.getCarte();
								Bukkit.broadcastMessage("§b" + instance.currentJoueur.getPseudo() + " tire : " + carteDynamite.getNom() + " ["+carteDynamite.getVal() + " de " + carteDynamite.getCouleur() + "]");
								instance.defausse(carteDynamite);
								instance.resetPartialPlateforme(instance.currentJoueur.getLocation());
								if (instance.faitExploser(carteDynamite)) {
									Bukkit.broadcastMessage("§bLa dynamite explose et le joueur perd 3 points de vie");
									instance.currentJoueur.damage(3);
									instance.removeDynamite(instance.currentJoueur);
									instance.currentJoueur.aDynamite = false;
								} else {
									Bukkit.broadcastMessage("§bLa dynamite n'explose pas et passe au prochain joueur");
									instance.removeDynamite(instance.currentJoueur);
									instance.setDynamite(instance.nextDynamite());
									instance.nextDynamite().aDynamite = true;
								}
							}

						} while(instance.currentJoueur.estEnPrison == true);															
					}

					Player pl = instance.getPlayerServer(instance.currentJoueur);
					pl.closeInventory();
					pl.openInventory(instance.playerInventory(instance.currentJoueur));
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
						if (instance.getJoueur(player).getPerso().getClass()== (CalamityJanet.class)) {
							if (c.getClass() == Bang.class || c.getClass() == Rate.class) {
								if (!joueur.peutTirer()) {
									player.sendMessage("§cVous êtes limité à un seul Bang par tour");
									event.setCancelled(true);
									return;
								}
							}
							BangController.getInstance().currentNbBang++;
							ItemStack item = new ItemStack(Material.ARROW);
							ItemMeta itemD = item.getItemMeta();
							itemD.setDisplayName("§6Choix victime");
							itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
							itemD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
							item.setItemMeta(itemD);
							player.getInventory().setItem(8, item);
							player.closeInventory();
							instance.getJoueur(player).currentAction = c;
							joueur.defausse(c);
						}
					}  else {
						c.appliquerEffet(instance.getJoueur(player),null);
					}


					if (instance.estArme(c) || c.getClass() == Biere.class || c.getClass() == Lunette.class ||c.getClass() == Rate.class ||c.getClass() == Mustang.class || c.getClass() == Planque.class ||c.getClass() == Dynamite.class ) {

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

		if (inv.getName().equalsIgnoreCase("§8Vision")) {

			if (type == Material.ARROW) {
				player.closeInventory();
				player.openInventory(instance.playerInventory(joueur));
			} else if (type == Material.EMERALD_BLOCK) {
				instance.quitVisionVolontaire = true;
				player.closeInventory();
				ItemStack item = inv.getItem(34);
				String actionCarte = item.getItemMeta().getDisplayName().substring(2);
				Carte sourceAction = instance.getType(actionCarte);
				ItemStack item2 = inv.getItem(35);
				String name = item2.getItemMeta().getDisplayName().substring(2);
				Joueur cible = BangController.getInstance().getJoueur(name);

				Inventory pick = BangController.getInstance().pickJoueur(cible, actionCarte);
				if (sourceAction.getClass() == Braquage.class) {
					joueur.joueurBraque = cible;
					Bukkit.broadcastMessage("§a"+joueur.getPseudo() + " braque " + cible.getPseudo());
					instance.coupDeFoudreJoueur = cible;
				} else {
					joueur.joueurBraque = cible;
					Bukkit.broadcastMessage("§a"+joueur.getPseudo() + " va coup de foudre " + cible.getPseudo());
					instance.coupDeFoudreJoueur = cible;
				}

				player.openInventory(pick);
			}

			event.setCancelled(true);
		}

		if (inv.getName().equalsIgnoreCase("§8Braquage") ) {
			if (type == Material.ENDER_PEARL) {
				Carte carte = joueur.joueurBraque.getRandomFromMain();
				joueur.pioche(carte);
				instance.coupDeFoudreJoueur = null;
			} else {
				Carte carte = joueur.joueurBraque.getFromPose(type);
				joueur.pioche(carte);
				joueur.joueurBraque.updateRemoveTargettable(carte);
				instance.coupDeFoudreJoueur = null;

			}

			Braquage braquage = new Braquage("", Couleur.Carreau);
			joueur.getFromMain(braquage.representation().getType());
			joueur.joueurBraque = null;

			player.closeInventory();
			player.openInventory(BangController.getInstance().playerInventory(joueur));
			event.setCancelled(true);

		}

		if (inv.getName().equalsIgnoreCase("§8Coup de foudre")) {
			if (type == Material.ENDER_PEARL) {
				Carte carte = joueur.joueurBraque.getRandomFromMain();
				Bukkit.broadcastMessage("§b"+joueur.joueurBraque.getPseudo() + " perd " + carte.getNom() );
				instance.defausse(carte);
				instance.coupDeFoudreJoueur = null;
			} else {
				Carte carte = joueur.joueurBraque.getFromPose(type);
				joueur.joueurBraque.updateRemoveTargettable(carte);
				Bukkit.broadcastMessage("§b"+joueur.joueurBraque.getPseudo() + " perd " + carte.getNom() );
				instance.defausse(carte);
				instance.coupDeFoudreJoueur = null;
			}

			CoupDeFoudre foudre = new CoupDeFoudre("", Couleur.Carreau);
			joueur.getFromMain(foudre.representation().getType());
			joueur.joueurBraque = null;
			player.closeInventory();

			player.openInventory(BangController.getInstance().playerInventory(joueur));
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void closeInventory(InventoryCloseEvent event) {
		if (BangController.getInstance().getPlayers() == null) return;
		final Player player = (Player) event.getPlayer();
		Inventory inv = event.getInventory();
		BangController instance = BangController.getInstance();
		final Joueur j = instance.getJoueur(player);
		if (j != null) {	
			if (inv.getName().equals("§8Vision")){
				if (instance.quitVisionVolontaire == false) {
					instance.supprimeDernierCarteDefausse();
				} 		
			} else if (inv.getName().equals("§8Coup de foudre")) {
				if (instance.coupDeFoudreJoueur != null) {
					Carte random = instance.coupDeFoudreJoueur.getRandomFromMainEtPose();
					instance.currentJoueur.joueurBraque.updateRemoveTargettable(random);
					Bukkit.broadcastMessage("§b"+instance.currentJoueur.joueurBraque.getPseudo() + " perd " + random.getNom() );
					instance.defausse(random);
					instance.coupDeFoudreJoueur = null;
					CoupDeFoudre foudre = new CoupDeFoudre("", Couleur.Carreau);
					instance.currentJoueur.getFromMain(foudre.representation().getType());
					instance.currentJoueur.joueurBraque = null;
					player.closeInventory();

					player.openInventory(BangController.getInstance().playerInventory(instance.currentJoueur));
				}
				instance.coupDeFoudreJoueur = null;
				instance.quitVisionVolontaire = false;
			} else if (inv.getName().equals("§8Braquage")) {
				if (instance.coupDeFoudreJoueur != null) {
					Carte random = instance.coupDeFoudreJoueur.getRandomFromMainEtPose();
					instance.currentJoueur.pioche(random);
					instance.currentJoueur.joueurBraque.updateRemoveTargettable(random);
					instance.coupDeFoudreJoueur = null;
					Braquage braquage = new Braquage("", Couleur.Carreau);
					instance.currentJoueur.getFromMain(braquage.representation().getType());
					instance.currentJoueur.joueurBraque = null;
					player.sendMessage("§bVous récupérez : " + random.getNom());
					player.closeInventory();
					player.openInventory(BangController.getInstance().playerInventory(instance.currentJoueur));
				}
				instance.coupDeFoudreJoueur = null;
				instance.quitVisionVolontaire = false;
			}
			if (instance.duelFin == true) {	
				if (inv.getName().equalsIgnoreCase("§8Action") && (instance.slabBang == true || instance.dernierSlabRate == true)) {
					if (instance.quitVolontaire == false) {
					//	Bukkit.broadcastMessage("§b" + j.getPseudo() + " a quitté et se prend le bang!");
						j.finAction = true;
						j.contreAction = false;
						j.contreAction = false;
						j.finAction = false;
						j.currentAction = null;
						j.sourceAction = null;
						j.joueurAttaque = null;
						j.getPerso().limiteBang = 1;
						j.bang(j.sourceAction);
						instance.resetSlabAttribute();
					} else if (j.finAction) {
						if (instance.slabBang == false) {
							Bukkit.broadcastMessage("§a"+ j.getPseudo() + " a réussi à contrer slab le flingueur");
							instance.resetSlabAttribute();
						} 
					}				
					instance.quitVolontaire = false;
				} else if (inv.getName().equalsIgnoreCase("§8Action") && !j.finAction) {
					j.finAction = true;
					j.contreAction = false;
					j.actionRecu.appliquerEffet(j.sourceAction, j);
					j.contreAction = false;
					j.finAction = false;
					j.currentAction = null;
					j.sourceAction = null;
					j.joueurAttaque = null;
					j.getPerso().limiteBang = 1;
					instance.quitVolontaire = false;
				} else if (instance.currentMagasin != null) {
					if (inv.getName().equalsIgnoreCase("§8Magasin") && j.choixMagasin == false) {
						try {
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
								player.closeInventory();
							}
						} catch(Exception e) {
							e.printStackTrace();
						}

					}
				} 

			} else {
				if (inv.getName().equals("§8Action")) {
					if (instance.duelCurrentJoueur == j) {
						ItemStack actionSource = inv.getItem(16);
						String nomSource = actionSource.getItemMeta().getDisplayName().substring(2);
						Joueur jAction = BangController.getInstance().getJoueur(nomSource);
						j.bang(jAction);
						if (BangController.getInstance().currentNbBang > 0) BangController.getInstance().currentNbBang--;

						instance.duelPremature = false;
						instance.duelFin = true;
						instance.duelCurrentJoueur = null;
						instance.resetAction();
					}
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
		if (BangController.getInstance().getPlayers() != null) {
			if (BangController.getInstance().getJoueur(player) != null && BangController.getInstance().startTask) {
				Bukkit.broadcastMessage("§c"+player.getName() + " a quitté la partie ! Fin du jeu");
				BangController.getInstance().startTask = false;
				BangController.getInstance().tpFinish();
			}
		}

	}





}
