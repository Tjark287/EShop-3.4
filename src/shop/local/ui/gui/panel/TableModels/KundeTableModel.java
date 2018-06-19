package shop.local.ui.gui.panel.TableModels;

import shop.local.domain.objects.Kunde;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class KundeTableModel extends AbstractTableModel {

    private List<Kunde> Kunden;
    private String[] spaltenName = {"Stadt", "Straße"};

    public KundeTableModel(List<Kunde> aktuelleKunden){
        super();
        Kunden = new Vector<Kunde>();
        Kunden.addAll(aktuelleKunden);
    }

    public void setKunden(List<Kunde> aktuelleKunden) {
        Kunden.clear();
        Kunden.addAll(aktuelleKunden);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return Kunden.size();
    }

    @Override
    public int getColumnCount() {
        return spaltenName.length;
    }

    @Override
    public String getColumnName(int col) {
        return spaltenName[col];
    }
    @Override
    public Object getValueAt(int row, int col) {
        Kunde gewälterKunde = Kunden.get(row);
        switch (col){
            case 0:
                return gewälterKunde.getStadt();
            case 1:
                return gewälterKunde.getStraße();
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Date.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return Integer.class;
            case 4:
                return String.class;
            default:
                return null;
        }
    }
}
