import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;


import hopital.dao.DaoCompteJdbcImpl;
import hopital.model.Medecin;
import hopital.model.Patient;
import hopital.model.Secretaire;
import hopital.model.FileAttente;
import hopital.model.Visite;
import hopital.util.JdbcContext;
import hopital.model.Compte; 
import hopital.model.FileAttente;

public class MenuTest {
//	public static String typeC;

	private static boolean salle1=false;
	private static boolean salle2=false;
	private static String phraseIntro = "Que souhaitez-vous faire ?\nRépondez par le numéro correspondant à l'action désirée.";
	public static FileAttente fileAttente = new FileAttente();
	public static List<Visite> visites = new ArrayList<Visite>();
	
	public static void main(String[] args) {
		while(true) {
			int Choix = 0;
			boolean sousmenu = true;
			Compte utilisateur = ConnectionCompte();
			System.out.println(utilisateur.toString());
			System.out.println("-----------------------");
			if (utilisateur.getTypeCompte().equals("secretaire")) {
				while(sousmenu) {
					System.out.println(phraseIntro
							+ "\n(0) : se déconnecter\n(1) : ajouter patient à la file d'attente\n(2) : "
							+ "voir état file d'attente\n(3)"
							+ " : historique d'un patient\n(4) : prendre une pause");
					Choix = saisieInt("Choix : ");
					switch(Choix) {
					case 0 : sousmenu=false;break; //[OK]
					case 1 : secrAjPatient();continuer();sousmenu=true;break; //[OK] SAUF que il faut verifier le n° et NOM et PRENOM ET il faut ajouter le patient avec son numéro sécu et pas implémenter un numéro incrémenté
					case 2 : fileAttente.afficher();continuer();sousmenu=true;break; //[OK]
					case 3 : Historique();continuer();sousmenu=true;break;
					case 4 : Secretaire.commencerPause(fileAttente);Secretaire.finirPause(fileAttente);
					continuer();sousmenu=true;break;
					default : sousmenu=true;break;
					}
				}
			}else if (utilisateur.getTypeCompte().equals("medecin")) {
				boolean salleActuelle = false;
				String nomSalle = new String();
				if (salle1==false && salle2==false) {
					while ((Choix!=1) && (Choix!=2)) {
						Choix = saisieInt("Veuillez choisir entre la salle (1) et (2) : ");
						if (Choix == 1) {
							salle1=true; salleActuelle = true; nomSalle = "Salle 1";
						}else if (Choix == 2) {
							salle2=true; salleActuelle = false; nomSalle = "Salle 2";
						}
					}
				} else if (salle1==true && salle2==false) {
					salle2=true; salleActuelle = false; nomSalle = "Salle 2";
				} else if (salle1==false && salle2==true) {
					salle1=true; salleActuelle = true; nomSalle = "Salle 1";
				}
				while(sousmenu) {
					System.out.println(phraseIntro+"\n(0) : se déconnecter\n(1) : quitter salle\n(2) : rendre salle disponible\n(3) : "
							+ "voir état file d'attente\n(4)"
							+ " : voir fiche du prochain patient\n(5) : sauvegarder la liste des visites");
					Choix = saisieInt("Choix : ");
					switch(Choix) {
					case 0 : sousmenu=false;break;//OK
					case 1 : if(salleActuelle) {salle1=false;}else{salle2=false;}sousmenu=false;break; //Déconnexion après salle rendue
					case 2 : Medecin.rendreSalleDisponible(fileAttente, visites, utilisateur.getNumero(), nomSalle);
						continuer();sousmenu=true;break;
					case 3 : fileAttente.afficher();continuer();sousmenu=true;break;
					case 4 : continuer();sousmenu=true;break;
					case 5 : continuer();sousmenu=true;break; // Medecin.saveVisites(visites);
					default : sousmenu=true;break;//OK
					}
				}
			}
		}
	}
	
	public static int saisieInt(String message)
	{
	Scanner sc=new Scanner(System.in);
	System.out.print(message);
	return sc.nextInt();
	}
	public static String saisieString(String message)
	{
	Scanner sc=new Scanner(System.in);
	System.out.print(message);
	return sc.nextLine();
	}
	public static void continuer() {
	Scanner sc=new Scanner(System.in);
	System.out.println("Appuyer sur une touche pour continuer …");
	sc.nextLine();
	}
	
	public static Compte ConnectionCompte() {
		int cle = 0;
		int logmdp = 0; 
		Scanner sc=new Scanner(System.in);
		while (logmdp==0) {
			System.out.print("Rentrer le login : ");
			String Login = sc.nextLine();
			System.out.print("Rentrer le mot de passe : ");
			String MdP = sc.nextLine();
			if (JdbcContext.getDaoCompte().connection(Login,MdP)!=0) { // Test SQL si Login & MdP correspondent à une même ligne
				cle = JdbcContext.getDaoCompte().connection(Login,MdP);
				logmdp = 1;
			}
		}
		//System.out.println(JdbcContext.getDaoCompte().findByKey(cle).getTypeCompte());
		return JdbcContext.getDaoCompte().findByKey(cle);
	}
	
	public static void secrAjPatient() {
	int key = saisieInt("Entrer le numéro du patient : ");
//	String nom = saisieString("Entrer le nom du patient : "); //Pour éviter qu'un nouveau venu ne donne le meme id qu'un patient déjà existant.
//	String prenom = saisieString("Entrer le prénom du patient : ");
	fileAttente.ajouterPatient(new Patient(key));
	System.out.println("Patient ajouté à la file.");
	}
	
	public static void Historique() {
		int key = saisieInt("Entrer le numéro du patient : ");
		Patient.getHistoric(key);
	}
	
}
