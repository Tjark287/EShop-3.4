package shop.local.ui.gui.panel.kundenmenü.warenkorb;

import net.miginfocom.swing.MigLayout;
import shop.local.domain.Exceptions.ArtikelExistiertNichtException;
import shop.local.domain.Exceptions.KundeExistiertNichtException;
import shop.local.domain.Shop;
import shop.local.domain.objects.Rechnung;
import shop.local.ui.cui.ShopClient;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.LoginPanel;
import shop.local.ui.gui.panel.kundenmenü.KundenPanel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RechnungsPanel extends JPanel {

    private Shop shop;
    private ShopClientGUI shopClientGUI;

    private JPanel headerPanel;
    private JLabel Rechnung;

    private JPanel rechnungPanel;
    private JLabel rechnungsvector;

    private JPanel untenPanel;

    private JPanel bearbeitenPanel;
    private JLabel anzahlÄndernLabel;
    private JLabel artikelNameLabel;
    private JTextField artikelNameTestfield;
    private JLabel wunschAnzahlLabel;
    private JTextField wunschAnzahlTestfield;
    private JButton anzahlÄndernButton;

    private JButton zumLogin;
    private JButton zumShop;
    private JButton zumWarenkorb;
    private JButton kaufButton;



    public RechnungsPanel ( ShopClientGUI shopGUi){
        super();
        shop = shopGUi.geteShopVerwaltung();
        shopClientGUI = shopGUi;
        initialize();
    }

    private void initialize() {

        headerPanel = new JPanel();
        Rechnung = new JLabel("Rechnung");

        rechnungPanel = new JPanel();
        rechnungsvector = new JLabel(String.valueOf(shop.gibWarenkorbAus()));
        zumLogin = new JButton("Zurück zum Login");
        zumLogin.addActionListener(new ZurückLoginListener());

        zumWarenkorb = new JButton("zum Warenkorb");
        zumWarenkorb.addActionListener(new WarenkorbListener());

        zumShop = new JButton("Zum Shop");
        zumShop.addActionListener(new ShopListener());

        kaufButton = new JButton("Kaufen");
        kaufButton.addActionListener(new KaufListener());

        anzahlÄndernButton = new JButton("Ändern");
        anzahlÄndernButton.addActionListener(new ÄndernListener());

        untenPanel = new JPanel();
        bearbeitenPanel = new JPanel();
        bearbeitenPanel.setLayout(new MigLayout());

        anzahlÄndernLabel = new JLabel("Anzahl Ändern");
        artikelNameLabel = new JLabel("Anzahl ändern bei");
        artikelNameTestfield = new JTextField(20);
        wunschAnzahlLabel = new JLabel("Anzahl anpassen (+/-)");
        wunschAnzahlTestfield = new JTextField(20);


        bearbeitenPanel.add(anzahlÄndernLabel,"wrap");
        bearbeitenPanel.add(artikelNameLabel);
        bearbeitenPanel.add(artikelNameTestfield,"wrap");
        bearbeitenPanel.add(wunschAnzahlLabel);
        bearbeitenPanel.add(wunschAnzahlTestfield, "wrap");
        bearbeitenPanel.add(anzahlÄndernButton);

        headerPanel.add(Rechnung);


        rechnungPanel.add(Rechnung);
        rechnungPanel.add(rechnungsvector);

        untenPanel.add(zumShop);
        untenPanel.add(zumWarenkorb);
       // untenPanel.add(zumLogin);
        untenPanel.add(kaufButton);




        this.setLayout(new BorderLayout());
       // this.add(headerPanel, BorderLayout.NORTH);
        this.add(rechnungPanel, BorderLayout.CENTER);
        this.add(bearbeitenPanel, BorderLayout.EAST);
        this.add(untenPanel, BorderLayout.SOUTH);
        this.setSize(1024, 800);
        this.setVisible(true);


    }

    private class ZurückLoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new LoginPanel(shopClientGUI));
        }
    }

    private class ShopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new KundenPanel(shopClientGUI));
        }
    }

    private class WarenkorbListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new KundenWarenkorbPanel(shopClientGUI));
        }
    }

    private class KaufListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //shopClientGUI.reInitialize(new RechnungsPanel(shopClientGUI));
                Rechnung rechnung = new Rechnung (shop.aktuellerKundeK().getKundNr(),shop.gibWarenkorbAus());
               // JOptionPane.showMessageDialog(new JFrame(), rechnung);
                JOptionPane.showMessageDialog(new RechnungsPanel(shopClientGUI), rechnung);
                //System.out.println(rechnung);
                shopClientGUI.reInitialize(new KundenWarenkorbPanel(shopClientGUI));
                shopClientGUI.reInitialize(new KundenWarenkorbPanel(shopClientGUI));
            } catch (KundeExistiertNichtException er) {
                er.getMessage();
            }

            try {
                shop.loescheWarenkorbUndSchreibeLogs();
            } catch (KundeExistiertNichtException el) {
                System.out.println(el.getMessage());
            }

        }

        }

    private class ÄndernListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           String artikelName = artikelNameTestfield.getText();
            String artikelAnzahl = wunschAnzahlTestfield.getText();

            try {
                shop.aendereAnzahlWarenkorb(artikelName, Integer.parseInt(artikelAnzahl) );
                shopClientGUI.reInitialize(new RechnungsPanel(shopClientGUI));
            } catch (ArtikelExistiertNichtException e1) {
                e1.printStackTrace();
            }
        }
    }
}


