package src.main.java;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
// flux
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;




public class FileAttente {
	List<Patient> patients;
	
	public FileAttente() {
		this.patients = new ArrayList<>();
	}
	
	public void ajouterPatient(Patient patient){
		if (patientInDb(patient.getId())) {
			patients.add(patient);
		} else {
			System.out.println("Enregistrement du patient dans la base de données");
			PatientToDb(patient);
			patients.add(patient);
		}
	}
	
	public boolean patientInDb() {
		// retourne true si l'id du patient est dans la base de donnees, sinon retourne false
	}
	public boolean patientToDb() {
		// enregistre les infos du patient dans la base de donnees
	}
	
	public void afficher() {
		System.out.println("Voici la file d'attente: ");
		for (Patient patient : patients) {
			System.out.print("Le patient ");
			patient.afficher();
			System.out.println(" a rendez-vous en salle "+patient.salle);
		}
	}
	
	public Patient prochainPatient(int salle) {
		System.out.println("Le prochain patient en salle "+salle+" est ");
		for (Patient patient : patients) {
			if(patient.salle==salle) {
				patient.afficher();
				return patient;
			}
		}
		return null;
	}
	

	
	///////////// methodes pour l'ecriture de la file d'attente dans un fichier
	
	private void saveFileAttente() {
		writeFileAttenteFromFile(patients);
	}
	
	private String loadFileAttente() {
		List<Patient>loadedPatients = readFileAttenteFromFile();
		if(patients.equals(loadedPatients)) {
			return "La file d'attente n'a pas changé depuis votre déconnexion.";
		}
		if(loadedPatients.size()<patients.size()) { 
			int nbPatientsTraites=0;
			for(Patient patient:patients) {
				if(!loadedPatients.contains(patient)) {
					nbPatientsTraites++;
				}
			}
			return ""+nbPatientsTraites+" Ont été traités depuis votre déconnexion.";
		}
		else {
			patients = loadedPatients;
		}
	}
	
	private static void writeFileAttenteFromFile() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("fileAttente.txt");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(patients);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<Patient> readFileAttenteFromFile() {
		ObjectInputStream ois = null;
		List<Patient> patients;
		try {
			ois = new ObjectInputStream(new FileInputStream("fileAttente.txt"));
			patients = (List<Patient>) ois.readObject();
			for (Patient patient : patients) {
				System.out.println(patient);
				
			}
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patients;
	}
	
	
	
	

	////////////// attributs et methodes a ajouter dans la classe Visite
	public static int nombre_de_numeros_de_visite = 0; //  est utilisé dans le constructeur pour générer des numéros de visite.
	public Visite(int idPatient, int idMedecin, int coutVisite, int salle, localDate dateVisite) {
		this.numero = nombre_de_numeros_de_visite;
		//[...]
		nombre_de_numeros_de_visite ++;
	}
	
	////////////// attributs et methodes a ajouter dans la classe Secretaire
	private FileAttente fileAttente; // les deux medecins et la secretaire ont la meme file d'attente
	
	public void ajouterPatient(Patient patient, int salle) {
		patient.salle = salle;
		fileAttente.ajouterPatient(patient);
	}
	
	public void afficherFileAttente() {
		fileAttente.afficher();
	}
	
	public void commencerPause() {
		System.out.println("Début de la pause: "+LocalDate.now().toString());
		saveFileAttente();
	}
	public void finirPause() {
		System.out.println("Fin de la pause: "+LocalDate.now().toString());
		String msg = loadFileAttente();
		System.out.println(msg);
	}
	////////////// attributs et methodes a ajouter dans la classe Medecin
	private List<Visite> visites;
	private int salle;
	private FileAttente fileAttente; // les deux medecins et la secretaire ont la meme file d'attente
	
	public Visite visiteDePatient(Patient patient) {
		return new Visite(patient.getId, getId(), 20, salle, localDate.now());
	}
	
	public void nouvelleVisite() {
		if(visites.size()>=10) {
			saveVisites();
			System.out.println("Les visites précédentes ont été sauvegardées");
		}
		visites.add(visiteDePatient(prochainPatient()));
	}
	
	public void saveVisites() {
		// utilise DAOvisite pour sauvegarder la liste de visites, quelle que soit sa longueur
		visites.clear();
	}
	
	public void prochainPatient() {
		return fileAttente.prochainPatient(salle);
	}

}