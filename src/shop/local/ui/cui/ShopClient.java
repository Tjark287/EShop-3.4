package shop.local.ui.cui;

import java.io.*;
import java.util.Collections;
import java.util.Vector;

import shop.local.domain.ArtikelNameComperator;
import shop.local.domain.ArtikelNumberComperator;
import shop.local.domain.Exceptions.*;

import shop.local.domain.Shop;

import shop.local.domain.Warenkorb;
import shop.local.domain.objects.Artikel;
import shop.local.domain.objects.Rechnung;


public class ShopClient {

    private Shop shop;
    private BufferedReader in;

    public ShopClient(String datei) throws IOException, MitarbeiterExistiertBereitsException, ClassNotFoundException {

        shop = new Shop(datei);
//        blabla
        in = new BufferedReader(new InputStreamReader(System.in));
    }


    //StartMenü
    private void gibStartMenueAus() {

        System.out.println("Login");
        System.out.println("\n  Kunde:        'k'");
        System.out.println("\n  Mitarbeiter:        'm'");
        System.out.println("\n  Registrieren:        'r'");
        System.out.println("         \n  Beenden:        'q'");
        System.out.print("> "); // Prompt
        System.out.flush(); // ohne NL ausgeben
    }

    private void verarbeiteEingabeStart(String line) throws IOException {
        String login;
        String passwort;
        String straße;
        String nummer;
        int hausnr;
        int plz;
        String stadt;
        Warenkorb wk;


        switch (line) {

            case "":
                gibStartMenueAus();
            case "m":
                System.out.println("Name  >");
                login = liesEingabe();
                System.out.println("Passwort >");
                passwort = liesEingabe();
                if (shop.LoginAbfrageM(login, passwort) == true) {
                    do {
                        gibMenueAusMi();

                        try {
                            line = liesEingabe();
                            verarbeiteEingabeMi(line);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } while (!line.equals("z"));
                    break;
                } else {
                    System.out.println("Keine Berechtigung oder falscher Nutzername.");
                    break;
                }


            case "k":

                System.out.println("Name  >");
                login = liesEingabe();
                System.out.println("Passwort >");
                passwort = liesEingabe();

                if (shop.LoginAbfrageK(login, passwort) == true) {
                        try {
                        shop.VectorToWarenkorb(shop.aktuellerKundeK());

                        } catch (KundeExistiertNichtException e) {
                            e.getMessage();
                        } catch (WarenkorbExistiertNichtEception e) {
                            e.getMessage();
                        }catch (ArtikelExistiertBereitsException e) {
                            e.getMessage();
                        } catch (ArtikelExistiertNichtException e) {
                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    do {
                            gibMenueAusKu();

                        try {
                            line = liesEingabe();
                            verarbeiteEingabeKu(line);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } while (!line.equals("z"));
                    shop.WarenkorbToVector();
                    break;
                } else {
                    System.out.println("Keine Berechtigung oder falscher Nutzername.");
                }
                break;
            case "r":
                System.out.println("k für Kunde oder m für Mitarbeiter");
                String KoM =liesEingabe();
                if (KoM.equals("k")) {
                    System.out.println("Ihr Name  >");
                    login = liesEingabe();
                    System.out.println("Wunschpasswort >");
                    passwort = liesEingabe();
                    System.out.println("Straße >");
                    straße = liesEingabe();
                    System.out.println("Hausnummer >");
                    nummer = liesEingabe();
                    hausnr = Integer.parseInt(nummer);
                    System.out.println("PLZ >");
                    nummer = liesEingabe();
                    System.out.println("Stadt >");
                    stadt = liesEingabe();
                    plz = Integer.parseInt(nummer);

                    try {
                        shop.fuegeKundenEin(login, passwort, straße, hausnr, plz, stadt);
                    } catch (KundeExistiertBereitsException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Neuer Nutzer mit Login: " + login + " und Passwort: " + passwort);
                    break;
                } else if (KoM.equals("m")) {
                    System.out.println("Ihr Name  >");
                    login = liesEingabe();
                    System.out.println("Wunschpasswort >");
                    passwort = liesEingabe();

                    shop.fuegeMitarbeiterEin(login,passwort);
                    System.out.println("Neuer Mitarbeiter mit Login: " + login + " und Passwort: " + passwort);
                    break;
                }else {
                    System.out.println("Falsche Eingabe");
                }
        }

    }



        /*switch (line) {
            case "k":
                System.out.println("Name  >");
                login = liesEingabe();
                System.out.println("Passwort >");
                passwort = liesEingabe();

                if ((login.equals("kunde")) && (passwort.equals("kunde"))) {
                    try {
                        input = liesEingabe();
                        verarbeiteEingabeKu(input);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                    case "m":
                        System.out.println("Name  >");
                        login = liesEingabe();
                        System.out.println("Passwort >");
                        passwort = liesEingabe();
                        if ((login.equals("mitarbeiter")) && (passwort.equals("mitarbeiter"))) {
                            try {
                                gibMenueAusMi();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                input = liesEingabe();
                                verarbeiteEingabeMi(input);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                }*/


    //Mitarbeiter Menü
    private void gibMenueAusMi() {

        System.out.print("Befehle: \n Artikel ausgeben: 'a'");
        System.out.print("\n Artikel nach Alphabet sortieren: 's'");
        System.out.print("\n Artikel einfügen: 'e'");
        System.out.print("\n Artikelanzahl ändern: 'n' ");
        System.out.print("\n Gib alle Kunden aus: 'k' ");
        System.out.print("\n Gib alle Mitarbeiter aus: 'm' ");
        System.out.print("\n Gib alle Logs aus: 'l' ");
        System.out.print("\n zurück: 'z' ");
        System.out.println("         \n  Shop Beenden:        'q'");
        System.out.print("> "); // Prompt
        System.out.flush(); // ohne NL ausgeben
    }

    private void verarbeiteEingabeMi(String line) throws IOException {
        String nummer;
        int Anzahl;
        String Artikelname;
        Vector liste;
        float preis;
        int zahl;

        gibMenueAusMi();

        switch (line) {
            case "l":
                shop.schreibeAlleLogsAus();
                break;
            case "s":
                shop.SortAlph();
                break;
            case "a":
                liste = shop.gibAlleArtikel();
                gibArtikelListeaus(liste);
                break;
            case "e":
                String artikelart;
                System.out.print("m für MassengutArtikel , a für EinzelArtikel > ");
                artikelart =liesEingabe();
                if (artikelart.equals("a")) {
                    System.out.print("Artikelname  > ");
                    Artikelname = liesEingabe();
                    System.out.print("Preis  >");
                    nummer = liesEingabe();
                    preis = Float.parseFloat(nummer);
                    System.out.print("Anzahl  > ");
                    nummer = liesEingabe();
                    Anzahl = Integer.parseInt(nummer);

                    try {
                        shop.fuegeArtikelein(Artikelname, Anzahl, preis);
                        System.out.println("Einfügen ok");
                    } catch (ArtikelExistiertBereitsException e) {
                        System.out.println("Fehler beim Einfügen");
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                if (artikelart.equals("m")) {
                    System.out.print("Artikelname  > ");
                    Artikelname = liesEingabe();
                    System.out.print("Preis  >");
                    nummer = liesEingabe();
                    preis = Float.parseFloat(nummer);
                    System.out.print("Packungsgröße  > ");
                    nummer = liesEingabe();
                    zahl = Integer.parseInt(nummer);
                    System.out.print("Anzahl  > ");
                    nummer = liesEingabe();
                    Anzahl = Integer.parseInt(nummer);
                    try {
                        shop.fuegeMassengutArtikelein(Artikelname, Anzahl, preis,zahl);
                        System.out.println("Einfügen ok");
                    } catch (ArtikelExistiertBereitsException e) {
                        System.out.println("Fehler beim Einfügen");
                        System.out.println(e.getMessage());
                    } catch (AnzahlEntsprichtNichtPackungsgrößeException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            case "n":
                System.out.print("Artikelname  > ");
                Artikelname = liesEingabe();
                System.out.print("Anzahl  > ");
                try {
                    Anzahl = Integer.parseInt(liesEingabe());
                    AnzahlErhoehen(Artikelname, Anzahl);
                } catch (IOException e) {
                    System.out.println("Falsche Eingabe");
                    e.getMessage();
                }

                break;
            case "k":
               liste= shop.gibAlleKunden();
               gibArtikelListeaus(liste);
                break;
            case "m":
               liste= shop.gibAlleMitarbeiter();
               gibArtikelListeaus(liste);
                break;
        }
    }


    // Mitarbeiter-Funktionen

    public void AnzahlErhoehen(String titel, int anz) {
        try {
            shop.plusAnzahl(titel, anz);
        } catch (ArtikelExistiertNichtException e) {
            e.getMessage();
        }

    }


    //Kunden-Menü


    private void gibMenueAusKu() {

        System.out.print("Befehle: \n Artikel ausgeben: 'a'");
        System.out.print("\n Artikel in Warenkorb legen: 'p'");
        System.out.print("\n Warenkorb anzeigen: 'w' ");
        System.out.print("\n Warenkorb kaufen: 'k'");
        System.out.print("\n Daten sichern: 's'");
        System.out.print("\n zurück: 'z' ");
        System.out.println("         \n  Beenden:        'q'");
        System.out.print("> "); // Prompt
        System.out.flush(); // ohne NL ausgeben

    }

    private void verarbeiteEingabeKu(String line) throws IOException {
        String nummer;
        int Anzahl;
        String Artikelname;
        Vector liste;
        String titel;

        gibMenueAusKu();

        switch (line) {

            case "a":
                System.out.print("Sortierung: alphabetisch: a  numerisch: n ");
                String sort = liesEingabe();
                Vector<Artikel> list =null;

                if (sort.equals("a")){
                    ArtikelNameComperator a = new ArtikelNameComperator();
                    list = shop.gibAlleArtikel();
                    Collections.sort(list, a);
                }
                if (sort.equals("n")) {
                    ArtikelNumberComperator a = new ArtikelNumberComperator();
                    list = shop.gibAlleArtikel();
                    Collections.sort(list, a);
                }
                gibArtikelListeaus(list);
                break;
            case "p":
                try {
                    System.out.print("Artikelname  > ");
                    Artikelname = liesEingabe();
                    System.out.print("Anzahl  > ");
                    nummer = liesEingabe();
                    Anzahl = Integer.parseInt(nummer);

                    try {
                        shop.ArtikelInWarenkorb(Artikelname, Anzahl);
                    } catch (ArtikelExistiertBereitsException e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }

                    System.out.println(Artikelname + " mit Anzahl:" + nummer + " wird in Warenkorb gelegt.");
                    } catch (ArtikelExistiertNichtException e) {
                        System.out.println(e.getMessage());
                }catch(IOException e) {
                    System.out.println(e.getMessage());
                }

            case "w":
                System.out.print("Sortierung: alphabetisch: a  numerisch: n ");
                String sortW = liesEingabe();
                Vector<Artikel> listW =null;

                if (sortW.equals("a")){
                    ArtikelNameComperator a = new ArtikelNameComperator();
                    listW = shop.gibWarenkorbAus();
                    Collections.sort(listW, a);
                }
                if (sortW.equals("n")) {
                    ArtikelNumberComperator a = new ArtikelNumberComperator();
                    listW = shop.gibWarenkorbAus();
                    Collections.sort(listW, a);
                }
                gibArtikelListeaus(listW);
                break;
            case "n":
                System.out.print("Artikelname  > ");
                Artikelname = liesEingabe();
                System.out.print("Anzahl  > ");
                try {
                    Anzahl = Integer.parseInt(liesEingabe());
                    try {
                        shop.aendereAnzahlWarenkorb(Artikelname, Anzahl);
                    } catch (ArtikelExistiertNichtException e) {
                        e.getMessage();
                    }
                } catch (IOException e) {
                    System.out.println("Falsche Eingabe");
                    e.getMessage();
                }

                break;

            case "x":
                try {
                    shop.leereWarenkorb();
                } catch (ArtikelExistiertNichtException e) {
                    e.getMessage();
                }
                System.out.println("Warenkorb: " + shop.gibWarenkorbAus());
                break;
            case "k":
                try {
                    Rechnung rechnung = new Rechnung (shop.aktuellerKundeK().getKundNr(),shop.gibWarenkorbAus());
                System.out.println(rechnung);
                } catch (KundeExistiertNichtException e) {
                    e.getMessage();
                }

                try {
                    shop.loescheWarenkorbUndSchreibeLogs();
                } catch (KundeExistiertNichtException e) {
                   System.out.println(e.getMessage());
                }
                break;
            case "s":
                shop.WarenkorbToVector();
                shop.schreibeArtikel();
                shop.schreibeMitarbeiter();
                shop.schreibeKunden();
                shop.schreibeLog();
                System.out.println("Speichern erfolgreich.");
        }

    }



    private String liesEingabe() throws IOException {
        // einlesen von Konsole
        return in.readLine();
    }



    private void gibArtikelListeaus(Vector liste) {
        System.out.print(liste);
    }


    public void run() throws KundeExistiertBereitsException {
        String input = "";
        do {
            gibStartMenueAus();
            try {
                input = liesEingabe();
                verarbeiteEingabeStart(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!input.equals("q"));




    }


    public static void main(String[] args) throws MitarbeiterExistiertBereitsException, KundeExistiertBereitsException, ClassNotFoundException {
        try {
            ShopClient cui;
            cui = new ShopClient("SHOP");
            cui.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}







