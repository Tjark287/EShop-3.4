package shop.local.ui.gui.panel;


import com.sun.corba.se.impl.orbutil.concurrent.Sync;
import shop.local.domain.Shop;
import net.miginfocom.swing.MigLayout;
import shop.local.domain.objects.Kunde;
import shop.local.domain.objects.Mitarbeiter;
import shop.local.ui.cui.ShopClient;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.kundenmenü.KundenPanel;
import shop.local.ui.gui.panel.mitarbeiterMenue.MitarbeiterPanel;
import sun.rmi.runtime.Log;
import shop.local.ui.gui.panel.registrieren.MitarbeiterRegistrierungsPanel;
import shop.local.ui.gui.panel.registrieren.KundenRegistrierungsPanel;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.io.Serializable;


public class LoginPanel extends JPanel implements Serializable {


    private Shop eshop;

    private JTextField benutzerNameLogin;        // Textfeld fuer den Loginnamen
    private JPasswordField loginPasswort;    // Textfeld für das Passwort
    private JButton loginButton = null;        // Button zum einloggen
    private JButton registerButton = null;     // Button um sich neu zu registrieren
    private ShopClientGUI shopClientGUI = null;


    public LoginPanel(ShopClientGUI shopGUI) {
        super();

        eshop = shopGUI.geteShopVerwaltung();
        shopClientGUI = shopGUI;


        benutzerNameLogin = new JTextField(20);
        loginPasswort = new JPasswordField(20);
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginListener());
        registerButton = new JButton("Neu Registrieren ");
        registerButton.addActionListener(new RegisterListener());


        JPanel LoginPanel = new JPanel();

        LoginPanel.setLayout(new MigLayout());
        LoginPanel.setBorder(BorderFactory.createTitledBorder("Login"));

        JLabel benutzerName = new JLabel("Name");
        JLabel passwort = new JLabel("Passwort");



        LoginPanel.add(benutzerName);
        LoginPanel.add(benutzerNameLogin, "wrap");
        LoginPanel.add(passwort);
        LoginPanel.add(loginPasswort);
        LoginPanel.add(loginButton, "wrap");
        LoginPanel.add(registerButton);


        this.setLayout(new BorderLayout());
        this.add(LoginPanel, BorderLayout.CENTER);
        this.setSize(1024, 800);
        this.setVisible(true);

    }


    public String getLoginName() {
        return benutzerNameLogin.getText();
    }

    /**
     * Gibt das eingegebende LoginPassword als String zurueck
     *
     * @return
     */
    public String getLoginPassword() {

        return loginPasswort.getText();

    }

    public void wechselListener(ActionListener a) {
        a = null;
        loginButton.addActionListener(a);
        registerButton.addActionListener(a);
    }

    /**
     * Löscht alle JTextfields des LoginPanels
     */


    private class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String benutzerName = benutzerNameLogin.getText();
            String Passwort = loginPasswort.getText();

            Kunde kunde;
            Mitarbeiter mitarbeiter;

            kunde= eshop.sucheNachKunde(benutzerName);
            mitarbeiter= eshop.sucheNachMitarbeiter(benutzerName);


            if(eshop.LoginAbfrageK(benutzerName,Passwort)) {
                shopClientGUI.reInitialize(new KundenPanel(shopClientGUI));
                resetJTextfields();


            } else if (eshop.LoginAbfrageM(benutzerName, Passwort)) {
                shopClientGUI.reInitialize(new MitarbeiterPanel(shopClientGUI));
                resetJTextfields();

            }else

            {
                //JOptionPane.showInputDialog(shopClientGUI, "Name oder Passwort falsch","Hoppla", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(new JFrame(), "Name oder Passwort falsch");
            }
            resetJTextfields();
        }
    }

    private class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("jkk");

            Object[] options = {"Mitarbeiter", "Kunde"};
             int result = JOptionPane.showOptionDialog(new JPanel(),
                    "Welche Art der Registrierung wollen sie durchführen?",
                    "Registrierung",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,     //do not use a custom Icon
                    options,  //the titles of buttons
                    options[0]); //default button title

            // Wird im Dialog Mitarbeiter ausgewählt wird der Nutzer zur Mitarbeiter Registrierung weiter geleitet
            if (result == JOptionPane.YES_OPTION) {


                shopClientGUI.reInitialize(new MitarbeiterRegistrierungsPanel(shopClientGUI));
                resetJTextfields();

                // Wird im Dialog Kunde ausgewählt wird der Nutzer zur Kunden Registrierung weiter geleitet
            } else if (result == JOptionPane.NO_OPTION){


                  shopClientGUI.reInitialize(new KundenRegistrierungsPanel(shopClientGUI));
                System.out.println("Kundenpanel");

            }else

            shopClientGUI.reInitialize(new LoginPanel(shopClientGUI));
            resetJTextfields();

        }

    }
    public void resetJTextfields() {
        benutzerNameLogin.setText("");
        loginPasswort.setText("");
    }
}






