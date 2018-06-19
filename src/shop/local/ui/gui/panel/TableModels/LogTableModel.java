package shop.local.ui.gui.panel.TableModels;

import shop.local.domain.objects.Artikel;
import shop.local.domain.objects.Log;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * Erstellt von: angelcastro
 * Datum: angelcastro
 * Projekt: EShop 3.3.2
 **/

public class LogTableModel extends AbstractTableModel {

    private List<Log> Logs;
    private String[] spaltenNamen = {"Datum", "Benutzer", "Artikel", "Anzahl","Vorgang"};


    public LogTableModel(List<Log> aktuelleLogs) {
        super();
        // Ich erstelle eine Kopie der Bücherliste,
        // damit beim Aktualisieren (siehe Methode setBooks())
        // keine unerwarteten Seiteneffekte entstehen.
        Logs = new Vector<Log>();
        Logs.addAll(aktuelleLogs);
    }

    public void setArtikel(List<Log> aktuelleLogs) {
        Logs.clear();
        Logs.addAll(aktuelleLogs);
        fireTableDataChanged();
    }


    /*
     * Ab hier überschriebene Methoden mit Informationen,
     * die eine JTable von jedem TableModel erwartet:
     * - Anzahl der Zeilen
     * - Anzahl der Spalten
     * - Benennung der Spalten
     * - Wert einer Zelle in Zeile / Spalte
     */

    @Override
    public int getRowCount() {
        return Logs.size();
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
        Log gewaehlterLog = Logs.get(row);
        switch (col) {
            case 0:
                return gewaehlterLog.getToday();
            case 1:
                return gewaehlterLog.getBenutzerName();
            case 2:
                return gewaehlterLog.getArtikelName();
            case 3:
                return gewaehlterLog.getStückzahl();
            case 4:
                return gewaehlterLog.getEinOderAuslagerung();
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
