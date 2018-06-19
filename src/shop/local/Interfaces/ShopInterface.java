package shop.local.Interfaces;

import shop.local.domain.Exceptions.*;
import shop.local.domain.objects.Artikel;
import shop.local.domain.objects.Kunde;
import shop.local.domain.objects.Log;
import shop.local.domain.objects.Mitarbeiter;

import java.io.IOException;
import java.util.Vector;

/**
 * Erstellt von: angelcastro
 * Datum: angelcastro
 * Projekt: EShop 3.3.3 Kopie
 **/

public interface ShopInterface {
    Vector gibAlleArtikel();

    void SortAlph();

    Vector getVectorOfObjectsDerArtikel();

    void fuegeArtikelein(String titel, int anzahl, float preis) throws ArtikelExistiertBereitsException;

    void fuegeMassengutArtikelein(String titel, int anzahl, float preis, int packGr) throws ArtikelExistiertBereitsException, AnzahlEntsprichtNichtPackungsgrößeException;

    void plusAnzahl(String name, int anzahl) throws ArtikelExistiertNichtException;

    void loescheArtikel(String name);

    Vector gibAlleKunden();

    boolean LoginAbfrageK(String name, String passwort);

    void fuegeKundenEin(String name, String passwort, String straße, int hausnummer, int PLZ, String stadt) throws KundeExistiertBereitsException;

    String aktuellerKunde();

    Kunde aktuellerKundeK() throws KundeExistiertNichtException;

    Vector gibAlleMitarbeiter();

    boolean LoginAbfrageM(String name, String passwort);

    void fuegeMitarbeiterEin(String name, String passwort);

    void VectorToWarenkorb(Kunde k) throws WarenkorbExistiertNichtEception, ArtikelExistiertBereitsException, ArtikelExistiertNichtException;

    void WarenkorbToVector();

    Vector gibWarenkorbAus();

    void aendereAnzahlWarenkorb(String name, int anzahl) throws ArtikelExistiertNichtException;

    void ArtikelInWarenkorb(String name, int anzahl) throws ArtikelExistiertNichtException, ArtikelExistiertBereitsException;

    boolean bestandContainsArtikel (Artikel a);

    void SortAlphW();

    void leereWarenkorb() throws ArtikelExistiertNichtException;

    void loescheWarenkorbUndSchreibeLogs() throws KundeExistiertNichtException;

    float GesamtPreis();

    Vector<Log> getLogListe();

    void schreibeAlleLogsAus();

    Vector<Artikel> sucheNachArtikelByString(String titel) throws ArtikelExistiertNichtException;

    Kunde sucheNachKunde(String name);

    Mitarbeiter sucheNachMitarbeiter(String name);

    void schreibeArtikel() throws IOException;

    void schreibeMitarbeiter() throws IOException;

    void schreibeKunden() throws IOException;

    void schreibeLog() throws IOException;
}
