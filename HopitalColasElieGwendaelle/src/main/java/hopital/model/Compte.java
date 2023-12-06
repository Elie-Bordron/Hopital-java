package hopital.model;

import java.util.Objects;
import java.util.Scanner;

import hopital.dao.DaoCompteJdbcImpl;
import hopital.util.JdbcContext;

public class Compte {

	private Integer numero;
	private String login;
	private String password;
	private String typeCompte;	
	public Compte() {
	}

	public Compte(int numero, String login, String password, String typeCompte) {
		this.numero = numero;
		this.login = login;
		this.password = password;
		this.typeCompte = typeCompte;
	}
	
	public Compte(String login, String password, String typeCompte) {
		this.login = login;
		this.password = password;
		this.typeCompte = typeCompte;
		
	}


	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTypeCompte() {
		return typeCompte;
	}

	public void setTypeCompte(String typeCompte) {
		this.typeCompte = typeCompte;
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
		Compte other = (Compte) obj;
		return Objects.equals(numero, other.numero);
	}
	
	@Override
	public String toString() {
		return numero + " " + login + " " + password + " " + typeCompte;
	}


	

	
}
