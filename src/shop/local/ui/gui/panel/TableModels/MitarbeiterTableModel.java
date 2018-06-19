package shop.local.ui.gui.panel.TableModels;

import shop.local.domain.objects.Mitarbeiter;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;
import java.util.Vector;



public class MitarbeiterTableModel  extends AbstractTableModel{

    private List<Mitarbeiter> Mitarbeiters;
    private String [] spaltenNamen = {"Name", "Passwort"};

    public MitarbeiterTableModel(List<Mitarbeiter> akktuelleMitarbeiter) {
        super();
        Mitarbeiters = new Vector<Mitarbeiter>();
        Mitarbeiters.addAll(akktuelleMitarbeiter);
    }

    public void setMitarbeiters(List<Mitarbeiter> aktuelleMitarbeiter){
        Mitarbeiters.clear();
        Mitarbeiters.addAll(aktuelleMitarbeiter);
    }





    @Override
    public int getRowCount() {
        return Mitarbeiters.size();
    }

    @Override
    public int getColumnCount() {
        return spaltenNamen.length;
    }
    @Override
    public String getColumnName(int col){
        return spaltenNamen[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Mitarbeiter gewälterMitarbeiter = Mitarbeiters.get(row);
        switch (col){
            case 0:
                return gewälterMitarbeiter.getName();
            case 1:
                return gewälterMitarbeiter.getPasswort();
            default:
                return null;

        }

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
