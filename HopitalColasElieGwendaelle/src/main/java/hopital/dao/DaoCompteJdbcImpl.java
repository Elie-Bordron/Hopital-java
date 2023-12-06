package hopital.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hopital.model.Compte;
import hopital.model.Medecin;
import hopital.model.Secretaire;
import hopital.util.JdbcContext;

public class DaoCompteJdbcImpl implements DaoCompte {

	@Override
	public void insert(Compte obj) {
		Connection connection = JdbcContext.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("insert into compte(login_compte, password_compte, type_compte) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, obj.getLogin());
			ps.setString(2, obj.getPassword());
			ps.setString(3, obj.getTypeCompte());
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
	public void update(Compte obj) {
		Connection connection = JdbcContext.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("update compte set login_compte = ?, password_compte = ?, type_compte = ? where id_compte = ?");
			ps.setString(1, obj.getLogin());
			ps.setString(2, obj.getPassword());
			ps.setString(3, obj.getTypeCompte());
			ps.setInt(4, obj.getNumero());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
	}

	@Override
	public void delete(Compte obj) {
		deleteByKey(obj.getNumero());
		
	}

	@Override
	public void deleteByKey(Integer key) {
		Connection connection = JdbcContext.getConnection();
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement("delete from compte where id_compte = ?");
			ps.setInt(1, key);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
	}

	@Override
	public Compte findByKey(Integer key) {
		Compte compte = new Compte();
		Connection connection = JdbcContext.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("select * from compte where id_compte = ?");
			ps.setInt(1, key);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("type_compte").equals("medecin")) {
					compte = new Medecin(rs.getInt("id_compte"), rs.getString("login_compte"), rs.getString("password_compte"));
				}
				if (rs.getString("type_compte").equals("secretaire")) {
					compte = new Secretaire(rs.getInt("id_compte"), rs.getString("login_compte"), rs.getString("password_compte"));
				}
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		return compte;
	}

	@Override
	public List<Compte> findAll() {
		List<Compte> comptes = new ArrayList<Compte>();
		Statement st = null;
		try {
			st = JdbcContext.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from compte");
			while (rs.next()) {
				if (rs.getString("type_compte").equals("medecin")) {
				comptes.add(new Medecin(rs.getInt("id_compte"), rs.getString("login_compte"), rs.getString("password_compte")));
				}
				if (rs.getString("type_compte").equals("secretaire")) {
					comptes.add(new Secretaire(rs.getInt("id_compte"), rs.getString("login_compte"), rs.getString("password_compte")));
					}
			}
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		return comptes;
	}
	
	public int connection(String login, String password) {
		int idCompte = 0;
		Connection connection = JdbcContext.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("select * from compte where login_compte = ? and password_compte = ?");
			ps.setString(1, login);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();	
			while (rs.next()) {
				idCompte = rs.getInt("id_compte");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JdbcContext.closeConnection();
		if (idCompte == 0) System.out.println("Identifiant ou mot de passe incorrect");
		
		return idCompte;

	}

	
}
