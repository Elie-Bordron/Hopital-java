package hopital.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import hopital.model.Medecin;
import hopital.model.Patient;
import hopital.model.Visite;
import hopital.util.JdbcContext;

public class DaoVisiteJdbcImpl implements DaoVisite{

	@Override
	public void insert(Visite obj) {
		Connection connection = JdbcContext.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into visite(id_patient, id_medecin, cout_visite, salle_visite, date_visite) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			if (obj.getPatient() != null) {
				ps.setInt(1, obj.getPatient().getNumero());
			} else {
				ps.setNull(1,Types.INTEGER);
			}
			if (obj.getMedecin() != null) {
				ps.setInt(2, obj.getMedecin().getNumero());
			} else {
				ps.setNull(2,Types.INTEGER);
			}
			ps.setInt(3, obj.getTarif());
			ps.setString(4, obj.getSalle());
			ps.setDate(5, Date.valueOf(obj.getDateVisite()));
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
	public void update(Visite obj) {
		Connection connection = JdbcContext.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update visite set id_patient = ?, id_medecin = ?, cout_visite = ?, salle_visite = ?, date_visite = ? where id_visite = ?");
			if (obj.getPatient() != null) {
				ps.setInt(1, obj.getPatient().getNumero());
			} else {
				ps.setNull(1,Types.INTEGER);
			}
			if (obj.getMedecin() != null) {
				ps.setInt(2, obj.getMedecin().getNumero());
			} else {
				ps.setNull(2,Types.INTEGER);
			}
			ps.setInt(3, obj.getTarif());
			ps.setString(4, obj.getSalle());
			ps.setDate(5, Date.valueOf(obj.getDateVisite()));
			ps.setInt(6, obj.getNumero());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		
	}

	@Override
	public void delete(Visite obj) {
		deleteByKey(obj.getNumero());
		
	}

	@Override
	public void deleteByKey(Integer key) {
		Connection connection = JdbcContext.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("delete from visite where id_visite = ?");
			ps.setInt(1, key);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		
	}

	@Override
	public Visite findByKey(Integer key) {
		Visite visite = null;
		Connection connection = JdbcContext.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("select * from visite v left join patient p on v.id_patient = p.id_patient left join compte c on v.id_medecin = c.id_compte where id_visite = ?");
			ps.setInt(1, key);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				visite = new Visite(rs.getInt("id_visite"), rs.getInt("cout_visite"),rs.getString("salle_visite"), rs.getDate("date_visite") != null ? rs.getDate("date_visite").toLocalDate() : null);
				if (rs.getInt("id_patient") != 0) {
					visite.setPatient(new Patient(rs.getInt("id_patient"), rs.getString("nom_patient"), rs.getString("prenom_patient")));
				}
				if (rs.getInt("id_medecin") != 0) {
					visite.setMedecin(new Medecin(rs.getInt("id_compte"), rs.getString("login_compte"), rs.getString("password_compte")));
				}	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		return visite;
	}

	@Override
	public List<Visite> findAll() {
		List<Visite> visites = new ArrayList<Visite>();
		Visite visite = null;
		Connection connection = JdbcContext.getConnection();
		Statement statement = null;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from visite v left join patient p on v.id_patient = p.id_patient left join compte c on v.id_medecin = c.id_compte");
			while (rs.next()) {
				visite = new Visite(rs.getInt("id_visite"), rs.getInt("cout_visite"),rs.getString("salle_visite"), rs.getDate("date_visite") != null ? rs.getDate("date_visite").toLocalDate() : null);
				if (rs.getInt("id_patient") != 0) {
					visite.setPatient(new Patient(rs.getInt("id_patient"), rs.getString("nom_patient"), rs.getString("prenom_patient")));
				}
				if (rs.getInt("id_medecin") != 0) {
					visite.setMedecin(new Medecin(rs.getInt("id_compte"), rs.getString("login_compte"), rs.getString("password_compte")));
				}	
				visites.add(visite);
			}
			
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		return visites;
	}

}
