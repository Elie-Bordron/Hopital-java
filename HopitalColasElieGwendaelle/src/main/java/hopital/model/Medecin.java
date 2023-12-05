package hopital.model;

import java.time.LocalDate;
import java.util.List;

import src.main.java.FileAttente;

public class Medecin extends Compte {
	
	private String type;
	private List<Visite> visites;
	private String salle;

	public Medecin() {
	}
	
	public Medecin(int numero, String login, String password) {
		super(numero, login, password,"medecin");
	}
	public Medecin(int numero, String login, String password, FileAttente fileAttente) {
		super(numero, login, password,"medecin", fileAttente);
	}
	
	public Medecin(String login, String password, FileAttente fileAttente) {
		super(login, password,"medecin", fileAttente);
	}

	
	public Visite visiteDePatient(Patient patient) {
		return new Visite(patient, this, 20, salle, LocalDate.now());
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
	
	public Patient prochainPatient() {
		return getFileAttente().prochainPatient(salle);
	}

}

