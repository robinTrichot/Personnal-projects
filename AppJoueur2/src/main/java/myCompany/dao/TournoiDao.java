package myCompany.dao;

import myCompany.beans.BeanException;
import myCompany.beans.Tournoi;

import java.util.List;

public interface TournoiDao {
    void ajouter(Tournoi tournoi) throws DaoException, BeanException;

    List<Tournoi> lister() throws DaoException;

    Tournoi lecture(Long id) throws DaoException;

    void modifier(Tournoi tournoi) throws DaoException, BeanException;

    void delete(Long id) throws DaoException;

    List<Tournoi> rechercher(String chaine) throws DaoException;
}
