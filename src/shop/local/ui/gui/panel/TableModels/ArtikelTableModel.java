package shop.local.ui.gui.panel.TableModels;

import java.util.List;
import java.util.Vector;
import shop.local.domain.objects.Artikel;

import javax.swing.table.AbstractTableModel;
import shop.local.domain.objects.MassengutArtikel;


import shop.local.domain.objects.Artikel;
import shop.local.domain.objects.MassengutArtikel;



public class ArtikelTableModel extends AbstractTableModel  {

    private List<Artikel> Artikel;
    private List <MassengutArtikel> MassengutArtikelListe;
    private String[] spaltenNamen = { "Title","Preis", "Anzahl", "ArtNr." };



    public ArtikelTableModel(List<Artikel> aktuelleArtikel) {
        super();
        // Ich erstelle eine Kopie der Bücherliste,
        // damit beim Aktualisieren (siehe Methode setBooks())
        // keine unerwarteten Seiteneffekte entstehen.
        Artikel = new Vector<>();
        Artikel.addAll(aktuelleArtikel);
    }

    public void setArtikel(List<Artikel> aktuelleArtikel){
        Artikel.clear();
        Artikel.addAll(aktuelleArtikel);
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
        return Artikel.size();
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
        Artikel gewaehlterArtikel = Artikel.get(row);

        switch (col) {
            case 0:
                return gewaehlterArtikel.getName()+gewaehlterArtikel.getPackung();
            case 1:
                return gewaehlterArtikel.getPreis();
            case 2:
                return gewaehlterArtikel.getAnzahl();
            case 3:
                return gewaehlterArtikel.getArtNr();
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