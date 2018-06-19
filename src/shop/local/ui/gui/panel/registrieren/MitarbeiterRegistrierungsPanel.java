package shop.local.ui.gui.panel.registrieren;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import javafx.beans.property.adapter.JavaBeanBooleanProperty;
import net.miginfocom.swing.MigLayout;
import shop.local.domain.Shop;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.LoginPanel;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.security.PrivateKey;

public class MitarbeiterRegistrierungsPanel extends JPanel implements Serializable {

    private Shop shop;
    private JLabel BenutzerNameLogin;
    private JLabel PasswortLogin;
    private JTextField benutzerNameLogin;
    private JTextField passwortLogin;
    private JButton RegistierenButton;
    private JButton BackButton;
    private ShopClientGUI shopClientGUI;


    public MitarbeiterRegistrierungsPanel(ShopClientGUI shopGUi) {
        super();
        shop = shopGUi.geteShopVerwaltung();
        shopClientGUI = shopGUi;
        initialize();
    }

    public void initialize() {



//        JPanel MitarbeiterRegistrierungsPanel = new JPanel();
//        MitarbeiterRegistrierungsPanel.setLayout(new MigLayout());
//        MitarbeiterRegistrierungsPanel.setBorder(BorderFactory.createTitledBorder("Mitarbeiter Registierung"));

        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Mitarbeiter Registierung"));

        BenutzerNameLogin = new JLabel("Name:");
        PasswortLogin = new JLabel("Login Passwort:");



        benutzerNameLogin = new JTextField(25);
        passwortLogin = new JTextField(25);

        RegistierenButton = new JButton("Mitarbeiter anlegen");
        RegistierenButton.addActionListener(new AnlegeListener());

        BackButton = new JButton("Zum Login");
        BackButton.addActionListener(new BackListener());


        panel.add(BenutzerNameLogin, "wrap");
        panel.add(benutzerNameLogin, "wrap");
        panel.add(PasswortLogin, "wrap");
        panel.add(passwortLogin, "wrap");
        panel.add(RegistierenButton);
        panel.add(BackButton);




      this.setLayout(new BorderLayout());
       this.setSize(1024, 800);
        this.add(panel, BorderLayout.CENTER);

        this.setVisible(true);



    }


    private class AnlegeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String benutzername = benutzerNameLogin.getText();
            String passwort = passwortLogin.getText();

            shop.fuegeMitarbeiterEin(benutzername, passwort);
            System.out.println("Einfügen M erfolgreich!");
            try {
                shop.schreibeMitarbeiter();
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
