package shop.local.ui.gui.panel.kundenmenü.warenkorb;

import net.miginfocom.swing.MigLayout;
import shop.local.domain.Exceptions.ArtikelExistiertNichtException;
import shop.local.domain.Exceptions.KundeExistiertNichtException;
import shop.local.domain.Shop;
import shop.local.domain.Warenkorb;
import shop.local.domain.objects.Artikel;
import shop.local.domain.objects.Rechnung;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.LoginPanel;
import shop.local.ui.gui.panel.TableModels.ArtikelTableModel;
import shop.local.ui.gui.panel.TableModels.WarenkorbTableModel;
import shop.local.ui.gui.panel.kundenmenü.KundenPanel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class KundenWarenkorbPanel extends JPanel {




    private Shop shop = null;
    private ShopClientGUI shopClientGUI = null;
    private JScrollPane scrollPane;
    private JList WarenkorbListe;
    private JTable WarenkorbTabelle;
    private WarenkorbTableModel tModel;
    private RowSorter<WarenkorbTableModel> wsorter;

    private JButton kaufenButton;
    private JButton zurückButton;
    private JButton warenkorbLeerenButton;
    private JPanel menu;
    private JButton rechnungButton;


    public KundenWarenkorbPanel(ShopClientGUI shopGUI) {
        super();
        shop = shopGUI.geteShopVerwaltung();
        shopClientGUI = shopGUI;
        initialize();
    }


    private void initialize() {



        menu = new JPanel();
        menu.setLayout(new MigLayout());

        kaufenButton = new JButton("Schnellkauf und Rechnung");
        kaufenButton.addActionListener(new KaufListener());

        zurückButton = new JButton("Zurück");
        zurückButton.addActionListener(new BackListener());

        rechnungButton = new JButton("Zur Kasse Rechnung");
        rechnungButton.addActionListener(new RechnungsListener());

        warenkorbLeerenButton = new JButton("Warenkorb leeren");
        warenkorbLeerenButton.addActionListener(new WarenkorbLeerenListener());

        WarenkorbListe = new JList();

        menu.add(warenkorbLeerenButton);
        menu.add(rechnungButton);
        menu.add(kaufenButton, "wrap");
        menu.add(zurückButton);



        // Tabelle
        List<Artikel> warenkorbListe = shop.gibWarenkorbAus();
//        warenkorbListe.add(new Artikel("test",5,3,4));
        tModel = new WarenkorbTableModel(warenkorbListe);
        WarenkorbTabelle = new JTable(tModel);
        scrollPane = new JScrollPane(WarenkorbListe);
        wsorter = new TableRowSorter<WarenkorbTableModel>(tModel);
        WarenkorbTabelle.setRowSorter(wsorter);

        scrollPane = new JScrollPane(WarenkorbTabelle);

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createTitledBorder("Warenkorb"));
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(menu, BorderLayout.SOUTH);
        this.setSize(1024, 800);
        this.setVisible(true);

    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new KundenPanel(shopClientGUI));
        }
    }

    private class KaufListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
               // shopClientGUI.reInitialize(new RechnungsPanel(shopClientGUI));
                Rechnung rechnung = new Rechnung (shop.aktuellerKundeK().getKundNr(),shop.gibWarenkorbAus());
                JOptionPane.showMessageDialog(new JFrame(), rechnung);
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
            shopClientGUI.reInitialize(new KundenWarenkorbPanel(shopClientGUI));
        }
    }

    private class WarenkorbLeerenListener implements ActionListener {
        // TODO Nach dem leeren ohne kauf Artikelbestand hinzufügen
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                shop.leereWarenkorb();
                Vector neuerWarenkorb = shop.gibWarenkorbAus();
                tModel.setWarenkorb(neuerWarenkorb);
//                shop.loescheWarenkorbUndSchreibeLogs();
            } catch (ArtikelExistiertNichtException e1) {
                e1.printStackTrace();
            } /*catch (KundeExistiertNichtException e1) {
                e1.printStackTrace();
            }*/

        }
    }

    private class RechnungsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new RechnungsPanel(shopClientGUI));
        }
    }
}


