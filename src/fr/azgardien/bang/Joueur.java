package fr.azgardien.bang;

import java.util.Arrays;

import fr.azgardien.roles.Personnage;

public class Joueur {
	
	private Role role;
	private int vie;
	private Personnage[] choix;
	private boolean choisi;
	private String pseudo;
	private Personnage perso;
	private int visionUp;
	
	

	public Joueur(String pseudo) {
		this.pseudo = pseudo;
		this.choix = new Personnage[2];
		this.choisi = false;
		this.visionUp = 1;
	}
	
	public int getVisionUp() {
		return visionUp;
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
	
	public void gatling() {
		
	}
	public void indiens() {
		
	}
	
	public void duel(Joueur j) {
		
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
		return "Joueur [role=" + role + ", vie=" + vie + ", choix=" + Arrays.toString(choix) + ", choisi=" + choisi
				+ ", pseudo=" + pseudo + ", perso=" + perso + ", visionUp=" + visionUp + "]";
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

}
