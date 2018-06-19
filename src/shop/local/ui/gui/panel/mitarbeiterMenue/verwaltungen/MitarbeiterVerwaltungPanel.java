package shop.local.ui.gui.panel.mitarbeiterMenue.verwaltungen;

import net.miginfocom.swing.MigLayout;
import shop.local.domain.Shop;
import shop.local.domain.objects.Mitarbeiter;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.TableModels.MitarbeiterTableModel;
import shop.local.ui.gui.panel.mitarbeiterMenue.MitarbeiterPanel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class MitarbeiterVerwaltungPanel extends JPanel {

    private Shop shop;
    private ShopClientGUI shopClientGUI;

    private JPanel menüpanel;
    private JLabel menüpanelLabel;

    private JButton alleMitarbeiterButton;


    private JPanel einfügePanel;
    private JLabel mitarbeitereinfügenLabel;
    private JLabel mitarbeiterNameLabel;
    private JTextField mitarbeiterNameTextfield;
    private JLabel mitarbeiterPasswortLabel;
    private JTextField mitarbeiterPasswortTextfield;
    private JButton einfügeButton;
    private JButton backButton;

    private JScrollPane scrollPane;
    private MitarbeiterTableModel tmode;
    private JList MitarbeiterListe;
    private JTable MitarbeiterTabelle;
    private RowSorter<MitarbeiterTableModel> sorter;


    public MitarbeiterVerwaltungPanel(ShopClientGUI shopGui) {
        super();

        initialize();

    }

    private void initialize() {

        menüpanel = new JPanel();
        menüpanelLabel = new JLabel("MitarbeiterVerwaltung");
        menüpanel.add(menüpanelLabel);

        alleMitarbeiterButton = new JButton("Alle Mitarbeiter");
        alleMitarbeiterButton.addActionListener(new MitarbeiterListener());



        //Tabelle und Liste
        scrollPane = new JScrollPane();
        MitarbeiterListe = new JList();
        java.util.List<Mitarbeiter> mitarbeiterlist = shop.getMitarbeiterListe();
        tmode = new MitarbeiterTableModel(mitarbeiterlist);
        MitarbeiterTabelle = new JTable(tmode);
        scrollPane = new JScrollPane(MitarbeiterListe);
        sorter = new TableRowSorter<>(tmode);
        MitarbeiterTabelle.setRowSorter(sorter);
        scrollPane = new JScrollPane(MitarbeiterTabelle);


        einfügePanel = new JPanel();
        einfügePanel.setLayout(new MigLayout());


        mitarbeitereinfügenLabel = new JLabel("Mitarbeiter neu erstellen");
        mitarbeiterNameLabel = new JLabel("Name:");
        mitarbeiterNameTextfield = new JTextField(20);
        mitarbeiterPasswortLabel = new JLabel("Passwort:");
        mitarbeiterPasswortTextfield = new JTextField(20);
        einfügeButton = new JButton("Einfügen");
        einfügeButton.addActionListener(new EinfügenListener());
        backButton = new JButton("zurück");
        backButton.addActionListener(new BackListener());
        einfügePanel.add(mitarbeitereinfügenLabel, "wrap");
        einfügePanel.add(mitarbeiterNameLabel);
        einfügePanel.add(mitarbeiterNameTextfield, "wrap");
        einfügePanel.add(mitarbeiterPasswortLabel);
        einfügePanel.add(mitarbeiterPasswortTextfield);
        einfügePanel.add(einfügeButton, "wrap");
        einfügePanel.add(backButton);
        einfügePanel.add(alleMitarbeiterButton);



        this.setLayout(new BorderLayout());
        this.add(menüpanel, BorderLayout.NORTH);
        this.add(einfügePanel, BorderLayout.WEST);
        this.add(scrollPane, BorderLayout.EAST);
        this.setVisible(true);


    }

    private class EinfügenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = mitarbeiterNameTextfield.getText();
            String passwort = mitarbeiterPasswortTextfield.getText();

            shop.fuegeMitarbeiterEin(name, passwort);
            try {
                shop.schreibeMitarbeiter();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("Mitarbeiter eingefügt");

        }
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new MitarbeiterPanel(shopClientGUI));
        }
    }

    private class MitarbeiterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Vector alleMitarbeiter = shop.gibAlleMitarbeiter();
            JOptionPane.showMessageDialog(new JFrame(), alleMitarbeiter);
        }
    }
}


