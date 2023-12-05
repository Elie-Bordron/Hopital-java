import java.util.Scanner;

import hopital.dao.DaoCompteJdbcImpl;
import hopital.model.Compte;
import hopital.model.Medecin;
import hopital.model.Secretaire;
import hopital.util.JdbcContext;


import hopital.model.Compte;

public class MenuTest {
//	public static String typeC;
	public static Compte utilisateur = ConnectionCompte();

	private static boolean salle1=false;
	private static boolean salle2=false;
	private static String phraseIntro = "Que souhaitez-vous faire ?\nRépondez par le numéro correspondant à l'action désirée.";
	
	public static void main(String[] args) {
		// 1 -
			
			System.out.println(utilisateur.getTypeCompte().equals("secretaire"));
		int a = 1;
		while(a == 1) {
			a = 0;
			int Choix = 0;
			boolean sousmenu = true;
			System.out.println("-----------------------");
//			System.out.println(utilisateur.getTypeCompte());
			if (utilisateur.getTypeCompte()=="secretaire") {
				while(sousmenu) {
					System.out.println(phraseIntro
							+ "\n(0) : se déconnecter\n(1) : ajouter patient à la file d'attente\n(2) : "
							+ "voir état file d'attente\n(3)"
							+ " : Historique d'un patient\n(4) : prendre une pause");
					Choix = saisieInt("Choix : ");
					switch(Choix) {
					case 0 : sousmenu=false;break;//OK
					case 1 : continuer();sousmenu=true;break;
					case 2 : continuer();sousmenu=true;break;
					case 3 : continuer();sousmenu=true;break;
					case 4 : continuer();sousmenu=true;break;
					default : sousmenu=true;break;//OK
					}
				}
			}else if (utilisateur.getTypeCompte().equals("medecin")) {
				boolean salleActuelle = false; //salle 1=true, salle 2=false
				if (salle1==false && salle2==false) {
					while ((Choix!=1) && (Choix!=2)) {
						Choix = saisieInt("Veuillez choisir entre la salle (1) et (2) : ");
						if (Choix == 1) {
							salle1=true; salleActuelle = true;
						}else if (Choix == 2) {
							salle2=true; salleActuelle = false;
						}
					}
				} else if (salle1==true && salle2==false) {
					salle2=true; salleActuelle = false;
				} else if (salle1==false && salle2==true) {
					salle1=true; salleActuelle = true;
				}
				while(sousmenu) {
					System.out.println(phraseIntro+"\n(0) : se déconnecter\n(1) : rendre salle\n(2) : rendre salle disponible\n(3) : "
							+ "voir état file d'attente\n(4)"
							+ " : voir fiche du prochain patient\n(5) : sauvegarder la liste des visites");
					Choix = saisieInt("Choix : ");
					switch(Choix) {
					case 0 : sousmenu=false;break;//OK
					case 1 : if(salleActuelle) {salle1=false;}else{salle2=false;}sousmenu=false;break; //Déconnexion après salle rendue
					case 2 : continuer();sousmenu=true;break;
					case 3 : continuer();sousmenu=true;break;
					case 4 : continuer();sousmenu=true;break;
					case 5 : continuer();sousmenu=true;break;
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
	
}
