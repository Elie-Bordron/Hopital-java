package hopital.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Secretaire extends Compte{
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  

	public Secretaire () {}
	
	public Secretaire(int numero, String login, String password) {
		super(numero, login, password,"secretaire");
	}
	
	public Secretaire(String login, String password) {
		super(login, password,"secretaire");
	}

	public static void ajouterPatient(Patient patient, FileAttente fileAttente) {
		fileAttente.ajouterPatient(patient);
	}
	
	public static void afficherFileAttente(FileAttente fileAttente) {
		fileAttente.afficher();
	}
	
	public static void commencerPause(FileAttente fileAttente) {
		
		System.out.println("Début de la pause: "+dtf.format(LocalDateTime.now()));
		fileAttente.saveFileAttente();
	}
	public static void finirPause(FileAttente fileAttente) {
		System.out.println("Fin de la pause: "+dtf.format(LocalDateTime.now()));
		String msg = fileAttente.loadFileAttente();
		System.out.println(msg);
	}
	
	
}
