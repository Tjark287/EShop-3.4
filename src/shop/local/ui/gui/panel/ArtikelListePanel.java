package shop.local.ui.gui.panel;

import shop.local.domain.objects.Artikel;

import java.awt.*;

public class ArtikelListePanel extends List {

    // Diese Klasse ist im Moment noch weitgehend sinnlos, weil Sie der Oberklasse
    // List (aus AWT, nicht aus util!) mit Ausnahme eines hilfreichen Konstruktors
    // keine weiteren Eigenschaften hinzufügt oder Methoden überschreibt.
    // Da das aber demnächst noch kommen wird, habe ich schon mal so eine "Panel"-Klasse angelegt.

    public ArtikelListePanel(java.util.List<Artikel> Artikel) {
        super();

        updateList(Artikel);
    }

    public void updateList(java.util.List<Artikel> Artikel) {
        removeAll();
        for (Artikel b: Artikel) {
            add(b.toString());
        }
    }
}
