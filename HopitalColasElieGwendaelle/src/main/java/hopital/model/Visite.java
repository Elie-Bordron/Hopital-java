package hopital.model;

import java.time.LocalDate;
import java.util.Objects;


public class Visite {

	private Integer numero;
	private Patient patient;
	private Medecin medecin;
	private int tarif;
	private String salle;
	private LocalDate dateVisite;
	public static int nombre_de_numeros_de_visite = 0; //  est utilisé dans le constructeur pour générer des numéros de visite.
	
	public Visite() {
	}
	
	
	public Visite(int tarif, String salle, LocalDate dateVisite) {
		this.numero = nombre_de_numeros_de_visite;
		nombre_de_numeros_de_visite ++;
		this.tarif = tarif;
		this.salle = salle;
		this.dateVisite = dateVisite;
	}

	public Visite(int numero, int tarif, String salle, LocalDate dateVisite) {
		this.numero = numero;
		this.tarif = tarif;
		this.salle = salle;
		this.dateVisite = dateVisite;
	}

	public Visite(Patient patient, Medecin medecin, int tarif, String salle, LocalDate dateVisite) {
		this.numero = nombre_de_numeros_de_visite;
		nombre_de_numeros_de_visite ++;
		this.patient = patient;
		this.medecin = medecin;
		this.tarif = tarif;
		this.salle = salle;
		this.dateVisite = dateVisite;
	}
	
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	public Patient getPatient() {
		return patient;
	}
	
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public Medecin getMedecin() {
		return medecin;
	}
	
	public void setMedecin(Medecin medecin) {
		this.medecin = medecin;
	}
	
	public int getTarif() {
		return tarif;
	}
	
	public void setTarif(int tarif) {
		this.tarif = tarif;
	}
	
	public String getSalle() {
		return salle;
	}
	
	public void setSalle(String salle) {
		this.salle = salle;
	}
	
	public LocalDate getDateVisite() {
		return dateVisite;
	}
	
	public void setDateVisite(LocalDate dateVisite) {
		this.dateVisite = dateVisite;
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
		Visite other = (Visite) obj;
		return Objects.equals(numero, other.numero);
	}
	
	@Override
	public String toString() {
		return "Numero de visite: "+numero + "\n" + patient + "\n" + "Numero de medecin: "+medecin + "\n Tarif consultation: " + tarif + "\n salle:" + salle + "\n date: " + dateVisite ;
	}
	
}
