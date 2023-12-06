package hopital.dao;

import java.util.List;

import hopital.model.Visite;

public interface DaoVisite extends DaoGeneric<Visite, Integer> {

	public List<Visite> findByPatient(Integer key);
}
