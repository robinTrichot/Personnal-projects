package myCompany.dao;

import myCompany.beans.Match;
import myCompany.beans.Tournoi;

import java.util.List;

public interface MatchDao {
    List<Match> lister() throws DaoException;

    Match lecture(int id) throws DaoException;

    void modifier(Match match) throws DaoException;

    void delete(int id) throws DaoException;

    List<Match> rechercher(String chaine) throws DaoException;

    List<Match> compteVicDef(String chaine) throws DaoException;
}
