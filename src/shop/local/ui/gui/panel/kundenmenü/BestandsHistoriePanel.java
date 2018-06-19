package shop.local.ui.gui.panel.kundenmenü;

import net.miginfocom.swing.MigLayout;
import shop.local.domain.Shop;
import shop.local.ui.gui.ShopClientGUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BestandsHistoriePanel extends JPanel {

    private Shop shop;
    private ShopClientGUI shopClientGUI;

    private JFrame grafikFrame;
    private JPanel grafikPanel;

    private JPanel topPanel;
    private JLabel bestandLayer;



    public BestandsHistoriePanel(ShopClientGUI shopGui) {
        super();
        shop = shopGui.geteShopVerwaltung();
        shopClientGUI = shopGui;
        initialize();

    }



    public void initialize() {
        grafikPanel = new JPanel();
        grafikPanel.setLayout(new MigLayout());

        topPanel = new JPanel();
        topPanel.setLayout(new MigLayout());



        bestandLayer = new JLabel("Bestandshistorie");

        topPanel.add(bestandLayer);




        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(grafikPanel, BorderLayout.CENTER);
        this.setSize(1024, 800);
        this.setVisible(true);
    }
}
