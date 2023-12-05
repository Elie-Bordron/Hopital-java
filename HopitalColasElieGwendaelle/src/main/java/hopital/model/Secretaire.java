package hopital.model;

import java.time.LocalDate;

import src.main.java.FileAttente;

public class Secretaire extends Compte{

	public Secretaire () {}
	
	public Secretaire(int numero, String login, String password) {
		super(numero, login, password,"secretaire");
	}
	
	public Secretaire(String login, String password) {
		super(login, password,"secretaire");
	}

	public static void ajouterPatient(Patient patient, String salle, FileAttente fileAttente) {
		patient.setSalle(salle);
		fileAttente.ajouterPatient(patient);
	}
	
	public static void afficherFileAttente(FileAttente fileAttente) {
		fileAttente.afficher();
	}
	
	public static void commencerPause(FileAttente fileAttente) {
		System.out.println("Début de la pause: "+LocalDate.now().toString());
		fileAttente.saveFileAttente();
	}
	public static void finirPause(FileAttente fileAttente) {
		System.out.println("Fin de la pause: "+LocalDate.now().toString());
		String msg = fileAttente.loadFileAttente();
		System.out.println(msg);
	}
	
	
}
