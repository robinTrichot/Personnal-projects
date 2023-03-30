import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class test {
    private JTable table1;
    private JPanel panel1;

    public test(JTable table) {
this.table1 = table;
        DefaultTableModel tableModel = new DefaultTableModel();
        table1 = new JTable(tableModel);
        tableModel.addColumn("Nom");
        tableModel.addColumn("Prenom");
        tableModel.addColumn("ID");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("test");
        frame.setContentPane(new test(new JTable()).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }
}
