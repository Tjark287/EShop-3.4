package shop.local.Server;

import shop.local.Interfaces.ShopInterface;
import shop.local.domain.Exceptions.*;
import shop.local.domain.objects.Artikel;
import shop.local.domain.objects.Kunde;
import shop.local.domain.objects.Log;
import shop.local.domain.objects.Mitarbeiter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

/**
 * Erstellt von: angelcastro
 * Datum: angelcastro
 * Projekt: EShop 3.3.3 Kopie
 **/

public class ShopClientRequestProcessor implements Runnable {

    // Bibliotheksverwaltungsobjekt, das die eigentliche Arbeit machen soll
    private ShopInterface si;

    // Datenstrukturen für die Kommunikation
    private Socket clientSocket;
    private BufferedReader in;
    private PrintStream out;

    public ShopClientRequestProcessor(Socket socket, ShopInterface shop) {

        si = shop;
        clientSocket = socket;


        // I/O-Streams initialisieren und ShopClientRequestProcessor-Objekt als Thread starten:
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            try {
                clientSocket.close();
            } catch (IOException e2) {
            }
            System.err.println("Ausnahme bei Bereitstellung des Streams: " + e);
            return;
        }

        System.out.println("Verbunden mit " + clientSocket.getInetAddress()
                + ":" + clientSocket.getPort());


    }

/*---------------------------------------------------------------------------------------------------------------*/

    public void verarbeiteAnfragen() {

        String input = "";

        // Begrüßungsnachricht an den Client senden
        out.println("Server bereit");

        // Hauptschleife zur wiederholten Abwicklung der Kommunikation
        do {
            // Beginn der Benutzerinteraktion:
            // Aktion vom Client einlesen [dann ggf. weitere Daten einlesen ...]
            try {
                input = in.readLine();
            } catch (Exception e) {
                System.out.println("--->Fehler beim Lesen vom Client (Aktion): ");
                System.out.println(e.getMessage());
                continue;
            }

            // Eingabe bearbeiten:
            if (input == null) {
                // input wird von readLine() auf null gesetzt, wenn Client Verbindung abbricht
                // Einfach behandeln wie ein "quit"
                input = "quit";
            } else if (input.equals("MitarbeiterLogin")){
                try {
                    String name = in.readLine();
                    String passwort = in.readLine();
                   if (si.LoginAbfrageM(name,passwort))  {
                       out.println("true");
                   }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (input.equals("KundenLogin")) {
            try {
                String name = in.readLine();
                String passwort = in.readLine();
                if (si.LoginAbfrageK(name,passwort))  {
                    out.println("true");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            }else if (input.equals("ArtikelEinfuegen")) {
                try {
                    String name = in.readLine();
                    String anzahlString = in.readLine();
                    String preisString = in.readLine();

                    int anzahl = Integer.parseInt(anzahlString);
                    Float preis = Float.parseFloat(preisString);

                    si.fuegeArtikelein(name,anzahl,preis);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ArtikelExistiertBereitsException e) {
                    e.printStackTrace();
                }

            }
        } while (!(input.equals("quit")));

        System.out.println("Verbindung zu " + clientSocket.getInetAddress()
                + ":" + clientSocket.getPort() + " durch Client abgebrochen");

        // Verbindung beenden
        try {
            clientSocket.close();
        } catch (IOException e2) {
        }
    }


////Methoden für run()
//
//    private void MitarbeiterLogin() {
//        try {
//            String name = in.readLine();
//            String passwort = in.readLine();
//            if (si.LoginAbfrageM(name,passwort) == true) {
//                System.out.println("Mitarbeiter-Login: "+name);
//                out.println("true");
//
//            }
//            else out.println("false");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }







    @Override
    public void run() {
    verarbeiteAnfragen();
    }


}
