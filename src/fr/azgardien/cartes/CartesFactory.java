package fr.azgardien.cartes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;

public class CartesFactory {

	public static final CartesFactory FACTORY = new CartesFactory();
	private CartesFactory () {}
	
	
	public ArrayList<Carte> allCartes(String filename) {
	
		ArrayList<Carte> cartes = new ArrayList<Carte>();
		File source = new File(filename);
		BufferedReader in = null ;
		try {
			in = new BufferedReader (new FileReader(source));
			String text ;
			try {
				while((text = in.readLine())!= null) {
					String q = in.readLine();
					int qte = Integer.parseInt(q);
					for (int i = 0 ; i < qte ; i++) {
						Carte p = createCarte(text);
						cartes.add(p);
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		Collections.shuffle(cartes);
		return cartes;
	}
	
	public Carte createCarte(String nom) {
		 try {
				Class<?> c = Class.forName(nom);
				Constructor<?> constructor = c.getConstructor(String.class, Couleur.class);
				Carte carte = (Carte) constructor.newInstance("A",Couleur.Pique);
				return carte;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

	}
}
