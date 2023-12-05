package hopital.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import hopital.dao.DaoCompte;
import hopital.dao.DaoCompteJdbcImpl;
import hopital.dao.DaoPatient;
import hopital.dao.DaoPatientJdbcImpl;
import hopital.dao.DaoVisite;
import hopital.dao.DaoVisiteJdbcImpl;

public class JdbcContext {

private static Connection singleton = null;
	
	private static DaoPatient daoPatient = new DaoPatientJdbcImpl();
	private static DaoCompte daoCompte = new DaoCompteJdbcImpl();
	private static DaoVisite daoVisite = new DaoVisiteJdbcImpl();

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		if (singleton == null) {
			try {
				singleton = DriverManager.getConnection("jdbc:mysql://localhost:3306/hopital", "root", "root123@");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return singleton;
	}
	
	public static void closeConnection() {
		if (singleton != null) {
			try {
				singleton.close();
				singleton = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static DaoPatient getDaoPatient() {
		return daoPatient;
	}

	public static DaoCompte getDaoCompte() {
		return daoCompte;
	}

	public static DaoVisite getDaoVisite() {
		return daoVisite;
	}
	
}