package hopital.model;

import java.time.LocalDate;
import java.util.List;

import hopital.dao.DaoCompte;
import hopital.util.JdbcContext;

public class Medecin extends Compte {
	
	private String type;
	private List<Visite> visites;
	private String salle;

	public Medecin() {
	}
	
	public Medecin(int numero, String login, String password) {
		super(numero, login, password,"medecin");
	}
	
	public Medecin(String login, String password) {
		super(login, password,"medecin");
	}
	
	public static void rendreSalleDisponible(FileAttente fileAttente, List<Visite> visites, int numMedecin, String salle) {
		if(visites.size()>=10) {
			saveVisites(visites);
			System.out.println("Les visites précédentes ont été sauvegardées");
		}
		visites.add(new Visite(fileAttente.sortirProchainPatient(), getMedecinByKey(numMedecin), 20, salle, LocalDate.now()));
	}
	
	public static void saveVisites(List<Visite> visites) {
		// utilise DAOvisite pour sauvegarder la liste de visites, quelle que soit sa longueur
		
		visites.clear();
	}
	
	public static Medecin getMedecinByKey(int numero) {
		DaoCompte daoCompte = JdbcContext.getDaoCompte();
		Medecin medecin = daoCompte.findByKey(numero);
		return medecin;
	}

	public static void afficherProchainPatient(FileAttente fileAttente) {
		fileAttente.afficherProchainPatient();
	}
	
	public static void afficherFileAttente(FileAttente fileAttente) {
		fileAttente.afficher();
	}
}

