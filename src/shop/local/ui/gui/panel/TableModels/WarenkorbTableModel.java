package shop.local.ui.gui.panel.TableModels;

import shop.local.domain.Warenkorb;
import shop.local.domain.objects.Artikel;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Vector;

public class WarenkorbTableModel extends AbstractTableModel {

    private List<Artikel> Warenkorb;
    private String [] spaltenNamen = {"Title", "Preis", "bestellte Menge", "ArtNr."};


    public WarenkorbTableModel(List<Artikel> akuellerWarenKorb) {
        super();
        Warenkorb = new Vector<Artikel>();
        Warenkorb.addAll(akuellerWarenKorb);
    }

    public void setWarenkorb(List<Artikel> aktuellerWarenorb){
        Warenkorb.clear();
        // Warenkorb.addAll(aktuellerWarenorb);
        fireTableDataChanged();
    }
    @Override
    public int getRowCount() {
        return Warenkorb.size();
    }


    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }

    @Override
    public String getColumnName(int col) {
        return spaltenNamen[col];
    }


     @Override
    public Object getValueAt(int row, int col) {
        Artikel  gewählterWarenkorb = Warenkorb.get(row);
        switch (col){
            case 0:
                return gewählterWarenkorb.getName();
            case 1:
                return gewählterWarenkorb.getPreis();
            case 2:
                return gewählterWarenkorb.getAnzahl();
            case 3:
                return gewählterWarenkorb.getArtNr();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return Float.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            default:
                return null;
        }
    }



}