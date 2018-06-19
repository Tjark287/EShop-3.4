package shop.local.ui.gui.panel.kundenmenü;

import shop.local.domain.Exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.Exceptions.ArtikelExistiertNichtException;
import shop.local.domain.Shop;
import shop.local.domain.objects.Artikel;
import net.miginfocom.swing.MigLayout;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.LoginPanel;
import shop.local.ui.gui.panel.TableModels.ArtikelTableModel;
import shop.local.ui.gui.panel.TableModels.WarenkorbTableModel;
import shop.local.ui.gui.panel.kundenmenü.warenkorb.KundenWarenkorbPanel;
import shop.local.ui.gui.panel.mitarbeiterMenue.verwaltungen.BestandsPanel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class KundenPanel extends JPanel {



    public interface SearchResultListener {
        public void onSearchResult(List<Artikel> ArtikelListe);
    }

    private Shop shop = null;
    private ShopClientGUI shopClientGUI = null;

    private SearchResultListener searchListener = null;
    private JScrollPane scrollPane;
    private JList ArtikelListe;
    private JTable ArtikelTabelle;
    private ArtikelTableModel tModel;
    private RowSorter<ArtikelTableModel> sorter;

    private JLabel suchbegriff;
    private JLabel kundenLabel;

    private JButton suchButton;
    private JButton warenkorbButton;
    private JButton logoutButton;
    private JButton zurückButton;
    private JButton rechnungButton;
    private JButton bestand;

    private JTextField suchtxt;
    private JButton inWarenkorbButton;

    private JButton bestandshistorieButton;





    public KundenPanel(ShopClientGUI shopGUI) {
        super();
        shop = shopGUI.geteShopVerwaltung();
        shopClientGUI = shopGUI;
        initialize();
    }


    private void initialize() {

        kundenLabel = new JLabel("Kunden Menü");


        // MenüPanel
        JPanel menuePanel = new JPanel();
        menuePanel.setBorder(BorderFactory.createTitledBorder(""));
        menuePanel.setLayout(new MigLayout("", "[] 10[]10[]10[]10[]10[]", "[] [] []"));

        suchbegriff = new JLabel("Suchbegriff");
        suchtxt = new JTextField(20);

        suchButton = new JButton("Suchen");
        suchButton.addActionListener(new SuchListener());

        warenkorbButton = new JButton("Warenkorb");
        warenkorbButton.addActionListener(new WarenkorbListener());

        rechnungButton = new JButton("Rechnung");
        rechnungButton.addActionListener(new RechnungListener());

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new LogoutListener());

        zurückButton = new JButton("Alle Artikel anzeigen");
        zurückButton.addActionListener(new BackListener());

        ArtikelListe = new JList();
        inWarenkorbButton = new JButton("In den WarenKorb");
        inWarenkorbButton.addActionListener(new InWarenkorbListener());

        bestand = new JButton("Bestand des Artikels");
        bestand.addActionListener(new BestandListener());

        bestandshistorieButton = new JButton("Bestandshistorie");
        bestandshistorieButton.addActionListener(new BestandhistorieListener());

        menuePanel.add(suchbegriff, "left, sg 1, split");
        menuePanel.add(suchtxt);
        menuePanel.add(suchButton);
        menuePanel.add(warenkorbButton);
        menuePanel.add(logoutButton);

        //EInfügePanel
        JPanel einfuegePanel = new JPanel();
        einfuegePanel.setBorder(BorderFactory.createTitledBorder(""));


        einfuegePanel.setLayout(new MigLayout("", " [] ", "[] [] "));



        einfuegePanel.add(zurückButton, "wrap");
        einfuegePanel.add(inWarenkorbButton, "wrap");
        einfuegePanel.add(bestandshistorieButton);


        //ArtikelListe

        List<Artikel> artikelListe = shop.gibAlleArtikel();
        tModel = new ArtikelTableModel(artikelListe);
        // JTable erzeugen (und in ScrollPane platzieren):
        ArtikelTabelle = new JTable(tModel);
        scrollPane = new JScrollPane(ArtikelListe);
        sorter = new TableRowSorter<ArtikelTableModel>(tModel);
        ArtikelTabelle.setRowSorter(sorter);
        //scrollPane.setBorder(BorderFactory.createTitledBorder("Artikelübersicht"));

        // South
        JPanel unten = new JPanel();
        unten.setLayout(new MigLayout());

        JLabel copy = new JLabel("Copyright 2018");
        JLabel imp = new JLabel("Impressum");

        unten.add(copy);
        unten.add(imp);


        scrollPane = new JScrollPane(ArtikelTabelle);

        // Layout zusammenbauen
        this.setLayout(new BorderLayout());
        this.add(menuePanel, BorderLayout.NORTH);
        this.add(einfuegePanel, BorderLayout.WEST);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(unten, BorderLayout.SOUTH);

        this.setSize(1024, 800);
        this.setVisible(true);


    }
    class SuchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(suchButton)) {
                String suchbegriff = suchtxt.getText();
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
    private class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO logour hinzufügen
            try {
                shop.schreibeLog();
                shop.schreibeArtikel();
                shop.schreibeMitarbeiter();
                shop.schreibeKunden();

                shopClientGUI.reInitialize(new LoginPanel(shopClientGUI));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("Logout ");
        }
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO kein Speichen
            shopClientGUI.reInitialize(new KundenPanel(shopClientGUI));
        }
    }

    private class WarenkorbListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO warenkorb Liste erstellen
            shopClientGUI.reInitialize(new KundenWarenkorbPanel(shopClientGUI));
        }
    }

    private class RechnungListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class InWarenkorbListener extends Component implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO Fehlerbehebung BestandsMenge muss geprüft werden beim verschieben in den Warenkorb
            String artikelName = (String) tModel.getValueAt(ArtikelTabelle.getSelectedRow(), 0);
            int anzahl = Integer.parseInt(JOptionPane.showInputDialog("Wie viele?", "1"));
            try {
                shop.ArtikelInWarenkorb(artikelName, anzahl);
                shopClientGUI.reInitialize(new KundenPanel(shopClientGUI));
            } catch (ArtikelExistiertNichtException e1) {
                JOptionPane.showMessageDialog(new JFrame(),"Nicht genug im Lagerbestand");
               // e1.printStackTrace();
            } catch (ArtikelExistiertBereitsException e1) {
                e1.printStackTrace();
                //JOptionPane.showMessageDialog(new JFrame(),e1.getMessage());
            }
        }
    }

    private class BestandListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new BestandsPanel(shopClientGUI));
        }
    }

    private class BestandhistorieListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}

