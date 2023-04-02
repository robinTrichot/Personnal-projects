package myCompany.dao;

import myCompany.beans.Tournoi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TournoiImpl implements TournoiDao {
    private final DaoFactory DAOF;

    public TournoiImpl(DaoFactory daof) {
        DAOF = daof;
    }

    @Override
    public void ajouter(Tournoi tournoi) throws DaoException {
        Connection connexion = null;
        PreparedStatement statement = null;
        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("INSERT INTO TOURNOI( nom, code) VALUES(?, ?)");
            statement.setString(1, tournoi.getNom());
            statement.setString(2, tournoi.getCode());
            statement.executeUpdate();

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
    }

    @Override
    public List<Tournoi> lister() throws DaoException {
        List<Tournoi> tournois = new ArrayList<>();
        Connection connexion = null;
        PreparedStatement statement = null;

        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM TOURNOI");
            ResultSet resultat = statement.executeQuery();

            while (resultat.next()) {
                Long id = resultat.getLong("id");
                String nom = resultat.getString("nom");
                String code = resultat.getString("code");

                Tournoi tournoi = new Tournoi(id, nom, code);
                tournois.add(tournoi);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tournois;
    }

    @Override
    public Tournoi lecture(Long id) throws DaoException {
        Connection connexion = null;
        PreparedStatement statement = null;

        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM TOURNOI WHERE ID = ?;");
            statement.setLong(1, id);
            ResultSet resultat = statement.executeQuery();

            if (resultat.next()) {
                return new Tournoi(
                        resultat.getLong("id"),
                        resultat.getString("nom"),
                        resultat.getString("code")
                );
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Tournoi tournoi) throws DaoException {
        Connection connexion = null;
        PreparedStatement statement = null;
        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("UPDATE TOURNOI SET nom = ?, code = ? WHERE id = ?");
            statement.setString(1, tournoi.getNom());
            statement.setString(2, tournoi.getCode());
            statement.setLong(3, tournoi.getId());
            statement.executeUpdate();
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
    }

    @Override
    public void delete(Long id) throws DaoException {
        Connection connexion = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        PreparedStatement pst3 = null;
        PreparedStatement pst4 = null;

        try {
            connexion = DAOF.getConnection();

            pst1 = connexion.prepareStatement(" DELETE sV FROM score_vainqueur AS sV " +
                    "INNER JOIN match_tennis AS m ON sV.ID_MATCH = m.ID " +
                    "INNER JOIN epreuve AS E ON M.ID_EPREUVE = E.ID WHERE E.ID_TOURNOI = ?");
            pst1.setLong(1, id);
            pst1.executeUpdate();

            pst2 = connexion.prepareStatement("DELETE M from match_tennis AS M INNER JOIN epreuve AS E ON M.ID_EPREUVE = E.ID WHERE E.ID_TOURNOI = ?");
            pst2.setLong(1, id);
            pst2.executeUpdate();

            pst3 = connexion.prepareStatement("DELETE E from epreuve AS E where id_tournoi = ?");
            pst3.setLong(1, id);
            pst3.executeUpdate();


            pst4 = connexion.prepareStatement(" DELETE T FROM TOURNOI AS T where id = ?");
            pst4.setLong(1, id);
            pst4.executeUpdate();

        } catch (SQLException e1) {

            e1.printStackTrace();
        }
    }

    @Override
    public List<Tournoi> rechercher(String chaine) throws DaoException {
        List<Tournoi> tournois = new ArrayList<>();
        Connection connexion = null;
        PreparedStatement statement = null;

        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT * FROM TOURNOI WHERE NOM LIKE ? OR CODE LIKE ?");
            statement.setString(1, "%" + chaine + "%");
            statement.setString(2, chaine + "%");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Long id = rs.getLong("id");
                String nom = rs.getString("nom");
                String code = rs.getString("code");

                Tournoi tournoi = new Tournoi(id, nom, code);
                tournois.add(tournoi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tournois;
    }
}
