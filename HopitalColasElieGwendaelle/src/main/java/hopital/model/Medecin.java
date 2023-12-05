package hopital.model;

public class Medecin extends Compte {

	private String type;
	
	public Medecin(int numero, String login, String password) {
		super(numero, login, password,"medecin");

	}
	
	public Medecin(String login, String password) {
		super(login, password,"medecin");

	}

	public String getType() {
		return type;
	}
	
	
}
