package shop.local.ui.gui.panel.mitarbeiterMenue.verwaltungen;

import jdk.nashorn.internal.scripts.JO;
import net.miginfocom.swing.MigLayout;
import shop.local.domain.Exceptions.KundeExistiertBereitsException;
import shop.local.domain.Shop;
import shop.local.domain.objects.Kunde;
import shop.local.domain.objects.Log;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.TableModels.KundeTableModel;
import shop.local.ui.gui.panel.TableModels.LogTableModel;
import shop.local.ui.gui.panel.TableModels.MitarbeiterTableModel;
import shop.local.ui.gui.panel.mitarbeiterMenue.MitarbeiterPanel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class KundenVerwaltungPanel extends JPanel {

    private Shop shop;
    private ShopClientGUI shopClientGUI;



    private JPanel KundenListePanel;
    private JLabel KundenlisteLayer;
    private JPanel einfügePanel;
    private JPanel menüPanel;

    private JLabel kundenVerwaltungLabel;

    private JLabel einfügenKundenLabel;
    private JLabel einfügenKundenNameLabel;
    private JLabel einfügenKundenPasswortLabel;
    private JLabel einfügenKundenStraßeLabel;
    private JLabel einfügenKundenHausnummerLabel;
    private JLabel einfügenKundenPlzLabel;
    private JLabel einfügenKundenStadtLabel;
    private JTextField einfügenKundenNameTextfield;
    private JTextField einfügenKundenPasswortTextfield;
    private JTextField einfügenKundenStraßeTextfield;
    private JTextField einfügenKundenHausnummerTextfield;
    private JTextField einfügenKundenPlzTextfield;
    private JTextField einfügenKundenStadtTextfield;
    private JButton einfügenKundeButton;

    private JButton backButton;
    private JButton KundenListeButton;



    private JLabel löscheKundeLabel;
    private JTextField löscheKundenTextfield;
    private JTextField einfügenKundenTextfield;

    private JScrollPane scrollPane;
    private KundeTableModel tmode;
    private JList KundenListe;
    private JTable KundenTabelle;
    private RowSorter<KundeTableModel> sorter;



    public KundenVerwaltungPanel(ShopClientGUI shopGui) {
        super();
        shop = shopGui.geteShopVerwaltung();
        shopClientGUI = shopGui;
        initialize();

    }
    private void initialize()  {



        einfügePanel = new JPanel();
        einfügePanel.setLayout(new MigLayout());
        einfügePanel.setBorder(BorderFactory.createTitledBorder("Verwalten"));


        löscheKundeLabel = new JLabel("Kunden löschen");
        löscheKundenTextfield = new JTextField(20);

        einfügenKundenLabel = new JLabel("Kunden neu anlegen");
        einfügenKundenNameLabel = new JLabel("Name:");
        einfügenKundenNameTextfield = new JTextField(20);
        einfügenKundenPasswortLabel = new JLabel("Passwort:");
        einfügenKundenPasswortTextfield = new JTextField(20);
        einfügenKundenStraßeLabel = new JLabel("Straße und Hausnummer:");
        einfügenKundenStraßeTextfield = new JTextField(20);
        einfügenKundenHausnummerTextfield = new JTextField(5);
        einfügenKundenPlzLabel = new JLabel("Plz:");
        einfügenKundenPlzTextfield = new JTextField(20);
        einfügenKundenStadtLabel = new JLabel("Stadt:");
        einfügenKundenStadtTextfield = new JTextField(20);
        einfügenKundeButton = new JButton("Einfügen");
        einfügenKundeButton.addActionListener(new EinfügenListener());

        backButton = new JButton("zurück");
        backButton.addActionListener(new BackListener());

        KundenListePanel = new JPanel();
        KundenlisteLayer = new JLabel();
        KundenListeButton = new JButton("Alle Kunden");
        KundenListeButton.addActionListener(new KundenListener());

        einfügePanel.add(einfügenKundenLabel, "wrap");
        einfügePanel.add(einfügenKundenNameLabel);
        einfügePanel.add(einfügenKundenNameTextfield, "wrap");
        einfügePanel.add(einfügenKundenPasswortLabel);
        einfügePanel.add(einfügenKundenPasswortTextfield, "wrap");
        einfügePanel.add(einfügenKundenStraßeLabel);
        einfügePanel.add(einfügenKundenStraßeTextfield);
        einfügePanel.add(einfügenKundenHausnummerTextfield, "wrap");
        einfügePanel.add(einfügenKundenPlzLabel);
        einfügePanel.add(einfügenKundenPlzTextfield, "wrap");
        einfügePanel.add(einfügenKundenStadtLabel);
        einfügePanel.add(einfügenKundenStadtTextfield);
        einfügePanel.add(einfügenKundeButton);
        einfügePanel.add(backButton, "wrap");
        einfügePanel.add(KundenListeButton);



        scrollPane = new JScrollPane();

        KundenListe = new JList();



        java.util.List<Kunde> kundenlist = shop.getKundenListe();
        tmode = new KundeTableModel(kundenlist);
        //  JTable erzeugen (und in ScrollPane platzieren):
        KundenTabelle = new JTable(tmode);
        scrollPane = new JScrollPane(KundenListe);
        sorter = new TableRowSorter<KundeTableModel>(tmode);
        KundenTabelle.setRowSorter(sorter);
        scrollPane = new JScrollPane(KundenTabelle);










        kundenVerwaltungLabel = new JLabel("Kundenverwaltung");
        menüPanel = new JPanel();
        menüPanel.add(kundenVerwaltungLabel);

        this.setLayout(new BorderLayout());
        this.add(menüPanel, BorderLayout.NORTH);
        this.add(einfügePanel, BorderLayout.WEST);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setSize(1024, 800);
        this.setVisible(true);



    }
    public void KudenListe(){
        shop.gibAlleKunden();
    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new MitarbeiterPanel(shopClientGUI));
        }
    }

    private class EinfügenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = einfügenKundenNameTextfield.getText();
            String passwort = einfügenKundenPasswortTextfield.getText();
            String straße = einfügenKundenStraßeTextfield.getText();
            String hausnummer= einfügenKundenHausnummerTextfield.getText();
            String plz = einfügenKundenPlzTextfield.getText();
            String stadt = einfügenKundenStadtTextfield.getText();

            try {
                shop.fuegeKundenEin(name, passwort, straße, Integer.parseInt(hausnummer),Integer.parseInt(plz), stadt);
            } catch (KundeExistiertBereitsException e1) {
                JOptionPane.showInputDialog(e1);
            }

        }
    }

    private class KundenListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Vector alleKunden = shop.gibAlleKunden();
            JOptionPane.showMessageDialog(new JFrame(), alleKunden);
        }
    }
}
