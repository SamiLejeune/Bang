package fr.azgardien.bang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import fr.azgardien.cartes.Carabine;
import fr.azgardien.cartes.Carte;
import fr.azgardien.cartes.CartesFactory;
import fr.azgardien.cartes.Remington;
import fr.azgardien.cartes.Schofield;
import fr.azgardien.cartes.Volcanic;
import fr.azgardien.cartes.Winchester;
import fr.azgardien.roles.Personnage;
import fr.azgardien.roles.PersonnageFactory;

public class BangController implements CommandExecutor {

	private static BangController INSTANCE = new BangController();

	public static BangController getInstance() {
		return INSTANCE;
	}
	private BangController() {}
	
	public ArrayList<Carte> allCartes;
	public ArrayList<Carte> pioche;
	private boolean start;
	public boolean startTask;
	private Location[] spots;
	private Location[] spotsFixe;
	private Role[] current;
	public ArrayList<Joueur> players;
	private ArrayList<Personnage> personnages;
	private World world;
	public Joueur currentJoueur;
	private JavaPlugin plugin;
	public int currentNbBang;
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
				BukkitTask task = new MortTask(plugin).runTaskTimer(plugin, 10, 20);
				init(sender);
				return true;
			}
					
			if (cmd.getName().equalsIgnoreCase("personnages")) {
				
				if (start && allAreReady()) {
					p.sendMessage("§eLes personnages des différents joueurs : ");
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
		startTask = true;
		afficheSherif();
		giveCompass();
		distribution();
		game();
	}
	
	public Joueur nextJoueur() {
		for (int i = 0 ; i < this.players.size() ; i++) {
			if (this.players.get(i) == this.currentJoueur) {
				if (i == 0) {
					Joueur j = this.players.get(this.players.size()-1); 
					Bukkit.broadcastMessage("§aAu tour de " + j.getPseudo() + " de jouer.");
					return j;
				} else {
					Joueur j = this.players.get(i-1);
					Bukkit.broadcastMessage("§aAu tour de " + j.getPseudo() + " de jouer.");
					return j;
				}
				
			}
		}
		return null;
	}
	
	public void game() {
		this.currentJoueur = sherif();
		currentJoueur.piocheTour();
		affichageCurrent();
	}
	
	public void tpFinish() {
		for (Joueur j : players) {
			Player p = getPlayerServer(j);
			p.teleport(new Location(world, 183.5, 131, 229.5 ,88,0));
		}
	}
	
	public void exitPlayer(Joueur j) {
		int idx = -1;
		for (int i = 0 ; i < this.players.size() ; i++) {
			if (j == this.players.get(i)) {
				idx = i;
			}
		}
		Bukkit.broadcastMessage("§aLe joueur " + j.getPseudo() + " est éliminé ! Il était " + j.getRole());
		this.players.remove(idx);
		this.getPlayerServer(j).getInventory().clear();
		this.getPlayerServer(j).teleport(new Location(world, 183.5, 131, 229.5 ,88,0));
		
		if (finish()) {
			annonceFin();
			tpFinish();
		}
	}
	
	public void annonceFin() {
		
		if (renegatWin()) {
			Bukkit.broadcastMessage("§aLe renégat a gagné !");
		} else if (horsLaLoiWin()) {
			Bukkit.broadcastMessage("§aLes hors-la-loi ont gagné !");		
		} else {
			Bukkit.broadcastMessage("§aLe shérif et ses adjoints gagnent!");
		}
	}
	
	public void affichageCurrent() {
		Location loc = currentJoueur.getLocation().clone();
		loc.setY(loc.getY()+3);
		Block b = loc.getBlock();
		b.setType(Material.SLIME_BLOCK);
	}
	
	private void distribution() {
		for (Joueur j : this.players) {
			for (int i = 0 ; i < j.getVie() ; i++) {
				j.pioche(getCarte());
			}
		}
	}
	
	public Joueur sherif() {
		for (Joueur j : this.players) {
			if (j.getRole() == Role.Sherif ) {
				return j;
			}
		}
		return null;
	}
	
	public boolean finish() {
		return sherifWin() || horsLaLoiWin() || renegatWin();
	}
	
	
	public boolean sherifWin() {
		for (Joueur j : this.players) {
			if (j.getRole() == Role.HorsLaLoi || j.getRole() == Role.Renegat ) {
				return false;
			}		
		}
		return true;
	}
	
	public boolean horsLaLoiWin() {
		boolean sherifMort = true;
		for (Joueur j : this.players) {
			if (j.getRole() == Role.Sherif ) {
				sherifMort = false;
			}		
		}
		
	
		return sherifMort && !renegatWin();
	}
	
	public boolean renegatWin() {
		return this.players.size() == 1 && this.players.get(0).getRole() == Role.Renegat;
	}

	private void afficheSherif() {
		Joueur sherif = sherif();
		Bukkit.broadcastMessage("§e" + sherif.getPseudo() + " est le Shérif ! ");
		Location loc = sherif.getLocation().clone();
		loc.setY(loc.getY()-1);
		Block b = loc.getBlock();
		b.setType(Material.GOLD_BLOCK);
		
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
	public List<String> getAllDistance(Joueur source) {
		ArrayList<String> distance = new ArrayList<String>(); 
		distance.add("§dDistance maximum : " + (source.getVisionUp() + source.getDistance()));
		distance.add("");
		for (Joueur j : players) {
			if (j != source) {
				int dist = (getMinimumDistance(players, source, j) + j.getPerso().getDistance());
				distance.add("§bDistance de §9" + j.getPseudo() + " §b: " + dist );
			}
		}
		return distance;
	}
	
	@SuppressWarnings("unchecked")
	public static int getMinimumDistance(ArrayList<Joueur> list, Joueur source, Joueur cible) {
		ArrayList<Joueur> clone = (ArrayList<Joueur>) list.clone();
		clone.addAll(clone);
		return Math.min(getDistance(clone, source, cible), getDistance(clone, cible, source));
	}
	
	public static int getDistance(ArrayList<Joueur> list, Joueur source, Joueur cible) {
		int distance = 0;
		//System.out.println(list);
		for (int i = 0 ; i < list.size() ; i ++) {
			if (list.get(i) != source) {
				if (list.get(i) == cible) {
					if (distance == 0) {
						distance = Math.abs(i - list.indexOf(source));
					} else {
						int compare = Math.abs(i - list.indexOf(source));
						if (compare < distance ) {
							distance = compare;
						}
					}
				}
				
			}
			
		}
		
		return distance;
	}
	
	public Inventory playerInventory(Joueur j) {
		
		Inventory inv = Bukkit.createInventory(null, 54, "§8Interaction");
		ItemStack info = new ItemStack(Material.PAPER);
		ItemMeta infoD = info.getItemMeta();
		infoD.setDisplayName("§eMes informations");
		String armeEquipe = "";
		if (j.armeEquipe == null) {
			armeEquipe = "§bArme équipé :§9 Colt .45";
		} else {
			armeEquipe = "§bArme équipé :§9 " + j.armeEquipe.getNom(); 
		}
		infoD.setLore(Arrays.asList("§bRôle : §9" + j.getRole(),"§bPersonnage : §9" + j.getPerso(),"§bBalles restantes : §9" + j.getVie(),armeEquipe));
		infoD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		infoD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		info.setItemMeta(infoD);
		
		ItemStack defausse = new ItemStack(Material.ENDER_PORTAL_FRAME);
		ItemMeta defausseD = defausse.getItemMeta();
		defausseD.setDisplayName("§eDéfausser");
		defausseD.setLore(Arrays.asList("§bDéfausser ses cartes"));
		defausseD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		defausseD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		defausse.setItemMeta(defausseD);
		
		ItemStack passe = new ItemStack(Material.BED);
		ItemMeta passeD = passe.getItemMeta();
		passeD.setDisplayName("§ePasser");
		passeD.setLore(Arrays.asList("§bPasser son tour"));
		passeD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		passeD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		passe.setItemMeta(passeD);
		
		ItemStack distance = new ItemStack(Material.DEAD_BUSH);
		ItemMeta distanceD = distance.getItemMeta();
		distanceD.setDisplayName("§eLes distances");
		distanceD.setLore(getAllDistance(j));
		distanceD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		distanceD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		distance.setItemMeta(distanceD);
		
		inv.setItem(0, info);
		inv.setItem(3, defausse);
		inv.setItem(5, passe);
		inv.setItem(8,distance);
		
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta itemD = item.getItemMeta();
		itemD.setDisplayName("§6Nothing");
		itemD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		itemD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(itemD);
		
		for (int posBloc = 9 ; posBloc < 18 ; posBloc++) {
			inv.setItem(posBloc, item);
		}
		
		int posMain = 18;
		for (Carte c : j.getMains()) {
			ItemStack itemMain = c.representation();
			inv.setItem(posMain++,itemMain);
		}
		
		for (int posBloc = 36 ; posBloc < 45 ; posBloc++) {
			inv.setItem(posBloc, item);
		}
		
		posMain = 45;
		for (Carte c : j.getPoses()) {
			ItemStack itemMain = c.representation();
			inv.setItem(posMain++,itemMain);
		}
		
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
		startTask = false;
		currentNbBang = 0;
		
		for(Player p : this.world.getPlayers()) {
			p.setGameMode(GameMode.ADVENTURE);
		}
		
		//init players spots;
		this.spotsFixe = new Location[7];
		spotsFixe[0] = new Location(sender.getServer().getWorld("world"), 183.5, 132, 224.5, 0, 10);
		spotsFixe[1] = new Location(sender.getServer().getWorld("world"), 178.5, 132, 226.5, -40, 10);
		spotsFixe[2] = new Location(sender.getServer().getWorld("world"), 177.5, 132, 230.5, -90, 10);
		spotsFixe[3] = new Location(sender.getServer().getWorld("world"), 180.5, 132, 235.5, -140, 10);
		spotsFixe[4] = new Location(sender.getServer().getWorld("world"), 185.5, 132, 236.5, 170, 10);
		spotsFixe[5] = new Location(sender.getServer().getWorld("world"), 189.5, 132, 232.5, 110, 10);
		spotsFixe[6] = new Location(sender.getServer().getWorld("world"), 188.5, 132, 226.5, 50, 10);
		
		
		this.spots = spotsFixe.clone();
		
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
			
			j.setLocation(spots[i]);
			this.players.add(j);		
			i++;
		}
		
		Joueur[] joueurs = new Joueur[7];
		
		for (int k = 0 ; k < joueurs.length ; k++) {
			for (Joueur p : this.players) {
				Location loc = this.spotsFixe[k];
				if (p.getLocation() == loc) {
					joueurs[k] = p;
				}
			}
		}  
		
		this.players = new ArrayList<Joueur>(Arrays.asList(deleteNull(joueurs)));
		

		//reset emplacement
		for (Location l : this.spotsFixe) {
			resetAllPlateforme(l);
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
		
		this.pioche = CartesFactory.FACTORY.allCartes("pioche.txt");
		this.allCartes = CartesFactory.FACTORY.allCartes("pioche.txt");
		shufflePioche();
	//	System.out.println(pioche);
	}

	public boolean estArme(Carte c) {
		return (c.getClass() == Schofield.class ||c.getClass() == Remington.class || c.getClass() == Winchester.class || c.getClass() == Volcanic.class ||
				c.getClass() == Carabine.class);
	}
	
	public Inventory actionInventory(Joueur source, Joueur cible, String actionType) {
		Inventory action = Bukkit.createInventory(null, 18, "§8Action");
		
		
		
		ItemStack fin = new ItemStack(Material.BARRIER);
		ItemMeta finD = fin.getItemMeta();
		finD.setDisplayName("§b"+actionType);
		finD.setLore(Arrays.asList("§eQuitter"));
		finD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		finD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		fin.setItemMeta(finD);
		
		ItemStack explication = new ItemStack(Material.PAPER);
		ItemMeta explicationD = explication.getItemMeta();
		explicationD.setDisplayName("§b"+ source.getPseudo());
		finD.setLore(Arrays.asList("§e"+actionType));
		explicationD.addEnchant(Enchantment.DAMAGE_ALL, 200, true);
		explicationD.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		explication.setItemMeta(explicationD);
		
		int posMain = 0;
		for (Carte c : cible.getMains()) {
			ItemStack itemMain = c.representation();
			action.setItem(posMain++,itemMain);
		}
		
		action.setItem(16, explication);
		action.setItem(17, fin);
		return action;
	}
	
	public void partialShuffle() {
		ArrayList<Carte> list = new ArrayList<Carte>();
		list.add(this.pioche.get(0));
		list.add(this.pioche.get(1));
		list.add(this.pioche.get(2));
		this.pioche.remove(0);
		this.pioche.remove(1);
		this.pioche.remove(2);
		Collections.shuffle(pioche);
		list.addAll(pioche);
		this.pioche = list;
	}
	
	public void resetPartialPlateforme(Location l) {
		Location loc = l.clone();
		loc.setY(loc.getY()+2);
		Block b2 = loc.getBlock();
		b2.setType(Material.BARRIER);
		loc.setY(loc.getY()+1);
		Block b3 = loc.getBlock();
		b3.setType(Material.AIR);
	}
	
	public Carte getType(String type) {
		for (Carte c : allCartes) {
			if (c.getNom().equals(type)) {
				return c;
			}
		}
		return null;
	}
	
	public void resetAllPlateforme(Location l) {
		Location loc = l.clone();
		loc.setY(loc.getY()-1);
		Block b = loc.getBlock();
		b.setType(Material.SANDSTONE);
		loc.setY(loc.getY()+3);
		Block b2 = loc.getBlock();
		b2.setType(Material.BARRIER);
		loc.setY(loc.getY()+1);
		Block b3 = loc.getBlock();
		b3.setType(Material.AIR);
	}
	
	public Carte isGameMaterial(Material m) {
		for(Carte c : this.allCartes) {
			if (c.representation().getType() == m) {
				return c;
			}
		}
		return null;
	}
	
	public boolean isTargettableCarte(Carte c) {
		return c.getNom().equalsIgnoreCase("Bang") || c.getNom().equalsIgnoreCase("Prison") || c.getNom().equalsIgnoreCase("Braquage") || c.getNom().equalsIgnoreCase("Coup de foudre")
				|| c.getNom().equalsIgnoreCase("Duel") || c.getNom().equalsIgnoreCase("Bang");
	}
	
	
	
	private Joueur[] deleteNull(Joueur[] joueur) {
		int size = 0;
		for (int i = 0 ; i < joueur.length ; i++) {
			if (joueur[i] != null) {
				size++;
			}
		}
		Joueur[] tab = new Joueur[size];
		int k = 0;
		for (int i = 0 ; i < joueur.length ; i++) {
			if (joueur[i] != null) {
				tab[k++] = joueur[i];
			}
		}
		return tab;
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
	
	public Joueur getJoueur(String p) {
		for (Joueur j : players ) {
			if (j.getPseudo().equals(p)) {
				return j;
			}
		}
		return null;
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
	
	private void shufflePioche() {
		Collections.shuffle(pioche);
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
		for (Joueur p : players) {
			if (p.getPseudo().equalsIgnoreCase(joueur)){
				return p.getChoix();
			}
		}
		return null;
	}

	
	public ArrayList<Carte> getPioche() {
		return pioche;
	}
	
	public Carte getCarte() {
		Carte carte = this.pioche.get(0);
		this.pioche.remove(0);
		return carte;
		
	}
	
	public void defausse(Carte c) {
		this.pioche.add(c);
	}
	
	public void setPlugin(JavaPlugin plugin) {
		this.plugin = plugin;
		
	}
	
}
