import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JTextField;


public class Bdd extends JFrame {


    Connection conn;
    PreparedStatement pst;
    String nom, prenom, sexe, id;
    PreparedStatement pst2;

    private JTable tableInventee;
    private JPanel mainPanelJoueur;
    private JTabbedPane MainPanelTennis;
    private JPanel PanelJoueur;
    private JTextField txtSexe;
    private JTextField txtFirstName;
    private JTextField txtName;
    private JButton ajouterButtonJoueur;
    private JButton editerButtonJoueur;
    private JButton DeleteJoueur;
    private JButton searchButtonId;
    private JTextField idJoueur;
    private JButton searchJoueurBySearch;
    private JTextField SearchJoueur;
    private JCheckBox hommeCheckBox;
    private JCheckBox femmeCheckBox;
    private JButton searchByNameButton;
    private JTextField txtNomTournoi;
    private JTextField textField3;
    private JTextField txtSearchTournoi;
    private JButton ajouterButtonTournoi;
    private JButton editerButtonTournoi;
    private JButton supprimerButtonTournoi;
    private JButton searchByIdButtonTournois;
    private JButton searchButtonTournoi;
    private JButton searchByNameTournoi;
    private JTextField txtCT;
    private JLabel nomTournoiLabel;
    private JLabel codeLabel;
    private JTable table1;
    private JButton SearchOnPanel;
    private JButton editerPanelButton;
    private JTextField txtLiveS;
    private JTable tableTournoi;
    private JButton searchButtonMatchs;
    private JTable tableMatch;
    private JTextField txtSearchMatchs;
    private JTable table2;
    private JTextField textFieldRatio;
    private JCheckBox vainqueurCheckBox;
    private JCheckBox finalisteCheckBox;
    private JTable tableMatchs;
    private JComboBox comboBoxEpreuve;
    private JTextField textField22;
    private JTable tableEpreuve;
    private JCheckBox tournoisHommeCheckBox;
    private JCheckBox tournoiFemmeCheckBox;
    private JTextField textFieldEpreuves;
    private JTable tableEpreuves;
    private JTable table2Matchs;

    public Bdd() {
        ajouterButtonJoueur.addActionListener(e -> {
            Connect();
            nom = txtName.getText();
            prenom = txtFirstName.getText();
            sexe = txtSexe.getText();
            id = idJoueur.getText();

            // vérification de tout ici + si c'ets bien H ou F pour le sexe !
            if (nom.equals("") || prenom.equals("") || sexe.equals("")) {
                JOptionPane.showMessageDialog(null, "Veuillez insérez des champs");
            } else if (!((sexe.equals("F") || (sexe.equals("H"))))) {
                JOptionPane.showMessageDialog(null, "Entrez F ou H pour le sexe");
            } else {
                try {
                    pst = conn.prepareStatement("INSERT INTO joueur (nom, prenom, sexe) VALUES (?,?,?)");
                    pst.setString(1, nom);
                    pst.setString(2, prenom);
                    pst.setString(3, sexe);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Value added");
                    txtName.setText("");
                    txtFirstName.setText("");
                    txtSexe.setText("");
                    txtName.requestFocus();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        searchJoueurBySearch.addActionListener(e -> {
            Connect();
            DefaultTableModel tableModel = new DefaultTableModel();
            JTable table = new JTable(tableModel);
            tableModel.addColumn("Nom");
            tableModel.addColumn("Prenom");
            tableModel.addColumn("ID");

            JFrame frame = new JFrame();
            frame.setSize(550, 350);
            frame.add(new JScrollPane(table));
            frame.setVisible(true);
            JLabel titre = new JLabel("Liste des joueurs de la BDD");
            titre.setFont(new Font("Calibri", Font.PLAIN, 20));
            frame.getContentPane().add(titre, BorderLayout.PAGE_START);
            try {
                if ((hommeCheckBox.isSelected() && femmeCheckBox.isSelected()) || (!hommeCheckBox.isSelected() && !femmeCheckBox.isSelected())) {
                    System.out.println("success");
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT NOM, PRENOM, ID FROM JOUEUR ORDER BY ID DESC");

                    while (rs.next()) {
                        String nom = rs.getString("NOM");
                        String prenom = rs.getString("PRENOM");
                        long id = rs.getLong("ID");
                        tableModel.insertRow(0, new Object[]{nom, prenom, id});
                        System.out.println("le joueur représénté par le numéro id: " + id + " est " + prenom + " " + nom);
                    }

                } else if (femmeCheckBox.isSelected()) {
                    titre = new JLabel("Liste des joueurs femmes de la BDD");
                    titre.setFont(new Font("Calibri", Font.TRUETYPE_FONT, 20));
                    frame.getContentPane().add(titre, BorderLayout.PAGE_START);

                    System.out.println("success");
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT NOM, PRENOM, ID FROM JOUEUR WHERE SEXE ='F' ORDER BY ID DESC");

                    while (rs.next()) {
                        String nom = rs.getString("NOM");
                        String prenom = rs.getString("PRENOM");
                        long id = rs.getLong("ID");

                        tableModel.insertRow(0, new Object[]{nom, prenom, id});

                        System.out.println("le joueur représénté par le numéro id: " + id + " est " + prenom + " " + nom);
                    }
                } else if (hommeCheckBox.isSelected()) {
                    titre = new JLabel("Liste des joueurs hommes de la BDD");
                    titre.setFont(new Font("Calibri", Font.PLAIN, 20));
                    frame.getContentPane().add(titre, BorderLayout.PAGE_START);

                    System.out.println("success");
                    Statement st = conn.createStatement();
                    ResultSet rs = st.executeQuery("SELECT NOM, PRENOM, ID FROM JOUEUR WHERE SEXE ='H' ORDER BY ID DESC");

                    while (rs.next()) {
                        String nom = rs.getString("NOM");
                        String prenom = rs.getString("PRENOM");
                        Long id = rs.getLong("ID");
                        tableModel.insertRow(0, new Object[]{nom, prenom, id});
                        System.out.println("le joueur représénté par le numéro id: " + id + " est " + prenom + " " + nom);
                    }
                }

            } catch (SQLException ee) {
                ee.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ee) {
                    ee.printStackTrace();
                }
            }
        });
        searchButtonId.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                try {
                    id = idJoueur.getText();
                    pst = conn.prepareStatement("SELECT nom, prenom, sexe FROM joueur WHERE id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        nom = rs.getString(1);
                        prenom = rs.getString(2);
                        sexe = rs.getString(3);

                        txtName.setText(nom);
                        txtFirstName.setText(prenom);
                        txtSexe.setText(sexe);
                    } else {
                        txtName.setText("");
                        txtFirstName.setText("");
                        txtSexe.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Gamer ID");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        editerButtonJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nom = txtName.getText();
                prenom = txtFirstName.getText();
                sexe = txtSexe.getText();
                id = idJoueur.getText();


                if (nom.equals("") || prenom.equals("") || sexe.equals("")) {
                    JOptionPane.showMessageDialog(null, "Veuillez insérez des champs");
                } else if (!((sexe.equals("F") || (sexe.equals("H"))))) {
                    JOptionPane.showMessageDialog(null, "Entrez F ou H pour le sexe");
                } else {
                    try {
                        pst = conn.prepareStatement("UPDATE joueur SET nom = ?,prenom = ?,sexe = ? WHERE id = ?");
                        pst.setString(1, nom);
                        pst.setString(2, prenom);
                        pst.setString(3, sexe);
                        pst.setString(4, id);

                        pst.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Record Update");

                        txtName.setText("");
                        txtFirstName.setText("");
                        txtSexe.setText("");
                        txtName.requestFocus();
                        idJoueur.setText("");
                    } catch (SQLException e1) {

                        e1.printStackTrace();
                    }
                }
            }
        });

        DeleteJoueur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                id = idJoueur.getText();
                try {
                    PreparedStatement pst2;
                    PreparedStatement pst1;
                    System.out.println("Succès de delete");

                    pst = conn.prepareStatement(" DELETE sV FROM score_vainqueur AS sV INNER JOIN match_tennis AS m ON sV.ID_MATCH = m.ID where m.ID_VAINQUEUR = ? OR m.ID_FINALISTE = ?");
                    pst.setString(1, id);
                    pst.setString(2, id);
                    pst.executeUpdate();

                    pst1 = conn.prepareStatement(" DELETE m FROM match_tennis AS m where m.ID_VAINQUEUR = ? OR m.ID_FINALISTE = ?");
                    pst1.setString(1, id);
                    pst1.setString(2, id);
                    pst1.executeUpdate();

                    pst2 = conn.prepareStatement(" DELETE j FROM joueur AS j where id = ?");
                    pst2.setString(1, id);
                    pst2.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record Delete");

                    txtName.setText("");
                    txtFirstName.setText("");
                    txtSexe.setText("");
                    txtName.requestFocus();
                    idJoueur.setText("");
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });


        // sert de recheche de no met prenom ave un like
        searchByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                DefaultTableModel tableModel = new DefaultTableModel();
                JTable table = new JTable(tableModel);
                tableModel.addColumn("Nom");
                tableModel.addColumn("Prenom");
                tableModel.addColumn("ID");

                nom = SearchJoueur.getText();
                prenom = SearchJoueur.getText();

                // nécessaire de faire un preparedStattemnt en first puisque requête
                // pour boucler et afficer il faut que je passe ensuite par un ResultSet sinon pas de while avec un prepared
                try {
                    if (SearchJoueur.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "enter a name or firstname");
                    } else {

                        JFrame frame = new JFrame();
                        frame.setSize(550, 350);
                        frame.add(new JScrollPane(table));
                        frame.setVisible(true);
                        JLabel titre = new JLabel("Liste des joueurs de la BDD");
                        titre.setFont(new Font("Calibri", Font.TRUETYPE_FONT, 20));
                        frame.getContentPane().add(titre, BorderLayout.PAGE_START);

                        PreparedStatement pstmt = conn.prepareStatement(
                                "SELECT NOM, PRENOM, ID FROM JOUEUR WHERE NOM LIKE ? OR PRENOM LIKE ? ORDER BY NOM DESC ");
                        pstmt.setString(1, nom + "%");
                        pstmt.setString(2, prenom + "%");
                        ResultSet rs = pstmt.executeQuery();
                        while (rs.next()) {
                            String nom = rs.getString("NOM");
                            String prenom = rs.getString("PRENOM");

                            Long id = rs.getLong("ID");
                            // voici ci dessous lutilsiation des defaulmodl pou afficher un atableau à 2d (jtable)
                            tableModel.insertRow(0, new Object[]{nom, prenom, id});

                        }
                    }
                } catch (SQLException ee) {
                    ee.printStackTrace();
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ee) {
                        ee.printStackTrace();
                    }
                }
            }
        });


        table1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                try {
                    Connect();
                    pst = conn.prepareStatement("SELECT * FROM JOUEUR");
                    ResultSet rs = pst.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    DefaultTableModel model = (DefaultTableModel) table1.getModel();

                    //https://www.youtube.com/watch?v=1xF_PFJLs4g&ab_channel=CSCORNERSunitaRai

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i = 0; i < cols; i++) {
                        colName[i] = rsmd.getColumnName(i + 1);
                        model.setColumnIdentifiers(colName);
                    }
                } catch (
                        SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });


        SearchOnPanel.addActionListener(e -> {
            table1.setModel(new DefaultTableModel());
            try {
                Connect();
                if ((hommeCheckBox.isSelected() && femmeCheckBox.isSelected()) || (!hommeCheckBox.isSelected() && !femmeCheckBox.isSelected())) {
                    pst = conn.prepareStatement("SELECT * FROM JOUEUR");
                } else if (hommeCheckBox.isSelected()) {
                    pst = conn.prepareStatement("SELECT * FROM JOUEUR where sexe = 'H'");
                } else if (femmeCheckBox.isSelected()) {
                    pst = conn.prepareStatement("SELECT * FROM JOUEUR where sexe = 'F'");
                }
                affichageJoueur(pst);
            } catch (
                    SQLException e1) {
                e1.printStackTrace();
            }
        });

        editerPanelButton.addActionListener(e -> {
            Connect();
            nom = txtName.getText();
            prenom = txtFirstName.getText();
            sexe = txtSexe.getText();
            id = idJoueur.getText();

            DefaultTableModel model = (DefaultTableModel) table1.getModel();

            model.setValueAt(idJoueur.getText(), table1.getSelectedRow(), 0);
            model.setValueAt(txtName.getText(), table1.getSelectedRow(), 1);
            model.setValueAt(txtFirstName.getText(), table1.getSelectedRow(), 2);
            model.setValueAt(txtSexe.getText(), table1.getSelectedRow(), 3);


            if (nom.equals("") || prenom.equals("") || sexe.equals("")) {
                JOptionPane.showMessageDialog(null, "Veuillez insérez des champs");
            } else if (!((sexe.equals("F") || (sexe.equals("H"))))) {
                JOptionPane.showMessageDialog(null, "Entrez F ou H pour le sexe");
            } else {
                try {
                    pst = conn.prepareStatement("UPDATE joueur SET nom = ?,prenom = ?,sexe = ? WHERE id = ?");
                    pst.setString(1, nom);
                    pst.setString(2, prenom);
                    pst.setString(3, sexe);
                    pst.setString(4, id);

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record Update");
                    txtName.setText("");
                    txtFirstName.setText("");
                    txtSexe.setText("");
                    txtName.requestFocus();
                    idJoueur.setText("");
                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }


        });

        txtLiveS.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                Connect();

                table1.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) table1.getModel();

                String entreUser = txtLiveS.getText();

                try {
                    PreparedStatement pstmt = conn.prepareStatement("SELECT ID, NOM, PRENOM, SEXE FROM JOUEUR WHERE NOM LIKE ? OR PRENOM LIKE ? OR ID LIKE ?");
                    pstmt.setString(1, "%" + entreUser + "%");
                    pstmt.setString(2, entreUser + "%");
                    pstmt.setString(3, entreUser + "%");
                    ResultSet rs = pstmt.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i = 0; i < cols; i++) {
                        colName[i] = rsmd.getColumnName(i + 1);
                        model.setColumnIdentifiers(colName);

                        String idData, lastNameData, firstNameData, sexeData;

                        //read row one by one & add in the table
                        while (rs.next()) {
                            idData = rs.getString(1);
                            lastNameData = rs.getString(2);
                            firstNameData = rs.getString(3);
                            sexeData = rs.getString(4);
                            String[] row = {idData, lastNameData, firstNameData, sexeData};
                            model.addRow(row);
                        }
                    }
                } catch (SQLException ee) {
                    ee.printStackTrace();
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ee) {
                        ee.printStackTrace();
                    }
                }
            }
        });


        // ##############################################################################################
        // ##############################################################################################
        // ##############################################################################################

        // Onglet Tournois


        searchByIdButtonTournois.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                try {
                    id = textField3.getText();
                    pst = conn.prepareStatement("SELECT nom, code FROM TOURNOI WHERE id = ?");
                    pst.setString(1, id);
                    ResultSet rs = pst.executeQuery();

                    if (rs.next() == true) {
                        String nomTournoi = rs.getString(1);
                        String code = rs.getString(2);

                        txtNomTournoi.setText(nomTournoi);
                        txtCT.setText(code);

                    } else {
                        txtNomTournoi.setText("");
                        txtCT.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Bdd.Tournoi ID");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        ajouterButtonTournoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                String nom = txtNomTournoi.getText();
                String code = txtCT.getText();
                id = textField3.getText();

                // vérification de tout ici + si c'ets bien H ou F pour le sexe !

                if (nom.equals("") || code.equals("") || id.equals("")) {
                    JOptionPane.showMessageDialog(null, "Veuillez insérez des champs");
                } else if (code.length() != 2) {
                    JOptionPane.showMessageDialog(null, "Le champ code doit comporter 2 lettres seuelement ");
                } else {
                    try {
                        //  code.toUpperCase();
                        pst = conn.prepareStatement("INSERT INTO TOURNOI (id, nom, code) VALUES (?,?,?)");
                        pst.setString(1, id);
                        pst.setString(2, nom);
                        // cette ligne convertit en uppercase
                        pst.setString(3, code.toUpperCase());
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Value added");
                        txtNomTournoi.setText("");
                        txtCT.setText("");
                        textField3.setText("");
                        txtNomTournoi.requestFocus();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        searchByNameTournoi.addActionListener(e -> {
            Connect();

            String nomTournoi = txtSearchTournoi.getText();


            // nécessaire de faire un preparedStattemnt en first puisque requête
            // pour boucler et afficer il faut que je passe ensuite par un ResultSet sinon pas de while avec un prepared
            try {
                if (txtSearchTournoi.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "enter a name or firstname");
                } else {

                    System.out.println("bien là ");
                    DefaultTableModel tableModel = new DefaultTableModel();
                    JTable table = new JTable(tableModel);
                    tableModel.addColumn("Nom");
                    tableModel.addColumn("CODE");
                    tableModel.addColumn("ID");

                    JFrame frame = new JFrame();
                    frame.setSize(550, 350);
                    frame.add(new JScrollPane(table));
                    frame.setVisible(true);
                    JLabel titre = new JLabel("Liste des Tournois");
                    titre.setFont(new Font("Calibri", Font.PLAIN, 20));
                    frame.getContentPane().add(titre, BorderLayout.PAGE_START);

                    PreparedStatement pstmt = conn.prepareStatement(
                            "SELECT NOM, CODE, ID FROM TOURNOI WHERE NOM LIKE ?");
                    pstmt.setString(1, "%" + nomTournoi + "%");
                    ResultSet rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String nom = rs.getString("NOM");
                        String code = rs.getString("CODE");
                        long id = rs.getLong("ID");
                        // voici ci dessous lutilsiation des defaulmodl pou afficher un atableau à 2d (jtable)
                        tableModel.insertRow(0, new Object[]{nom, code, id});

                    }
                }
            } catch (SQLException ee) {
                ee.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ee) {
                    ee.printStackTrace();
                }
            }
        });

        searchButtonTournoi.addActionListener(e -> {
            tableTournoi.setModel(new DefaultTableModel());
            try {
                Connect();
                pst = conn.prepareStatement("SELECT * FROM TOURNOI");
                affichageTournoi(pst);
            } catch (
                    SQLException e1) {
                e1.printStackTrace();
            }
        });

        txtSearchTournoi.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                Connect();

                tableTournoi.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) tableTournoi.getModel();

                String entreUser = txtSearchTournoi.getText();


                try {
                    PreparedStatement pstmt = conn.prepareStatement("SELECT ID, NOM, CODE FROM TOURNOI WHERE NOM LIKE ? OR CODE LIKE ? OR ID LIKE ?");
                    pstmt.setString(1, "%" + entreUser + "%");
                    pstmt.setString(2, entreUser + "%");
                    pstmt.setString(3, entreUser + "%");
                    ResultSet rs = pstmt.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];
                    for (int i = 0; i < cols; i++) {
                        colName[i] = rsmd.getColumnName(i + 1);
                        model.setColumnIdentifiers(colName);

                        String idData, nameData, codeData;

                        //read row one by one & add in the table
                        while (rs.next()) {
                            idData = rs.getString(1);
                            nameData = rs.getString(2);
                            codeData = rs.getString(3);
                            String[] row = {idData, nameData, codeData};
                            model.addRow(row);
                        }
                    }
                } catch (SQLException ee) {
                    ee.printStackTrace();
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ee) {
                        ee.printStackTrace();
                    }
                }
            }
        });
        editerButtonTournoi.addActionListener(e -> {
            Connect();
            String nomTournoi = txtNomTournoi.getText();
            String code = txtCT.getText();
            id = textField3.getText();


            DefaultTableModel model = (DefaultTableModel) tableTournoi.getModel();

            model.setValueAt(txtNomTournoi.getText(), tableTournoi.getSelectedRow(), 1);
            model.setValueAt(txtCT.getText(), tableTournoi.getSelectedRow(), 2);
            model.setValueAt(textField3.getText(), tableTournoi.getSelectedRow(), 0);


            try {
                pst = conn.prepareStatement("UPDATE TOURNOI SET id = ?, nom = ?, code = ? WHERE id = ?");
                pst.setString(2, nomTournoi);
                pst.setString(3, code);
                pst.setString(1, id);
                pst.setString(4, id);

                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Record Update");

                txtNomTournoi.setText("");
                txtCT.setText("");
                textField3.setText("");

            } catch (SQLException e1) {

                e1.printStackTrace();
            }
        });

        supprimerButtonTournoi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();
                id = textField3.getText();

                try {
                    PreparedStatement pst4;
                    pst4 = conn.prepareStatement(" DELETE sV FROM score_vainqueur AS sV " +
                            "INNER JOIN match_tennis AS m ON sV.ID_MATCH = m.ID " +
                            "INNER JOIN epreuve AS E ON M.ID_EPREUVE = E.ID WHERE E.ID_TOURNOI = ?");
                    pst4.setString(1, id);
                    pst4.executeUpdate();

                    PreparedStatement pst3;
                    pst3 = conn.prepareStatement("DELETE M from match_tennis AS M INNER JOIN epreuve AS E ON M.ID_EPREUVE = E.ID WHERE E.ID_TOURNOI = ?");
                    pst3.setString(1, id);
                    pst3.executeUpdate();

                    pst2 = conn.prepareStatement("DELETE E from epreuve AS E where id_tournoi = ?");
                    pst2.setString(1, id);
                    pst2.executeUpdate();


                    pst = conn.prepareStatement(" DELETE T FROM TOURNOI AS T where id = ?");
                    pst.setString(1, id);
                    pst.executeUpdate();


                    JOptionPane.showMessageDialog(null, "Record Delete");

                    txtNomTournoi.setText("");
                    txtCT.setText("");
                    textField3.setText("");

                } catch (SQLException e1) {

                    e1.printStackTrace();
                }
            }
        });

        // ########################################################################################################
        // Onglet Matchs
        // ########################################################################################################


        tableMatch.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

            }
        });

        txtSearchMatchs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connect();
                tableMatch.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) tableMatch.getModel();

                String entreUser = txtSearchMatchs.getText();

                try {
                    PreparedStatement pstmt = conn.prepareStatement("SELECT mt.ID, J.NOM as Victoires, J1.NOM as Defaite \n" +
                            "FROM MATCH_TENNIS mt \n" +
                            "INNER JOIN JOUEUR J ON J.ID = mt.ID_VAINQUEUR \n" +
                            "INNER JOIN JOUEUR J1 ON J1.ID = mt.ID_FINALISTE\n" +
                            "WHERE J.NOM LIKE ? OR J1.NOM LIKE ? ORDER BY mt.ID ASC");
                    pstmt.setString(1, "%" + entreUser + "%");
                    pstmt.setString(2, "%" + entreUser + "%");

                    PreparedStatement psV = conn.prepareStatement("SELECT (SELECT COUNT(ID_VAINQUEUR) as Victoires FROM match_tennis m " +
                            "INNER Join JOUEUR J ON m.ID_VAINQUEUR = J.ID where J.NOM LIKE ?\n" +
                            ") as Victoires,\n" +
                            "(SELECT COUNT(ID_FINALISTE) as Defaites FROM match_tennis m\n" +
                            "INNER Join JOUEUR J ON m.ID_FINALISTE = J.ID where J.NOM LIKE ? \n" +
                            ") as Défaites");
                    psV.setString(1, "%" + entreUser + "%");
                    psV.setString(2, "%" + entreUser + "%");

                    ResultSet rs = pstmt.executeQuery();
                    ResultSetMetaData rsmd = rs.getMetaData();

                    int cols = rsmd.getColumnCount();
                    String[] colName = new String[cols];

                    for (int i = 0; i < cols; i++) {
                        colName[i] = rsmd.getColumnName(i + 1);
                        model.setColumnIdentifiers(colName);

                        String idData, id_Vainqueur, id_Fianliste;

                        //read row one by one & add in the table
                        while (rs.next()) {
                            idData = rs.getString(1);
                            id_Vainqueur = rs.getString(2);
                            id_Fianliste = rs.getString(3);
                            String[] row = {idData, id_Vainqueur, id_Fianliste};
                            model.addRow(row);
                        }
                    }

                    ResultSet rsV = psV.executeQuery();
                    ResultSetMetaData rsMV = rsV.getMetaData();


                    table2.setModel(new DefaultTableModel());
                    DefaultTableModel modelFactice = (DefaultTableModel) table2.getModel();

                    int cols3 = rsMV.getColumnCount();
                    String[] colName3 = new String[cols3];
                    int recupV = 0;
                    int recupF = 0;

                    for (int i = 0; i < cols3; i++) {
                        colName3[i] = rsMV.getColumnName(i + 1);
                        modelFactice.setColumnIdentifiers(colName3);
                        String idData1, idData2;

                        while (rsV.next()) {
                            idData1 = rsV.getString(1);
                            idData2 = rsV.getString(2);
                            recupV = Integer.parseInt(rsV.getString(1));
                            recupF = Integer.parseInt(rsV.getString(2));
                            String[] row = {idData1, idData2};
                            modelFactice.addRow(row);
                        }
                    }
                    textFieldRatio.setText(String.valueOf(ratio(recupV, recupF)));
                } catch (SQLException ee) {
                    ee.printStackTrace();
                } finally {
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    } catch (SQLException ee) {
                        ee.printStackTrace();
                    }
                }
            }
        });
        searchButtonMatchs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connect();

                tableMatchs.setModel(new DefaultTableModel());

                // il faut créer un tableau à 3 colonnes;
                // id match  ---- id vainqueur --- id finalsite

                try {
                    Connect();
                    if ((vainqueurCheckBox.isSelected() && finalisteCheckBox.isSelected()) || (!vainqueurCheckBox.isSelected() && !finalisteCheckBox.isSelected())) {
                        pst = conn.prepareStatement("SELECT mt.ID, J.NOM, J1.NOM FROM MATCH_TENNIS mt INNER JOIN JOUEUR J ON J.ID = mt.ID_VAINQUEUR INNER JOIN JOUEUR J1 ON J1.ID = mt.ID_FINALISTE order by mt.id;");

                        ResultSet rs = pst.executeQuery();
                        ResultSetMetaData rsmd = rs.getMetaData();

                        DefaultTableModel model = (DefaultTableModel) tableMatchs.getModel();

                        int cols = rsmd.getColumnCount();
                        String[] colName = new String[cols];

                        for (int i = 0; i < cols; i++) {
                            colName[i] = rsmd.getColumnName(i + 1);
                            model.setColumnIdentifiers(colName);

                            String id_Epreuve, id_Vainqueur, id_Fianliste;
                            //read row one by one & add in the table
                            while (rs.next()) {
                                id_Epreuve = rs.getString(1);
                                id_Vainqueur = rs.getString(2);
                                id_Fianliste = rs.getString(3);

                                String[] row = {id_Epreuve, id_Vainqueur, id_Fianliste};
                                model.addRow(row);
                            }
                        }
                    } else if (vainqueurCheckBox.isSelected()) {
                        pst = conn.prepareStatement("SELECT mt.ID, J.NOM FROM MATCH_TENNIS mt INNER JOIN JOUEUR J ON J.ID = mt.ID_VAINQUEUR ORDER BY mt.ID ASC");

                        ResultSet rs = pst.executeQuery();
                        ResultSetMetaData rsmd = rs.getMetaData();

                        DefaultTableModel model = (DefaultTableModel) tableMatchs.getModel();

                        int cols = rsmd.getColumnCount();
                        String[] colName = new String[cols];

                        for (int i = 0; i < cols; i++) {
                            colName[i] = rsmd.getColumnName(i + 1);
                            model.setColumnIdentifiers(colName);

                            String id_Vainqueur, id_Fianliste;
                            //read row one by one & add in the table
                            while (rs.next()) {

                                id_Vainqueur = rs.getString(1);
                                id_Fianliste = rs.getString(2);

                                String[] row = {id_Vainqueur, id_Fianliste};
                                model.addRow(row);
                            }
                        }
                    } else if (finalisteCheckBox.isSelected()) {
                        pst = conn.prepareStatement("SELECT mt.ID, J1.NOM FROM MATCH_TENNIS mt INNER JOIN JOUEUR J1 ON J1.ID = mt.ID_FINALISTE order by mt.ID ASC;");

                        ResultSet rs = pst.executeQuery();
                        ResultSetMetaData rsmd = rs.getMetaData();
                        DefaultTableModel model = (DefaultTableModel) tableMatchs.getModel();

                        int cols = rsmd.getColumnCount();
                        String[] colName = new String[cols];

                        for (int i = 0; i < cols; i++) {
                            colName[i] = rsmd.getColumnName(i + 1);
                            model.setColumnIdentifiers(colName);

                            String id_Vainqueur, id_Fianliste;
                            //read row one by one & add in the table
                            while (rs.next()) {

                                id_Vainqueur = rs.getString(1);
                                id_Fianliste = rs.getString(2);

                                String[] row = {id_Vainqueur, id_Fianliste};
                                model.addRow(row);
                            }
                        }
                    }
                } catch (
                        SQLException e1) {
                    e1.printStackTrace();
                }

            }
        });

        ///////////////// onglet Epreuve

        String[] annees = {"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019"};
        int[] anneesInt = {2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019};

        comboBoxEpreuve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Connect();
                tableEpreuve.setModel(new DefaultTableModel());
                DefaultTableModel model = (DefaultTableModel) tableEpreuve.getModel();


                int annee = (comboBoxEpreuve.getSelectedIndex());


                try {
                    PreparedStatement pst;
                    if ((tournoiFemmeCheckBox.isSelected() && tournoisHommeCheckBox.isSelected()) || (!tournoiFemmeCheckBox.isSelected() && !tournoisHommeCheckBox.isSelected())) {

                        pst = conn.prepareStatement("SELECT J.NOM, J.PRENOM FROM JOUEUR J INNER JOIN match_tennis mt ON J.id = mt.ID_VAINQUEUR or J.id = mt.ID_FINALISTE INNER JOIN epreuve e ON mt.id = e.ID WHERE e.ANNEE = ?");
                        pst.setInt(1, anneesInt[annee - 1]);

                        ResultSet rs = pst.executeQuery();
                        ResultSetMetaData rsmd = rs.getMetaData();

                        int cols = rsmd.getColumnCount();
                        String[] colName = new String[cols];

                        for (int i = 0; i < cols; i++) {
                            colName[i] = rsmd.getColumnName(i + 1);
                            model.setColumnIdentifiers(colName);

                            String nom, prenom;
                            //read row one by one & add in the table
                            while (rs.next()) {

                                nom = rs.getString(1);
                                prenom = rs.getString(2);

                                String[] row = {nom, prenom};
                                model.addRow(row);
                            }
                        }

                    } else if (tournoisHommeCheckBox.isSelected()) {
                        pst = conn.prepareStatement("SELECT J.NOM, J.PRENOM FROM JOUEUR J INNER JOIN match_tennis mt ON J.id = mt.ID_VAINQUEUR or J.id = mt.ID_FINALISTE INNER JOIN epreuve e ON mt.id = e.ID WHERE e.ANNEE = ? AND J.SEXE ='H'");
                        pst.setInt(1, anneesInt[annee - 1]);

                        ResultSet rs = pst.executeQuery();
                        ResultSetMetaData rsmd = rs.getMetaData();

                        int cols = rsmd.getColumnCount();
                        String[] colName = new String[cols];

                        for (int i = 0; i < cols; i++) {
                            colName[i] = rsmd.getColumnName(i + 1);
                            model.setColumnIdentifiers(colName);

                            String nom, prenom;
                            //read row one by one & add in the table
                            while (rs.next()) {

                                nom = rs.getString(1);
                                prenom = rs.getString(2);

                                String[] row = {nom, prenom};
                                model.addRow(row);
                            }
                        }

                    } else if (tournoiFemmeCheckBox.isSelected()) {
                        pst = conn.prepareStatement("SELECT CONCAT(J.nom,' ',J.Prenom) as Nom FROM JOUEUR J INNER JOIN match_tennis mt ON J.id = mt.ID_VAINQUEUR or J.id = mt.ID_FINALISTE INNER JOIN epreuve e ON mt.id = e.ID WHERE e.ANNEE = ? AND J.SEXE ='F'");
                        pst.setInt(1, anneesInt[annee - 1]);

                        ResultSet rs = pst.executeQuery();
                        ResultSetMetaData rsmd = rs.getMetaData();

                        int cols = rsmd.getColumnCount();
                        String[] colName = new String[cols];

                        for (int i = 0; i < cols; i++) {
                            colName[i] = rsmd.getColumnName(i + 1);
                            model.setColumnIdentifiers(colName);

                            String nom, prenom;
                            //read row one by one & add in the table
                            while (rs.next()) {

                                nom = rs.getString(1);
                             //   prenom = rs.getString(2);

                                String[] row = {nom};
                                model.addRow(row);
                            }
                        }
                    }

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public void affichageJoueur(PreparedStatement pst) throws SQLException {
        ResultSet rs = pst.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();

        int cols = rsmd.getColumnCount();
        String[] colName = new String[cols];
        for (int i = 0; i < cols; i++) {
            colName[i] = rsmd.getColumnName(i + 1);
            model.setColumnIdentifiers(colName);

            String idData, lastNameData, firstNameData, sexeData;

            //read row one by one & add in the table
            while (rs.next()) {
                idData = rs.getString(1);
                lastNameData = rs.getString(2);
                firstNameData = rs.getString(3);
                sexeData = rs.getString(4);
                String[] row = {idData, lastNameData, firstNameData, sexeData};
                model.addRow(row);
            }
        }

    }

    public void affichageTournoi(PreparedStatement pst) throws SQLException {
        ResultSet rs = pst.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();
        DefaultTableModel model = (DefaultTableModel) tableTournoi.getModel();

        int cols = rsmd.getColumnCount();
        String[] colName = new String[cols];
        for (int i = 0; i < cols; i++) {
            colName[i] = rsmd.getColumnName(i + 1);
            model.setColumnIdentifiers(colName);

            String idData, nameData, codeData;

            //read row one by one & add in the table
            while (rs.next()) {
                idData = rs.getString(1);
                nameData = rs.getString(2);
                codeData = rs.getString(3);

                String[] row = {idData, nameData, codeData};
                model.addRow(row);
            }
        }
    }

    public void Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis", "root", "");
            System.out.println("Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static double ratio(double victoire, double defaite) {
        return victoire / (victoire + defaite);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bdd Tennis");
        frame.setContentPane(new Bdd().mainPanelJoueur);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/rolandpng.jpg");
        frame.setIconImage(icon);
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(500, 250);
        frame.setSize(600, 500);
    }
}