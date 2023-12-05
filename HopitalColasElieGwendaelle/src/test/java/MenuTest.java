import java.util.Scanner;

import banque.classe.simple;

public class MenuTest {

//	private static typeCompte utilisateur; //SSi classe typeCompte créée
	private static Compte utilisateur;

	private static boolean salle1=false;
	private static boolean salle2=false;
	private static String phraseIntro = "Que souhaitez-vous faire ?\nRépondez par le numéro correspondant à l'action désirée.";
	
	public static void main(String[] args) {
		
		// 1 -
		while(true) {
			int Choix = 0;
			boolean sousmenu = true;
			utilisateur.Connection;
//			utilisateur = Compte.Connection; //SSi classe typeCompte créée
			if (utilisateur instanceof secretaire) {
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
			}else if (utilisateur instanceof medecin) {
				boolean salleActuelle; //1=true, 2=false
				if (salle1==false && salle2==false) {
					while (Choix!=1||Choix!=2) {
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

	
	
	//TypeCompte -> classe mère
	// Medecin -> classe enfant
	// Secretaire -> classe enfant
	//Dans compte, il y a toutes les requetes SQL nécéssaires dont :
	// - public static typeCompte Connexion :
	//  1/ Saisie Login et MdP
	//  2/ Test SQL si Login & MdP correspondent à une même ligne
	//  3/ Retourne l'objet typeCompte/(médecin ou secrétaire)
	public static int saisieInt(String message)
	{
	Scanner sc=new Scanner(System.in);
	System.out.print(message);
	sc.close();
	return sc.nextInt();
	}
	public static String saisieString(String message)
	{
	Scanner sc=new Scanner(System.in);
	System.out.print(message);
	sc.close();
	return sc.nextLine();
	}
	public static void continuer() {
	Scanner sc=new Scanner(System.in);
	System.out.println("Appuyer sur une touche pour continuer …");
	sc.nextLine();
	sc.close();
	}
	
}
