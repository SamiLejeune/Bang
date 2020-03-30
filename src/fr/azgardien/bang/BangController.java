package fr.azgardien.bang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.azgardien.cartes.Carte;
import fr.azgardien.roles.Personnage;
import fr.azgardien.roles.PersonnageFactory;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class BangController implements CommandExecutor {

	private static BangController INSTANCE = new BangController();

	public static BangController getInstance() {
		return INSTANCE;
	}

	public ArrayList<Carte> pioche;
	
	private BangController() {}

	private boolean start;
	private Location[] spots;
	private Role[] current;
	private ArrayList<Joueur> players;
	private ArrayList<Personnage> personnages;
	private World world;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (sender.getServer().getWorld("world").getPlayers().size() > 7) {
			Bukkit.broadcastMessage("§cMax 7 joueurs");
			return false;
		}
		this.world = sender.getServer().getWorld("world");
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("start")) {
				Bukkit.broadcastMessage("§eLa partie débute ! ");	
				init(sender);
				return true;
			}
			
			if (cmd.getName().equalsIgnoreCase("personnages")) {
				
				if (start && allAreReady()) {
					p.sendMessage("§eLes personnages des différents joueurs :  ");
					for (Joueur j : players) {
						p.sendMessage("§b"+j.getPseudo() + " §9: " + j.getPerso() );
					}
					return true;
				} else {
					p.sendMessage("§c La partie n'a pas commencé ou tous les joueurs n'ont pas choisi le personnage");
					return false;
				}
				
			}
			if (cmd.getName().equalsIgnoreCase("choix")) {
				
				if (args.length == 0 || args.length > 1) {
					p.sendMessage("§c Il faut un argument (/choix 1 ou /choix 2)");
					return false;
				}
				
				try {
					int c = Integer.parseInt(args[0]);
					Joueur j = getJoueur(p);
					if (c == 1 || c ==2) {
						if (!start || j.aChoisi()) {
							p.sendMessage("§cLa partie n'a pas commencé ou vous avez déjà choisi ! ");
							return false;
						} else {							
							setPerso(p, c-1);
							p.sendMessage("§bVous avez choisi : " + j.getPerso());
							j.ready();
							Bukkit.broadcastMessage("§a-> " + p.getName() + " §b est prêt");
							if (allAreReady()) {
								Bukkit.broadcastMessage("§bTout le monde est prêt");
								start();
							}
							
							return true;
						}
					} else {
						p.sendMessage("§c Il faut un argument de type 1 ou 2");
						return false;
					}
				} catch(NumberFormatException e) {
					p.sendMessage("§c L'argument doit être un chiffre");
				}
				
				
			}
		}
		return false;
	}

	public void start() {
		initLife();
		giveCompass();
	}
	
	public void initLife() {
		for (Joueur j : players) {
			j.init();
		}
	}
	
	public void giveCompass() {
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassD = compass.getItemMeta();
		compassD.setDisplayName("§bInteraction");
		compassD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		compassD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		compass.setItemMeta(compassD);
		
		for (Joueur j : players) {
			Player p = getPlayerServer(j);
			p.getInventory().setItem(0, compass);
		}
	}
	
	public int getDistance(Joueur source, Joueur cible) {
		//distance de base + distance de base de la cible  
		return Math.abs(getIndex(cible) - getIndex(source)) + cible.getPerso().getDistance();
	}
	
	private int getIndex(Joueur joueur) {
		int i = 0;
		for (Joueur j : players) {
			if (j == joueur) {
				return i;
			}
			i++;
		}
		return -12;
	}
	
	public List<String> getAllDistance(Joueur source) {
		ArrayList<String> distance = new ArrayList<String>(); 
		distance.add("§dDistance maximum : " + source.getVisionUp());
		distance.add("");
		for (Joueur j : players) {
			if (j != source) {
				distance.add("§bDistance de §9" + j.getPseudo() + " §b: " + getDistance(source, j));
			}
		}
		return distance;
	}
	
	public Inventory playerInventory(Joueur j) {
		
		Inventory inv = Bukkit.createInventory(null, 54, "§8Interaction");
		ItemStack info = new ItemStack(Material.PAPER);
		ItemMeta infoD = info.getItemMeta();
		infoD.setDisplayName("§eMes informations");
		infoD.setLore(Arrays.asList("§bRôle : §9" + j.getRole(),"§bPersonnage : §9" + j.getPerso(),"§bBalles restantes : §9" + j.getVie()));
		infoD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		infoD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		info.setItemMeta(infoD);
		
		ItemStack distance = new ItemStack(Material.FISHING_ROD);
		ItemMeta distanceD = distance.getItemMeta();
		distanceD.setDisplayName("§eLes distances");
		distanceD.setLore(getAllDistance(j));
		distanceD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		distanceD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		distance.setItemMeta(distanceD);
		
		inv.setItem(0, info);
		inv.setItem(8,distance);
		return inv;
	}
	
	private boolean allAreReady() { 
		for (Joueur p : players) {
			if (!p.aChoisi()) {
				return false;
			}
		}	
		return true;
	}
	
	private void init(CommandSender sender) {
		start = true;
		
		//init players spots;
		this.spots = new Location[7];
		spots[0] = new Location(sender.getServer().getWorld("world"), 183.5, 132, 224.5, 0, 10);
		spots[1] = new Location(sender.getServer().getWorld("world"), 178.5, 132, 226.5, -40, 10);
		spots[2] = new Location(sender.getServer().getWorld("world"), 177.5, 132, 230.5, -90, 10);
		spots[3] = new Location(sender.getServer().getWorld("world"), 180.5, 132, 235.5, -140, 10);
		spots[4] = new Location(sender.getServer().getWorld("world"), 185.5, 132, 236.5, 170, 10);
		spots[5] = new Location(sender.getServer().getWorld("world"), 189.5, 132, 232.5, 110, 10);
		spots[6] = new Location(sender.getServer().getWorld("world"), 188.5, 132, 226.5, 50, 10);
		shuffleLocations();

		
		
		//init roles joueurs;
		this.current = getRoles(sender.getServer().getWorld("world").getPlayers().size());
		shuffleRoles();

		//init arraylist players;
		this.players = new ArrayList<Joueur>();

		int i = 0;
		for (Player p : sender.getServer().getWorld("world").getPlayers()) {
			p.teleport(spots[i]);
			Joueur j = new Joueur(p.getName());
			j.setRole(current[i]);
			p.sendMessage("§bVous êtes : " + current[i]);
			i++;
			this.players.add(j);
		}

		//clear players inventory
		for (Player p : sender.getServer().getWorld("world").getPlayers()) {
			p.getInventory().clear();
		}
		
		//init arraylist roles;
		this.personnages = PersonnageFactory.FACTORY.allPersonnages("personnages.txt");
		int perso = 0;
		for (Player p : sender.getServer().getWorld("world").getPlayers()) {
			Personnage p1 = this.personnages.get(perso);
			Personnage p2 = this.personnages.get(perso+1);
			String message = "§eChoix personnage\n   §cPersonnage 1\n   §a"+p1.getNom()+"\n   §b"+p1.getVie()+ " balles" + "\n   §cDescription :\n   §d"+p1.getDescription();
			message+= "\n\n   §cPersonnage 2\n   §a"+p2.getNom()+"\n   §b"+p2.getVie()+ " balles" + "\n   §cDescription :\n   §d"+p2.getDescription();
			p.sendMessage(message);
			p.sendMessage("§e/choix 1 ou /choix 2 pour choisir le personnage");
			setChoixJoueurs(p, p1, p2);
			perso+=2;
		}
		
		//init pioche;
		
		

	}


	private void setChoixJoueurs(Player p, Personnage p1, Personnage p2) {
		for (Joueur j : players ) {
			if (j.getPseudo().equals(p.getName())) {
				j.setPersoChoix(p1, p2);

			}
		}
	}
	
	private void setPerso(Player p , int choix) {
		Joueur j = getJoueur(p);
		j.setPerso(j.getChoix()[choix]);
	}
	
	public Joueur getJoueur(Player p) {
		for (Joueur j : players ) {
			if (j.getPseudo().equals(p.getName())) {
				return j;
			}
		}
		return null;
	}
	
	public Player getPlayerServer(Joueur j) {
		for (Player p : this.world.getPlayers() ) {
			if (j.getPseudo().equals(p.getName())) {
				return p;
			}
		}
		return null;
	}

	private void shuffleRoles() {
		for (int i = 0 ; i < this.current.length ; i++) {
			Random random = new Random();
			int rdm = random.nextInt(this.current.length);
			Role temp = this.current[rdm];
			this.current[rdm] = this.current[i];
			this.current[i] = temp;
		}
	}

	private void shuffleLocations() {
		for (int i = 0 ; i < this.spots.length ; i++) {
			Random random = new Random();
			int rdm = random.nextInt(this.spots.length);
			Location temp = this.spots[rdm];
			this.spots[rdm] = this.spots[i];
			this.spots[i] = temp;
		}
	}

	private Role[] getRoles(int nbJoueur) {
		Role[] role2Joueurs = new Role[] {Role.Sherif,Role.Renegat};
		Role[] role3Joueurs = new Role[] {Role.Sherif,Role.Renegat,Role.HorsLaLoi};
		Role[] role4Joueurs = new Role[] {Role.Sherif,Role.Renegat,Role.HorsLaLoi,Role.HorsLaLoi};
		Role[] role5Joueurs = new Role[] {Role.Sherif,Role.Renegat,Role.HorsLaLoi,Role.HorsLaLoi,Role.Adjoint};
		Role[] role6Joueurs = new Role[] {Role.Sherif,Role.Renegat,Role.HorsLaLoi,Role.HorsLaLoi,Role.HorsLaLoi,Role.Adjoint};
		Role[] role7Joueurs = new Role[] {Role.Sherif,Role.Renegat,Role.HorsLaLoi,Role.HorsLaLoi,Role.HorsLaLoi,Role.Adjoint,Role.Adjoint};

		Role[] role;
		switch(nbJoueur) {
		case 2 : 
			role =  role2Joueurs;
			break;
		case 3 : 
			role = role3Joueurs;
			break;
		case 4 : 
			role = role4Joueurs;
			break;
		case 5 : 
			role = role5Joueurs;
			break;
		case 6 : 
			role = role6Joueurs;
			break;
		case 7 : 
			role = role7Joueurs;
			break;
		default : 
			role = role7Joueurs;
			break;

		}

			int nbHorsLaLoi=0, nbAdjoints=0;
			for (Role r : role) {
				if (r == Role.HorsLaLoi) nbHorsLaLoi++;
				if (r == Role.Adjoint) nbAdjoints++;
			}
			Bukkit.broadcastMessage("§aComposition de la partie : ");
			Bukkit.broadcastMessage("  §bShérif : 1");
			Bukkit.broadcastMessage("  §bHors-la-loi : "+ nbHorsLaLoi);
			Bukkit.broadcastMessage("  §bAdjoint : " + nbAdjoints);
			Bukkit.broadcastMessage("  §bRenégat : 1");
			return role;
		
	}



	public ArrayList<Joueur> getPlayers() {
		return players;
	}

	public Personnage[] choixPersonnage(String joueur) {
		System.out.println(joueur);
		for (Joueur p : players) {
			System.out.println("Verif : " + p.getPseudo() );
			if (p.getPseudo().equalsIgnoreCase(joueur)){
				return p.getChoix();
			}
		}
		return null;
	}

}
