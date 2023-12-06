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
		if (patientInDb(patient.getNumero())) {
			patients.add(patient);
		} else {
			System.out.println("Enregistrement du patient dans la base de données");
			patientToDb(patient);
			patients.add(patient);
		}
	}

	public void retirerPatient(Patient patient) {
		
	}
	
	public static boolean patientInDb(int numero) {
		DaoPatient daoPatient = JdbcContext.getDaoPatient();
		Patient patient = daoPatient.findByKey(numero);
		if (patient == null) return false;
		return true;
	}
	
	public void patientToDb(Patient patient) {
		DaoPatient daoPatient = JdbcContext.getDaoPatient();
		daoPatient.insert(patient);
	}
	
	public void afficher() {
		System.out.println("Voici la file d'attente: ");
		for (Patient patient : patients) {
			System.out.print("Le patient ");
			patient.toString();
			System.out.println(" a rendez-vous en salle "+patient.getSalle());
		}
	}
	
	public void afficherProchainPatient(String salle) {
		System.out.println("Le prochain patient en salle "+salle+" est ");
		for (Patient patient : patients) {
			if(patient.getSalle()==salle) {
				patient.toString();
				break;
			}
		}
	}
	
	public Patient getProchainPatient(String salle) {
		for (Patient patient : patients) {
			if(patient.getSalle()==salle) {
				return patient;
			}
		}
		return null;
	}
	
	public Patient sortirProchainPatient(String salle) {
		patient = getProchainPatient(salle);
		
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
				oos = new ObjectOutputStream(fos);
				fos = new FileOutputStream("fileAttente.txt");
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
			oos = new ObjectOutputStream(fos);
			fos = new FileOutputStream("fileAttente.txt");
			oos.writeBytes("");
			fos.close();
			oos.close();
			
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return patients;
	}
}