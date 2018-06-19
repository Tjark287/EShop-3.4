package shop.local.ui.gui.panel.mitarbeiterMenue.verwaltungen;

import net.miginfocom.swing.MigLayout;
import shop.local.domain.Shop;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.mitarbeiterMenue.MitarbeiterPanel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BestandsPanel extends JPanel {


    private Shop shop;
    private ShopClientGUI shopClientGUI;

    private  JPanel headerPanel;
    private JLabel headerLabel;
    private  JButton backButton;
    private JPanel bestandPanel;


    public BestandsPanel( ShopClientGUI shopGui){
        shop = shopGui.geteShopVerwaltung();
        shopClientGUI = shopGui;
        initialize();
    }

    private void initialize(){

        headerPanel = new JPanel();
        headerLabel = new JLabel("Bestand");

        bestandPanel = new JPanel();
        backButton = new JButton("Zurück");
        backButton.addActionListener(new BackListener());

        headerPanel.add(headerLabel);
        bestandPanel.add(backButton);

        this.setLayout(new BorderLayout());
        this.add(headerPanel, BorderLayout.NORTH);
        this.add(bestandPanel, BorderLayout.CENTER);
        this.setVisible(true);

    }

    private class BackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new MitarbeiterPanel(shopClientGUI));
        }
    }
}
