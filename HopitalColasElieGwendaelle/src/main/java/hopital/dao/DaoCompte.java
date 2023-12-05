package hopital.dao;

import hopital.model.Compte;

public interface DaoCompte extends DaoGeneric<Compte, Integer> {

	int connection(String login, String password);
}
