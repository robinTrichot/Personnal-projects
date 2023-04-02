package myCompany.dao;

import myCompany.beans.Joueur;
import myCompany.beans.Match;
import myCompany.beans.Tournoi;
import myCompany.utils.SessionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchDaoImpl implements MatchDao {
    private final DaoFactory DAOF;

    public MatchDaoImpl(DaoFactory DAOF) {
        this.DAOF = DAOF;
    }

    @Override
    public List<Match> lister() throws DaoException {
        List<Match> matchs = new ArrayList<>();
        Connection connexion = null;
        PreparedStatement statement = null;

        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT mt.ID, J.NOM, J1.NOM FROM MATCH_TENNIS mt INNER JOIN JOUEUR J ON J.ID = mt.ID_VAINQUEUR INNER JOIN JOUEUR J1 ON J1.ID = mt.ID_FINALISTE order by mt.id");
            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                int id = resultat.getInt("id");
                int id_epeuvre = resultat.getInt("mt.id");
                String id_vainqueur = resultat.getString("j.nom");
                String id_finaliste = resultat.getString("j1.nom");


                Match match = new Match(id, id_epeuvre, id_vainqueur, id_finaliste);
                matchs.add(match);
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la BDD");
        } finally {
            try {
                if (connexion != null) {
                    connexion.close();
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la BDD");
            }
        }
        return matchs;
    }

    @Override
    public Match lecture(int id) throws DaoException {
        return null;
    }

    @Override
    public void modifier(Match match) throws DaoException {

    }

    @Override
    public void delete(int id) throws DaoException {

    }

    @Override
    public List<Match> rechercher(String chaine) throws DaoException {

        List<Match> matchs = new ArrayList<>();
        Connection connexion = null;
        PreparedStatement statement = null;


        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT mt.ID, J.NOM as Victoires, J1.NOM as Defaites \n" +
                    "FROM MATCH_TENNIS mt \n" +
                    "INNER JOIN JOUEUR J ON J.ID = mt.ID_VAINQUEUR \n" +
                    "INNER JOIN JOUEUR J1 ON J1.ID = mt.ID_FINALISTE\n" +
                    "WHERE J.NOM LIKE ? OR J1.NOM LIKE ? ORDER BY mt.ID ASC");

            statement.setString(1, "%" + chaine + "%");
            statement.setString(2, "%" + chaine + "%");
            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                int id = resultat.getInt("id");
                int id_epeuvre = resultat.getInt("mt.id");
                String id_vainqueur = resultat.getString("victoires");
                String id_finaliste = resultat.getString("defaites");

                Match match = new Match(id, id_epeuvre, id_vainqueur, id_finaliste);
                matchs.add(match);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return matchs;
    }

    @Override
    public List<Match> compteVicDef(String chaine) throws DaoException {

        List<Match> matchs = new ArrayList<>();
        Connection connexion = null;
        PreparedStatement statement = null;

        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT (SELECT COUNT(ID_VAINQUEUR) FROM match_tennis m " +
                    "INNER Join JOUEUR J ON m.ID_VAINQUEUR = J.ID where J.NOM LIKE ?\n" +
                    ") as Victoires,\n" +
                    "(SELECT COUNT(ID_FINALISTE) FROM match_tennis m\n" +
                    "INNER Join JOUEUR J ON m.ID_FINALISTE = J.ID where J.NOM LIKE ? \n" +
                    ") as Défaites");
            statement.setString(1, "%" + chaine + "%");
            statement.setString(2, "%" + chaine + "%");

            ResultSet resultSet = statement.executeQuery();
            double recupV = 0;
            double recupF = 0;

            while (resultSet.next()) {
                String id_vainqueur = resultSet.getString("Victoires");
                String id_finaliste = resultSet.getString("Défaites");
                recupV = Integer.parseInt(resultSet.getString("Victoires"));
                recupF = Integer.parseInt(resultSet.getString("Défaites"));
                Match match = new Match(id_vainqueur, id_finaliste);
                matchs.add(match);
            }
            double result = SessionUtils.ratio(recupV, recupF);
            System.out.println(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return matchs;
    }

}