import java.time.LocalDate;

import hopital.dao.DaoCompte;
import hopital.dao.DaoPatient;
import hopital.dao.DaoVisite;
import hopital.model.Medecin;
import hopital.model.Patient;
import hopital.model.Visite;
import hopital.model.Compte;
import hopital.util.JdbcContext;

public class TestDao {

	public static void main(String[] args) {
	
		DaoPatient daoPatient = JdbcContext.getDaoPatient();
		
//		daoPatient.insert(new Patient("patien1", "test"));
//		System.out.println(daoPatient.findAll());
//		daoPatient.deleteByKey(2);
//		System.out.println(daoPatient.findAll());
		
		DaoCompte daoCompte =JdbcContext.getDaoCompte();
		
//		daoCompte.insert(new Medecin("login1", "mdp1"));
//		System.out.println(daoCompte.findAll());
//		daoCompte.delete(new Compte(3, null, null, null));
		
		DaoVisite daoVisite = JdbcContext.getDaoVisite();
		daoVisite.insert(new Visite(new Patient(1, null, null), new Medecin(1,null, null),20, "1", LocalDate.now()));
		System.out.println(daoVisite.findAll());
	}
	
	

}
