package shop.local.ui.gui.panel.mitarbeiterMenue.verwaltungen;

import net.miginfocom.swing.MigLayout;
import shop.local.domain.Shop;
import shop.local.domain.objects.Log;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.panel.TableModels.ArtikelTableModel;
import shop.local.ui.gui.panel.TableModels.LogTableModel;
import shop.local.ui.gui.panel.mitarbeiterMenue.MitarbeiterPanel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import shop.local.domain.LogVerwaltung;


public class LogsVerwaltungsPanel extends JPanel {
    private Shop shop;
    private ShopClientGUI shopClientGUI;

    private JPanel menüpanel;
    private JLabel menuLabel;
    private JScrollPane scrollPane;
    private JButton backButton;
    private LogTableModel tModel;

    private JList LogListe;
    private JTable LogTabelle;
    private RowSorter<LogTableModel> sorter;



    public LogsVerwaltungsPanel( ShopClientGUI shopGui){
        super();
        shop = shopGui.geteShopVerwaltung();
        shopClientGUI = shopGui;
        initialize();

    }



    public void initialize(){
        menüpanel = new JPanel();
        menuLabel = new JLabel("Logs");

        backButton = new JButton("Zurück");
        backButton.addActionListener(new Backlistener());

        menüpanel.add(menuLabel);
        menüpanel.add(backButton);

        scrollPane = new JScrollPane();

        LogListe = new JList();



    java.util.List<Log> loglist = shop.getLogListe();
    tModel = new LogTableModel(loglist);
    //  JTable erzeugen (und in ScrollPane platzieren):
    LogTabelle = new JTable(tModel);
    scrollPane = new JScrollPane(LogListe);
    sorter = new TableRowSorter<LogTableModel>(tModel);
    LogTabelle.setRowSorter(sorter);
    scrollPane = new JScrollPane(LogTabelle);

        this.setLayout(new MigLayout());
        this.add(menüpanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setSize(1024, 800);
        this.setVisible(true);
    }

    private class Backlistener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            shopClientGUI.reInitialize(new MitarbeiterPanel(shopClientGUI));
        }
    }
}

