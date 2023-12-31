package hopital.model;

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
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


import hopital.dao.DaoPatient;
import hopital.util.JdbcContext;

public class FileAttente {
	List<Patient> patients;
	
	public FileAttente() {
		this.patients = new ArrayList<>();
	}
	
	public List<Patient> getPatients() {
		return patients;
	}

	public void setPatients(List<Patient> patients) {
		this.patients = patients;
	}

	public void ajouterPatient(Patient patient){
		if(patients.contains(patient)) {
			System.out.println("Ce patient est déjà en attente d'un rendez-vous.");
		} else if (patientInDb(patient.getNumero())) {
			System.out.println("Le patient est connu dans la base de données");
			patient = JdbcContext.getDaoPatient().findByKey(patient.getNumero());
			patients.add(patient);
			System.out.println("Patient ajouté à la file d'attente.");
		} else {
			System.out.println("Le patient doit etre enregistré dans la base de données");
			patientToDb(patient);
			patients.add(patient);
			System.out.println("Patient ajouté à la file d'attente.");
		}
	}
		
	public static boolean patientInDb(int numero) {
		DaoPatient daoPatient = JdbcContext.getDaoPatient();
		Patient patient = daoPatient.findByKey(numero);
		if (patient == null) return false;
		return true;
	}
	
	public void patientToDb(Patient patient) {
//		System.out.println("nom patient: "+patient.getNom());
		if(patient.getNom()==null) {
			patient.setNom(saisieString("Entrez votre Nom: "));
		}
//		System.out.println("prenom patient: "+patient.getPrenom());
		if(patient.getPrenom()==null) {
			patient.setPrenom(saisieString("Entrez votre Prenom: "));
		}
		DaoPatient daoPatient = JdbcContext.getDaoPatient();
		daoPatient.insert(patient);
	}
	
	public void afficher() {
		System.out.println("Il y a "+ patients.size()+" patients dans la file d'attente: ");
		for(Patient p : patients) {
			System.out.println(p.toString());
		}
//		System.out.println(patients);
	}
	
	public void afficherProchainPatient() {
		patients.get(0).toString();
	}
	
	public Patient getProchainPatient() {
		return patients.get(0);
	}
	
	public Patient sortirProchainPatient() {
		Patient patient = getProchainPatient();
		patients.remove(0);
		return patient;
		
	}

	
	///////////// methodes pour l'ecriture de la file d'attente dans un fichier
	
	public void saveFileAttente() {
		writeFileAttenteToFile();
	}
	
	public String loadFileAttente() {
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
		return "";
	}
	
	public void writeFileAttenteToFile() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		if(patients.size()==0) {
			System.out.println("La file d'attente est vide");
		} else {
			try {
				fos = new FileOutputStream("fileAttente.txt");
				oos = new ObjectOutputStream(fos);
				oos.writeObject(patients);
				fos.close();
				oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	private List<Patient> readFileAttenteFromFile() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		List<Patient> patients = new ArrayList<>();
		try {
			ois = new ObjectInputStream(new FileInputStream("fileAttente.txt"));
			patients = (List<Patient>) ois.readObject();
			for (Patient patient : patients) {
				System.out.println(patient);
			}
			// vide le fichier
			fos = new FileOutputStream("fileAttente.txt", false);
			oos = new ObjectOutputStream(fos);
			oos.writeBytes("");
			fos.close();
			oos.close();
			
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patients;
	}

	//////////////////////////////////////// Pour faire une Saisie dans ajouterPatient()
	public static String saisieString(String message){
		Scanner sc=new Scanner(System.in);
		System.out.println(message);
		return sc.nextLine();
	}
	
	

}