package fr.azgardien.bang;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.azgardien.cartes.Carabine;
import fr.azgardien.cartes.Carte;
import fr.azgardien.cartes.Couleur;
import fr.azgardien.cartes.Lunette;
import fr.azgardien.cartes.Remington;
import fr.azgardien.cartes.Schofield;
import fr.azgardien.cartes.Volcanic;
import fr.azgardien.cartes.Winchester;
import fr.azgardien.roles.Personnage;

public class Joueur {

	private Role role;
	private int vie;
	private Personnage[] choix;
	private boolean choisi;
	private String pseudo;
	private Personnage perso;
	private int visionUp;
	private Location location;
	private ArrayList<Carte> mains;
	public Carte currentAction;

	public boolean finAction;
	public boolean contreAction;
	public Joueur sourceAction;
	public Carte actionRecu;
	public Joueur joueurAttaque;
	public Joueur tueur;
	public Carte armeEquipe;

	public ArrayList<Carte> getMains() {
		return mains;
	}


	public ArrayList<Carte> getPoses() {
		return poses;
	}

	private ArrayList<Carte> poses;


	public Location getLocation() {
		return location;
	}

	public void clear() {
		for (Carte c : this.mains) {
			BangController.getInstance().defausse(c);
		} 
		
		for (Carte c : this.poses) {
			BangController.getInstance().defausse(c);
		}
		this.mains.clear();
		this.poses.clear();
	}
	
	public Joueur(String pseudo) {
		this.pseudo = pseudo;
		this.choix = new Personnage[2];
		this.choisi = false;
		this.visionUp = 1;
		this.mains = new ArrayList<Carte>();
		this.poses = new ArrayList<Carte>();
		this.armeEquipe = null;
	}


	private void setLunette() {
		this.visionUp++;
	}

	public boolean lunette() {
		System.out.println(carteDejaPose(new Lunette("", Couleur.Carreau)));
		if (!carteDejaPose(new Lunette("", Couleur.Carreau))) {
			setLunette();
			return true;
		}
		return false;
	}

	public void removeLunette() {
		this.visionUp--;
	}

	public void pioche(Carte c) {
		this.mains.add(c);
	}

	public void piocheTour() {
		this.mains.addAll(this.perso.piocheTour());
	}


	public void defausse(Carte c) {
		int idx = -1;

		for (int i = 0 ; i < this.mains.size() ; i++) {
			if (this.mains.get(i).getNom() == c.getNom()) {
				idx = i;
			}
		}
		if (idx != -1) {
			this.mains.remove(idx);
			BangController.getInstance().defausse(c);
			return;
		}

		if (idx == -1) {
			for (int i = 0 ; i < this.poses.size() ; i++) {
				if (this.poses.get(i).getNom() == c.getNom()) {
					idx = i;
				}
			}
		}
		if (c.getClass() == Lunette.class) {
			removeLunette();
		}

		this.poses.remove(idx);

		BangController.getInstance().defausse(c);

	}



	public boolean estArmePose(Carte c) {
		return this.armeEquipe.getClass() == c.getClass();
	}

	public int getVisionUp() {
		return visionUp;
	}

	public void setLocation(Location loc) {
		this.location = loc;
	}

	public Personnage[] getChoix() {
		return choix;
	}

	public void ready() {
		this.choisi = true;
	}

	public boolean aChoisi() {
		return this.choisi;
	}


	public Personnage getPerso() {
		return perso;
	}

	public void setPersoChoix(Personnage p1 , Personnage p2) {
		this.choix[0] = p1;
		this.choix[1] = p2;
	}

	public void setPerso(Personnage p) {
		this.perso = p;
	}

	public void bang(Joueur source) {
		String msg = this.getPerso().touche(this, source);
		BangController.getInstance().currentNbBang++;
		Bukkit.broadcastMessage(msg);

	}

	public void gatling() {

	}
	public void indiens() {

	}

	public void duel(Joueur j) {

	}

	public boolean biere(Carte c) {
		if (getRole() == Role.Sherif) {
			if (getVie() < (getPerso().getVie()+1)) {
				this.vie++;
				defausse(c);
				return true;
			}
		} else {
			if (getVie() < getPerso().getVie()) {
				this.vie++;
				defausse(c);
				return true;
			}
		}

		return false;
	}

	public boolean biereSaloon() {
		if (getRole() == Role.Sherif) {
			if (getVie() < (getPerso().getVie()+1)) {
				this.vie++;			
			}
		} else {
			if (getVie() < getPerso().getVie()) {
				this.vie++;			
				return true;
			}
		}

		return false;
	}

	public void pose(Carte c) {
		int idx = -1;
		for (int i = 0 ; i < this.mains.size() ; i++) {
			if (this.mains.get(i).getNom() == c.getNom()) {
				idx = i;
			}
		}
		this.mains.remove(idx);
		this.poses.add(c);
	}

	public boolean peutTirer() {
		return BangController.getInstance().currentNbBang < getPerso().getLimiteBang();
	}

	public boolean poseArme(Carte carte) {
		if (!BangController.getInstance().estArme(carte)) return false;
		if (armeEquipe == null) {
			this.armeEquipe = carte;
			pose(carte);
			return true;
		} else {
			if (armeEquipe.getNom().equals(carte.getNom())) {
				return false;
			} else {
				Bukkit.broadcastMessage("§bDéfausse de "+armeEquipe.getNom());
				defausseArme(armeEquipe);
				this.armeEquipe = carte;
				pose(carte);
				return true;
			}
		}

	}

	public void defausseArme(Carte c) {
		int idx = -1;

		for (int i = 0 ; i < this.poses.size() ; i++) {
			if (this.poses.get(i).getNom() == c.getNom()) {
				idx = i;
			}
		}
		
		this.poses.remove(idx);

		BangController.getInstance().defausse(c);
	}

	public void removeCartePose(Carte carte) {
		int idx = -1;

		for (int i = 0 ; i < this.poses.size() ; i++) {
			if (this.poses.get(i) == carte) {
				idx = i;
			}
		}

		this.poses.remove(idx);
	}


	public boolean carteDejaPose(Carte carte) {
		for (Carte c : this.poses) {
			if (carte.getClass() == c.getClass()) {
				return true;
			}
		}
		return false;
	}

	public boolean isAlive() {
		return this.vie > 0;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}

	public void init() {
		if(this.role == Role.Sherif) {
			this.vie = this.perso.getVie()+1;
		} else {
			this.vie = this.perso.getVie();
		}
		this.visionUp += this.perso.getRangeLunette();

	}

	@Override
	public String toString() {
		return "Joueur [pseudo=" + pseudo + ", visionUp=" + visionUp + "]";
	}

	public int getVie() {
		return vie;
	}


	public void damage(int oof) {
		this.vie = getVie() - oof;
	}

	public String getPseudo() {
		return pseudo;
	}

	public int getDistance() {
		if (armeEquipe == null) return 0;
		else return armeEquipe.getDistance();
	}



}
