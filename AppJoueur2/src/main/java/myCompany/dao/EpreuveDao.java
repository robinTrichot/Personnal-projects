package myCompany.dao;

import myCompany.beans.Epreuve;

import java.util.List;

public interface EpreuveDao {

    void ajouter(Epreuve epreuve) throws DaoException;

    List<Epreuve> lister() throws DaoException;

    Epreuve lecture(Long id) throws DaoException;

    void modifier(Epreuve epreuve) throws DaoException;

    void delete(Long id) throws DaoException;

    List<Epreuve> rechercher(String chaine) throws DaoException;
}
