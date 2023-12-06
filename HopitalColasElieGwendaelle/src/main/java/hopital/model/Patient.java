package hopital.model;

import java.util.List;
import java.util.Objects;

import hopital.dao.DaoVisite;
import hopital.util.JdbcContext;

public class Patient {

	private Integer numero;
	private String nom;
	private String prenom;
	
	public Patient() {
	}

	public Patient(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	}

	public Patient(Integer numero, String nom, String prenom) {
		this.numero = numero;
		this.nom = nom;
		this.prenom = prenom;
	}


	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(numero);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(numero, other.numero);
	}
	
	@Override
	public String toString() {
		return numero + " " + nom + " " + prenom;
	}
	
	public static void getHistoric(int numero) {
		DaoVisite daoVisite = JdbcContext.getDaoVisite();
		List<Visite> visite = daoVisite.findByPatient(numero);
		
		boolean first = true;
		
		for (Visite v : visite) {
			if (first) System.out.println("Patient : " + v.getPatient().getNumero() +
					" " + v.getPatient().getNom() + " " + v.getPatient().getPrenom()+ "\n");
			first = false;
			System.out.println("Numéro de visite " + v.getNumero() + "\nMedecin : " + v.getMedecin().getNumero() + " - " + 
		v.getMedecin().getLogin() + ", salle : " + v.getSalle() + ", date : " + v.getDateVisite());
		}
		
	}
}
