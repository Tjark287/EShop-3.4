package shop.local.ui.gui.panel.mitarbeiterMenue;


import com.sun.javafx.iio.jpeg.JPEGImageLoader;
import com.sun.xml.internal.bind.v2.TODO;
import jdk.nashorn.internal.scripts.JO;
import shop.local.domain.Exceptions.AnzahlEntsprichtNichtPackungsgrößeException;
import shop.local.domain.Exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.Exceptions.ArtikelExistiertNichtException;
import shop.local.domain.Exceptions.MitarbeiterExistiertBereitsException;
import shop.local.domain.Shop;
import shop.local.domain.objects.Artikel;
import net.miginfocom.swing.MigLayout;


import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.LoginPanel;
import shop.local.ui.gui.panel.TableModels.ArtikelTableModel;
import shop.local.ui.gui.panel.kundenmenü.KundenPanel;
import shop.local.ui.gui.panel.mitarbeiterMenue.verwaltungen.BestandsPanel;
import shop.local.ui.gui.panel.mitarbeiterMenue.verwaltungen.KundenVerwaltungPanel;
import shop.local.ui.gui.panel.mitarbeiterMenue.verwaltungen.LogsVerwaltungsPanel;
import shop.local.ui.gui.panel.mitarbeiterMenue.verwaltungen.MitarbeiterVerwaltungPanel;

import java.awt.*;


import javax.swing.*;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MitarbeiterPanel extends JPanel {

    private Shop shop;
    private ShopClientGUI shopClientGUI;

    private JScrollPane scrollPane;
    private JList ArtikelListe;
    private JTable ArtikelTabelle;
    private RowSorter<ArtikelTableModel> sorter;
    private ArtikelTableModel tModel;
    private KundenPanel.SearchResultListener searchListener = null;

    //ArtikelAuffüllen
    private JLabel artikelAuffüllenLabel;
    private JLabel artikelNAmeAuffüllenLabel;
    private JLabel artikelAnzahlLabel;
    private JTextField artikelAnzahlTextfield;
    private JTextField artikelAuffüllenTextfield;
    private JButton artikelAuffüllenButton;
    //MassengutEinfügen
    private JLabel massengutEinfügenLabel;
    private JLabel massengutNameLabel;
    private JLabel massengutAnzahlLabel;
    private JLabel massengutPreisLabel;
    private JLabel massengutPackungLabel;
    private JTextField massengutNameTextfield;
    private JTextField massengutAnzahlTextfield;
    private JTextField massengutPreisTextfield;
    private JTextField massengutPackungTextfield;
    //ArtikelEinfügen
    private JLabel artikelEinfuegeLabel;
    private JLabel suchbegriffLabel;
    private JLabel neuerPreisLabel;
    private JLabel neueAnzahlLabel;
    private JLabel neuerArtikelLabel;
    //ArtikelLöschen
    private JLabel artikelLöschenLabel;
    private JLabel artikelNameLöschenLabel;
    private JTextField artikelNameLöschenTextfield;
    private JButton artikelLöschenButton;

    private JTextField neuerArtikelTextfield;
    private JTextField neuerPreisTextLabel;
    private JTextField neueAnzahlTextfieeld;
    private JTextField suchTextfield;
    private JButton suchButton;



    private JButton warenkorbButton;
    private JButton logoutButton;
    private JButton backbutton;
    private JButton einfügenArtikelButton;
    private JButton einfügenMassenGutButton;
    private JButton alleKundenButton;
    private JButton alleMitarbeiter;
    private JButton alleLogs;

    private JButton bestand;




    public MitarbeiterPanel(ShopClientGUI shopGui) {
        super();
        shop = shopGui.geteShopVerwaltung();
        shopClientGUI = shopGui;
        initialize();

    }





    private void initialize()  {





        JFrame LayeredPane =new JFrame();

        // MenüPanel
        JPanel menuePanel = new JPanel();
        menuePanel.setBorder(BorderFactory.createTitledBorder("MenüLeiste"));
        LayeredPane.add(menuePanel);


        menuePanel.setLayout(new MigLayout("", "[] 10[]10[]10[]10[]10[]", "[] [] []" ));

        suchbegriffLabel = new JLabel("Suchbegriff");
        suchTextfield = new JTextField(20);
        suchButton = new JButton("Suchen");
        suchButton.addActionListener(new SuchListener());
        warenkorbButton = new JButton("Warenkorb");
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new logoutListener());
        alleKundenButton = new JButton("Kunden Verwaltung");
        alleKundenButton.addActionListener(new KundenListener());
        alleMitarbeiter = new JButton("Mitarbeiter Verwaltung");
        alleMitarbeiter.addActionListener(new MitarbeiterListener());
        alleLogs = new JButton("Logs");
        alleLogs.addActionListener(new LogListener());
        bestand = new JButton("Bestand");
        bestand.addActionListener(new BestandListener());



        menuePanel.add(suchbegriffLabel, "left, sg 1, split");
        menuePanel.add(suchTextfield);
        menuePanel.add(suchButton);
        menuePanel.add(alleKundenButton);
        menuePanel.add(alleMitarbeiter);
        menuePanel.add(alleLogs);
        menuePanel.add(bestand);
        menuePanel.add(logoutButton);

        //EInfügePanel
        JPanel einfuegePanel = new JPanel();
        einfuegePanel.setBorder(BorderFactory.createTitledBorder("Artikel Verwaltung"));
        LayeredPane.add(einfuegePanel);

        einfuegePanel.setLayout(new MigLayout("", "", "[][] " ));


        JLabel platzhalter1 = new JLabel();
        JLabel platzhalter2 = new JLabel();
        JLabel platzhalter3 = new JLabel();


        //Anzahl ändern
        artikelAuffüllenLabel = new JLabel("Artikelbestand auffüllen");
        artikelNAmeAuffüllenLabel = new JLabel("Artikel:");
        artikelAuffüllenTextfield = new JTextField(20);
        artikelAnzahlLabel = new JLabel("Anzahl:");
        artikelAnzahlTextfield = new JTextField(20);
        artikelAuffüllenButton = new JButton("Auffüllen");
        artikelAuffüllenButton.addActionListener(new AuffüllButtonListener());

        //ArtikelLöschen
        artikelLöschenLabel = new JLabel("Artikel löschen");
        artikelNameLöschenLabel = new JLabel("Artikel der gelöscht werden soll");
        artikelNameLöschenTextfield = new JTextField(20);
        artikelLöschenButton = new JButton("Löschen");
        artikelLöschenButton.addActionListener(new LöschenListener());




        artikelEinfuegeLabel = new JLabel("Artikel Einfügen");
        neuerArtikelLabel = new JLabel("Artikelname:");
        neuerPreisLabel = new JLabel("Preis:");
        neueAnzahlLabel = new JLabel("Anzahl:");
        neuerArtikelTextfield = new JTextField(20);
        neuerPreisTextLabel = new JTextField(20);
        neueAnzahlTextfieeld= new JTextField(20);
        einfügenArtikelButton = new JButton(" Artikel einfügen");
        einfügenArtikelButton.addActionListener(new ArtikelEinfügeListener());

        massengutEinfügenLabel = new JLabel("Massengut einfügen");
        massengutNameLabel = new JLabel("Artikelname:");
        massengutPreisLabel = new JLabel("Preis:");
        massengutAnzahlLabel = new JLabel("Anzahl:");
        massengutPackungLabel = new JLabel("Packungsgröße:");
        massengutNameTextfield = new JTextField(20);
        massengutAnzahlTextfield = new JTextField(20);
        massengutPackungTextfield= new JTextField(20);
        massengutPreisTextfield = new JTextField(20);
        einfügenMassenGutButton = new JButton("Massengut einfügen");
        einfügenMassenGutButton.addActionListener(new MassengutEinfügeListener());

        //Artikel einfügen
        einfuegePanel.add(artikelEinfuegeLabel,"wrap");
        einfuegePanel.add(neuerArtikelLabel);
        einfuegePanel.add(neuerArtikelTextfield, " wrap");
        einfuegePanel.add(neuerPreisLabel);
        einfuegePanel.add(neuerPreisTextLabel, " wrap");
        einfuegePanel.add(neueAnzahlLabel);
        einfuegePanel.add(neueAnzahlTextfieeld, "wrap");
        einfuegePanel.add(einfügenArtikelButton, "wrap");
        einfuegePanel.add(platzhalter1, "wrap");
        einfuegePanel.add(platzhalter2, "wrap");
        einfuegePanel.add(platzhalter3, "wrap");
        //Massengut einfügen
        einfuegePanel.add(massengutEinfügenLabel,"wrap");
        einfuegePanel.add(massengutNameLabel);
        einfuegePanel.add(massengutNameTextfield, "wrap");
        einfuegePanel.add(massengutAnzahlLabel);
        einfuegePanel.add(massengutAnzahlTextfield, "wrap");
        einfuegePanel.add(massengutPreisLabel);
        einfuegePanel.add(massengutPreisTextfield, "wrap");
        einfuegePanel.add(massengutPackungLabel);
        einfuegePanel.add(massengutPackungTextfield, "wrap");
        einfuegePanel.add(einfügenMassenGutButton,"wrap");
        einfuegePanel.add(platzhalter1, "wrap");
        einfuegePanel.add(platzhalter2, "wrap");
        einfuegePanel.add(platzhalter3, "wrap");
        //Artikel löschen
        einfuegePanel.add(artikelNameLöschenLabel, "wrap");
        einfuegePanel.add(artikelLöschenLabel);
        einfuegePanel.add(artikelNameLöschenTextfield, "wrap");
        einfuegePanel.add(artikelLöschenButton,"wrap");
        einfuegePanel.add(platzhalter1, "wrap");
        einfuegePanel.add(platzhalter2, "wrap");
        einfuegePanel.add(platzhalter3, "wrap");
        // Artikel Auffüllen
        einfuegePanel.add(artikelAuffüllenLabel,"wrap");
        einfuegePanel.add(artikelNAmeAuffüllenLabel);
        einfuegePanel.add(artikelAuffüllenTextfield, "wrap");
        einfuegePanel.add(artikelAnzahlLabel);
        einfuegePanel.add(artikelAnzahlTextfield, "wrap");
        einfuegePanel.add(artikelAuffüllenButton);



        //ArtikelListe

        java.util.List<Artikel> artikelListe = shop.gibAlleArtikel();
        ArtikelTableModel tModel = new ArtikelTableModel(artikelListe);
       //  JTable erzeugen (und in ScrollPane platzieren):
        ArtikelTabelle = new JTable(tModel);
        scrollPane = new JScrollPane(ArtikelTabelle);
        sorter = new TableRowSorter<ArtikelTableModel>(tModel);
        ArtikelTabelle.setRowSorter(sorter);

        // South
        JPanel unten = new JPanel();
        unten.setLayout(new MigLayout());

        JLabel copy = new JLabel("Copyright 2018");
        JLabel imp = new JLabel("Impressum");

        unten.add(copy);
        unten.add(imp);


        // Layout zusammenbauen
        this.setLayout(new BorderLayout());
        this.add(menuePanel, BorderLayout.NORTH);
        this.add(einfuegePanel, BorderLayout.WEST);
        this.add(scrollPane,BorderLayout.CENTER);
        this.add(unten, BorderLayout.SOUTH);

        this.setSize(1024, 800);
        this.setVisible(true);





    }


    private class ArtikelEinfügeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = neuerArtikelTextfield.getText();
            String preis = neuerPreisTextLabel.getText();
            String anzahl = neueAnzahlTextfieeld.getText();
            String s = String.valueOf(preis);


            try {
                shop.fuegeArtikelein(name, Integer.parseInt(anzahl),Float.parseFloat(preis));
                try {
                    shop.schreibeArtikel();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (ArtikelExistiertBereitsException e1) {
                e1.printStackTrace();
            }

            resetArtikelJTextfields();
            shopClientGUI.reInitialize(new MitarbeiterPanel(shopClientGUI));

        }
    }

    private class MassengutEinfügeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = massengutNameTextfield.getText();
            String anzahl = massengutAnzahlTextfield.getText();
            String preis = massengutPreisTextfield.getText();
            String packung = massengutPackungTextfield.getText();

            try {
                shop.fuegeMassengutArtikelein(name,Integer.parseInt(anzahl),Float.parseFloat(preis),Integer.parseInt(packung));
                try {
                    shop.schreibeArtikel();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (ArtikelExistiertBereitsException e1) {
                e1.printStackTrace();
            } catch (AnzahlEntsprichtNichtPackungsgrößeException e1) {
                e1.printStackTrace();
            }
            System.out.println(" Massengut einfügen Funktions anpassen!");
            resetMassengutJTextfields();
            shopClientGUI.reInitialize(new MitarbeiterPanel(shopClientGUI));
        }
    }

    public void resetMassengutJTextfields() {
        massengutNameTextfield.setText("");
        massengutPreisTextfield.setText("");
        massengutPackungTextfield.setText("");
        massengutAnzahlTextfield.setText("");
    }

    public void resetArtikelJTextfields() {
        neueAnzahlTextfieeld.setText("");
        neuerPreisTextLabel.setText("");
        neuerArtikelTextfield.setText("");
        }

    private class logoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO Speichern
            try {
                shop.schreibeLog();
                shop.schreibeKunden();
                shop.schreibeMitarbeiter();
                shop.schreibeArtikel();
            } catch (IOException e1) {
                e1.printStackTrace();
            }


            shopClientGUI.reInitialize(new LoginPanel(shopClientGUI));

        }
    }

    private class LöschenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO Artikel Löschen Einfügen
            String artikename = artikelNameLöschenTextfield.getText();
            shop.loescheArtikel(artikename);
            artikelNameLöschenTextfield.setText("");
            try {
                shop.schreibeArtikel();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            shopClientGUI.reInitialize(new MitarbeiterPanel(shopClientGUI));

            System.out.println("Artikel Gelöscht?!");
        }
    }

    private class AuffüllButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = artikelAuffüllenTextfield.getText();
            String anzahl = artikelAnzahlTextfield.getText();

            try {
                shop.plusAnzahl(name, Integer.parseInt(anzahl));
                artikelAuffüllenTextfield.setText("");
                artikelAuffüllenTextfield.setText("");
                shopClientGUI.reInitialize(new MitarbeiterPanel(shopClientGUI));
            } catch (ArtikelExistiertNichtException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, e1.getMessage());
            }

        }
    }

    private class KundenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new KundenVerwaltungPanel(shopClientGUI));

        }
    }

    private class MitarbeiterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new MitarbeiterVerwaltungPanel(shopClientGUI));
        }
    }

    private class LogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new LogsVerwaltungsPanel(shopClientGUI));

        }
    }

    private class BestandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new BestandsPanel(shopClientGUI));
        }
    }

    private class SuchListener implements ActionListener {
        @Override
        //TODO nullPointerException beheben
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(suchButton)) {
                String suchbegriff = suchTextfield.getText();
                java.util.List<Artikel> suchErgebnis;

                if (suchbegriff.isEmpty()) {
                    suchErgebnis = shop.gibAlleArtikel();
                    tModel.setArtikel(suchErgebnis);
                    searchListener.onSearchResult(suchErgebnis);
                } else {
                    try {
                        suchErgebnis = shop.sucheNachArtikelByString(suchbegriff);
                        tModel.setArtikel(suchErgebnis);
                        searchListener.onSearchResult(suchErgebnis);
                    } catch (ArtikelExistiertNichtException e1) {
                        JOptionPane.showMessageDialog(new JFrame(),e1.getMessage());
                    }

//                    JOptionPane.showMessageDialog(new JFrame(), "Artikel nicht gefunden");
                }


            }
        }

    }
}





