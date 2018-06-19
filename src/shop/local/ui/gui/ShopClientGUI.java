package shop.local.ui.gui;

import shop.local.domain.Exceptions.MitarbeiterExistiertBereitsException;
import shop.local.domain.Shop;
import net.miginfocom.swing.MigLayout;
import shop.local.ui.gui.panel.LoginPanel;
import shop.local.ui.gui.panel.registrieren.KundenRegistrierungsPanel;
import shop.local.ui.gui.panel.registrieren.MitarbeiterRegistrierungsPanel;
import shop.local.ui.gui.panel.registrieren.KundenRegistrierungsPanel;
import shop.local.ui.gui.panel.registrieren.MitarbeiterRegistrierungsPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;

import shop.local.ui.gui.*;
import  shop.local.ui.gui.panel.kundenmenü.*;




public class ShopClientGUI extends JFrame implements Serializable {



        private Shop eShopVerwaltung; // erzeugt eine EShopVerwaltung

        private JPanel switchPanel; // ist das Panel dem die Elemente hinzugefügt und entzogen werden
//        private MenuItem menuDateiSpeichern;     // Menüeintrag zum speichern der Daten
//        private MenuItem menuDateiQuit;          // Menüeintrag zum schließen des EShops
//        private MenuItem menuHelpAbout;


        private JScrollPane scrollPane;

        // Alle Panels
        private LoginPanel addLoginPanel;
        private KundenRegistrierungsPanel addKundenRegistrierungsPanel;
        private MitarbeiterRegistrierungsPanel addMitarbeiterRegistrierungsPanel;
        //Kunden Panels
        private KundenPanel addKundePanel;





    public ShopClientGUI(String datei) throws IOException, MitarbeiterExistiertBereitsException, ClassNotFoundException {
            super(datei);

            eShopVerwaltung = new Shop(datei);



            //Initialisiert die Komponenten
           initialize();




            //Min, Max, Close
            //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            // macht das Frame sichtbar
            // setVisible(true);
        }

    public void reInitialize(JPanel newSwitchPanel) {
        switchPanel.removeAll();
        switchPanel.add(newSwitchPanel);
        switchPanel.revalidate();
    }

    public Shop geteShopVerwaltung() {
        return eShopVerwaltung;
    }

    // Initialisiert die Komponenten
        public void initialize() {
            this.setTitle("EShop Herzlich Willkommen");

           // this.setLayout(new BorderLayout());
            switchPanel = new JPanel();
            switchPanel.setLayout(new BorderLayout());
           // addLoginPanel = (LoginPanel) new JPanel();


            setupMenu();
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
          //  JPanel neuesloginPanel = new LoginPanel(eShopVerwaltung, this);
            //this.setLayout(new BorderLayout());
            addLoginPanel = new LoginPanel(this);
            this.setContentPane(switchPanel);

           switchPanel.add(addLoginPanel, BorderLayout.CENTER);


            this.setSize(1024, 800);
           // this.setSize(this.getPreferredSize());
            this.setVisible(true);

        }


    private void changeLoginPaneltoKunden() {
        this.remove(addLoginPanel);
        this.add(addKundePanel);
        this.revalidate();
    }







//        /**
//         * Intialisiert die Listener für die verschiedenen Action & Mouse-Events
//         */
//        private void initListeners() {
//            // Finale Selbstreferenz (damit GUI-Referenz "this" auch im ActionListener-Kontext verfuegbar ist)
//            final ShopClientGUI ShopClientGUI = this;
//


            /**
             *  Actionlistener - LoginPanel
             */



        private void setupMenu() {

            // Menü definieren ...
            JMenuBar mBar = new JMenuBar();

            JMenu fileMenu = new FileMenu();
            mBar.add(fileMenu);

            JMenu helpMenu = new HelpMenu();
            mBar.add(helpMenu);

            // ... und beim Fenster anmelden
            this.setJMenuBar(mBar);
        }



        public static void main(String[] args) /*throws MitarbeiterExistiertBereitsException, IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException*/ {
// Swing-UI auf dem GUI-Thread initialisieren
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {

                        //ShopClientGUI gui;

                    try {
                        ShopClientGUI gui = new ShopClientGUI("SHOP");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (MitarbeiterExistiertBereitsException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        });
        }


    // Lokale Klasse für Help-Menü
    class HelpMenu extends JMenu implements ActionListener {

        public HelpMenu() {
            super("Hilfe");

            // Nur zu Testzwecken: Menü mit Untermenü
            JMenu m = new JMenu("About");
            JMenuItem mi = new JMenuItem("Programmers");
            mi.addActionListener(this);
            m.add(mi);
            this.add(m);

            // Geht in AWT auch (alternativ zu mi.addActionListener(this)-Aufrufen oben):
//			m.addActionListener(this);
        }


        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
    // Lokale Klasse für File-Menü
    class FileMenu extends JMenu implements ActionListener {

        public FileMenu() {
            super("Datei");

            JMenuItem mi = new JMenuItem("Speichern");
            mi.addActionListener(this);
            this.add(mi);

            // mi = new MenuItem("-");
            // this.add(mi);
            this.addSeparator();

            mi = new JMenuItem("Verlassen");
            mi.addActionListener(this);
            this.add(mi);

            // Geht in AWT auch (alternativ zu mi.addActionListener(this)-Aufrufen oben):
//			this.addActionListener(this);
        }


        @Override
        public void actionPerformed(ActionEvent ae) {
            String command = ae.getActionCommand();
            System.out.println(command);

            if (command.equals("Speichern")) {
                // try {
                //      shop.schriebArtikel();
                // } catch (IOException e) {
                //  e.printStackTrace();
                System.out.println("Fehler beim Speichern!!!");
//                }
//            } else if (command.equals("Verlassen")) {
//                setVisible(false);
//                //dispose();
//
//                System.exit(0);
            }
        }
    }

}


















