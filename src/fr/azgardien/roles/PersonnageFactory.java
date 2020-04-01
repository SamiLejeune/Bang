package fr.azgardien.roles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;

public class PersonnageFactory {

	public static final PersonnageFactory FACTORY = new PersonnageFactory();
	private PersonnageFactory () {}
	
	
	public ArrayList<Personnage> allPersonnages(String filename) {
	
		ArrayList<Personnage> personnages = new ArrayList<Personnage>();
		File source = new File(filename);
		BufferedReader in = null ;
		try {
			in = new BufferedReader (new FileReader(source));
			String text ;
			try {
				while((text = in.readLine())!= null) {
					Personnage p = createPersonnage(text);
					personnages.add(p);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		Collections.shuffle(personnages);
		return personnages;
	}
	
	public Personnage createPersonnage(String nom) {
		 try {
				Class<?> c = Class.forName(nom);
				Constructor<?> constructor = c.getConstructor();
				Personnage personnage = (Personnage) constructor.newInstance();
				return personnage;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

	}
	
}
