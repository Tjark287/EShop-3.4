package shop.local.ui.gui.panel.registrieren;

import shop.local.domain.Exceptions.KundeExistiertBereitsException;
import net.miginfocom.swing.MigLayout;
import shop.local.domain.Shop;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.LoginPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;

public class KundenRegistrierungsPanel extends JPanel  implements Serializable{
    private Shop shop;
    private ShopClientGUI shopClientGUI;

    private JTextField Vorname;
    private JTextField Nachname;
    private JTextField Benutzername;
    private JTextField Straße;
    private JTextField PLZ;
    private JTextField Hausnummer;
    private JTextField Ort;
    private JTextField Passwort;

    private JLabel vorname;
    private JLabel nachname;
    private JLabel benutzername;
    private JLabel straße;
    private JLabel plz;
    private JLabel hausnummer;
    private JLabel ort;
    private JLabel passwort;

    private JButton anlegenButton;
    private JButton backButton;

    private JPanel datenPanel;


    public KundenRegistrierungsPanel(ShopClientGUI shopGUI) {
        super();
        shop = shopGUI.geteShopVerwaltung();
        shopClientGUI = shopGUI;
        initialize();
    }

    public void initialize() {


        JPanel lieferAdresse = new JPanel();
        lieferAdresse.setLayout(new MigLayout());
        JPanel Persönlichedaten = new JPanel();
      //  Persönlichedaten.setLayout(new MigLayout( "[]", "[][][][][][][][]"));
        Persönlichedaten.setLayout(new MigLayout());
        lieferAdresse.setBorder(BorderFactory.createTitledBorder("Lieferadresse"));
        Persönlichedaten.setBorder(BorderFactory.createTitledBorder("Persönliche Daten"));


        vorname = new JLabel("Vorname:");
        nachname = new JLabel("Nachname:");
       // benutzername = new JLabel("Benutzername:");
        straße = new JLabel("Straße und Hausnummer:");
        plz = new JLabel("PLZ:");
        ort = new JLabel("Ort:");
        passwort = new JLabel("Passwort:");


        Vorname = new JTextField(25);
        Nachname = new JTextField(25);
        Benutzername = new JTextField(25);
        Straße = new JTextField(25);
        PLZ = new JTextField(25);
        Ort = new JTextField(25);
        Passwort = new JTextField(25);
        Hausnummer = new JTextField(5);

        anlegenButton = new JButton("Neu Anlegen");
        anlegenButton.addActionListener(new AnlegeListener());
        backButton = new JButton("Zum Login");
        backButton.addActionListener(new BackListener());


        Persönlichedaten.add(vorname, "wrap");
        Persönlichedaten.add(Vorname, "wrap");
        Persönlichedaten.add(nachname, "wrap");
        Persönlichedaten.add(Nachname, "wrap");
        //Persönlichedaten.add(benutzername, "wrap");
       // Persönlichedaten.add(Benutzername, "wrap");
        Persönlichedaten.add(passwort, "wrap");
        Persönlichedaten.add(Passwort, "wrap");
        Persönlichedaten.add(straße, "wrap");
        Persönlichedaten.add(Straße);
        //Persönlichedaten.add(lieferAdresse);
//        Persönlichedaten.add(straße, "wrap");
//        Persönlichedaten.add(Straße, "wrap");
//        Persönlichedaten.add(plz, "wrap");
//        Persönlichedaten.add(Plz, "wrap");
//        Persönlichedaten.add(ort, "wrap");


        lieferAdresse.add(straße, "wrap");
        lieferAdresse.add(Straße);
        lieferAdresse.add(Hausnummer, "wrap");
        lieferAdresse.add(ort, "wrap");
        lieferAdresse.add(Ort, "wrap");
        lieferAdresse.add(plz, "wrap");
        lieferAdresse.add(PLZ, "wrap");
        lieferAdresse.add(anlegenButton);
        lieferAdresse.add(backButton);



//        this.setLayout(new BorderLayout());
//        this.setSize(840, 980);
//
//        this.add(Persönlichedaten, BorderLayout.WEST);
//        this.add(lieferAdresse, BorderLayout.EAST);

        this.setLayout(new BorderLayout());
        //this.setSize(1920, 900);
        this.add(Persönlichedaten, BorderLayout.NORTH);
        this.add(lieferAdresse, BorderLayout.CENTER);


        this.setVisible(true);


    }


    private class AnlegeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String vorname = Vorname.getText();
            String nachname = Nachname.getText();
           // String benutzername = Benutzername.getText();
            String passwort = Passwort.getText();

            String straße = Straße.getText();
            String ort = Ort.getText();
            String plz = PLZ.getText();
            String homenummer = Hausnummer.getText();

            try {
                shop.fuegeKundenEin(vorname, passwort, straße, Integer.parseInt(homenummer), Integer.parseInt(plz), ort);
                shop.schreibeKunden();
                System.out.println("Eingefügt K erfolgreich!");


            } catch (KundeExistiertBereitsException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

        private class BackListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                shopClientGUI.reInitialize(new LoginPanel(shopClientGUI));
            }
        }
    }
