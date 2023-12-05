package hopital.model;

public class Medecin extends Compte {

	
	public Medecin(int numero, String login, String password) {
		super(numero, login, password,"medecin");

	}
	
	public Medecin(String login, String password) {
		super(login, password,"medecin");

	}

	
	
}
