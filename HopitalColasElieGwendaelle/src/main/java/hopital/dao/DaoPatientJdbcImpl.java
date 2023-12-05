package hopital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hopital.model.Patient;
import hopital.util.JdbcContext;

public class DaoPatientJdbcImpl implements DaoPatient{

	@Override
	public void insert(Patient obj) {
		Connection connection = JdbcContext.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into patient(nom_patient, prenom_patient) values(?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getPrenom());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				obj.setNumero(rs.getInt(1));
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		
	}

	@Override
	public void update(Patient obj) {
		Connection connection = JdbcContext.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update patient set nom_patient = ?, prenom_patient = ? where id_patient = ?");
			ps.setString(1, obj.getNom());
			ps.setString(2, obj.getPrenom());
			ps.setInt(3, obj.getNumero());
			ps.executeUpdate();
			
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		
	}

	@Override
	public void delete(Patient obj) {
		deleteByKey(obj.getNumero());
		
	}

	@Override
	public void deleteByKey(Integer key) {
		PreparedStatement ps = null;
		try {
			ps =  JdbcContext.getConnection().prepareStatement("delete from patient where id_patient = ?");
			ps.setInt(1, key);
			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		
	}

	@Override
	public Patient findByKey(Integer key) {
		Patient patient = null;
		Connection connection = JdbcContext.getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement("select * from patient where id_patient = ?");
			ps.setInt(1, key);
			ResultSet rs  = ps.executeQuery();
			while(rs.next()) {
				 patient = new Patient(rs.getInt("id_patient"), rs.getString("nom_patient"),rs.getString("prenom_patient"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		return patient;
	}

	@Override
	public List<Patient> findAll() {
		List<Patient> patients = new ArrayList<Patient>();

		Connection connection = JdbcContext.getConnection();
		Statement statement = null;
		try {

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from patient");

			while (rs.next()) {
				patients.add(new Patient(rs.getInt("id_patient"), rs.getString("nom_patient"), rs.getString("prenom_patient")));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		return patients;
	}

}
