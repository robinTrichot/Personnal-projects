package myCompany.dao;

import myCompany.beans.Epreuve;
import myCompany.beans.Match;
import myCompany.utils.SessionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EpreuveDaoImpl implements EpreuveDao {

    private final DaoFactory DAOF;

    public EpreuveDaoImpl(DaoFactory DAOF) {
        this.DAOF = DAOF;
    }


    @Override
    public void ajouter(Epreuve epreuve) throws DaoException {

    }

    @Override
    public List<Epreuve> lister() throws DaoException {

        return null;
    }

    @Override
    public Epreuve lecture(Long id) throws DaoException {
        return null;
    }

    @Override
    public void modifier(Epreuve epreuve) throws DaoException {

    }

    @Override
    public void delete(Long id) throws DaoException {

    }

    @Override
    public List<Epreuve> rechercher(String chaine) throws DaoException {

        List<Epreuve> epreuves = new ArrayList<>();
        Connection connexion = null;
        PreparedStatement statement = null;
        try {
            connexion = DAOF.getConnection();
            statement = connexion.prepareStatement("SELECT t.NOM, J.NOM, J.PRENOM \n" +
                    "FROM JOUEUR J \n" +
                    "INNER JOIN match_tennis mt ON J.id = mt.ID_VAINQUEUR or J.id = mt.ID_FINALISTE \n" +
                    "INNER JOIN epreuve e ON mt.id = e.ID \n" +
                    "INNER JOIN tournoi t ON e.ID_TOURNOI = t.ID\n" +
                    "WHERE e.ANNEE = ?");
            statement.setString(1, chaine);


            ResultSet rs = statement.executeQuery();

            String nom, nomT, prenom;
            System.out.println(chaine + " test methode avant la boucle ! ");

            while (rs.next()) {
                System.out.println("dans la boucle");
                nomT = rs.getString(1);
                nom = rs.getString(2);
                prenom = rs.getString(3);

                System.out.println("la boule est bouclée");
                System.out.println(prenom + " voici le prenom : ");

                Epreuve epreuve = new Epreuve(nomT, nom, prenom);
                epreuves.add(epreuve);
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
        return epreuves;
    }
}
