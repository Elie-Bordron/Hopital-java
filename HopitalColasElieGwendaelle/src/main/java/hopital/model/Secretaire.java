package hopital.model;

import java.time.LocalDate;

import src.main.java.FileAttente;

public class Secretaire extends Compte{

	public Secretaire(int numero, String login, String password, FileAttente fileAttente) {
		super(numero, login, password,"secretaire", fileAttente);
	}
	
	public Secretaire(String login, String password, FileAttente fileAttente) {
		super(login, password,"secretaire", fileAttente);
	}

	public void ajouterPatient(Patient patient, String salle) {
		patient.setSalle(salle);
		getFileAttente().ajouterPatient(patient);
	}
	
	public void afficherFileAttente() {
		getFileAttente().afficher();
	}
	
	public void commencerPause() {
		System.out.println("Début de la pause: "+LocalDate.now().toString());
		getFileAttente().saveFileAttente();
	}
	public void finirPause() {
		System.out.println("Fin de la pause: "+LocalDate.now().toString());
		String msg = getFileAttente().loadFileAttente();
		System.out.println(msg);
	}
	
	
}
