package myCompany.dao;

import myCompany.beans.Joueur;

import java.sql.SQLException;
import java.util.List;

public interface JoueurDao {
    void ajouter(Joueur joueur) throws DaoException;

    List<Joueur> lister() throws SQLException, DaoException;

    Joueur lecture(Long id) throws DaoException;

    void modifier(Joueur joueur) throws DaoException;

    void delete(Long id) throws DaoException;

    List<Joueur> rechercher(String chaine) throws DaoException;
}
