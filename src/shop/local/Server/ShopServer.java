package shop.local.Server;

import shop.local.Interfaces.ShopInterface;
import shop.local.domain.Exceptions.MitarbeiterExistiertBereitsException;
import shop.local.domain.Shop;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Erstellt von: angelcastro
 * Datum: angelcastro
 * Projekt: EShop 3.3.3 Kopie
 **/

public class ShopServer {

    public final static int DEFAULT_PORT = 6789;

    protected int port;
    protected ServerSocket serverSocket;
    private ShopInterface shopInterface;


    /**
     * Konstruktor zur Erzeugung des Bibliotheksservers.
     *
     * @param port Portnummer, auf der auf Verbindungen gewartet werden soll
     * (wenn 0, wird Default-Port verwendet)
     * @throws MitarbeiterExistiertBereitsException
     * @throws ClassNotFoundException
     */
    public ShopServer(int port) throws MitarbeiterExistiertBereitsException, IOException, ClassNotFoundException {

        shopInterface = new Shop("SHOP");

        if (port == 0)
            port = DEFAULT_PORT;
        this.port = port;

        try {
            // Server-Socket anlegen
            serverSocket = new ServerSocket(port);

            // Serverdaten ausgeben
            InetAddress ia = InetAddress.getLocalHost();
            System.out.println("Host: " + ia.getHostName());
            System.out.println("Server: " + ia.getHostAddress()	+ " Port: " + port);
            System.out.println("Waiting for connections...\n");
        } catch (IOException e) {
            fail(e, "Eine Ausnahme trat beim Anlegen des Server-Sockets auf");
        }

        acceptClientConnectRequests();
    }

    /**
     * Methode zur Entgegennahme von Verbindungswünschen durch Clients.
     * Die Methode fragt wiederholt ab, ob Verbindungsanfragen vorliegen
     * und erzeugt dann jeweils ein ShopClientRequestProcessor-Objekt mit dem
     * fuer diese Verbindung erzeugten Client-Socket.
     */
    public void acceptClientConnectRequests() {

        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ShopClientRequestProcessor c = new ShopClientRequestProcessor(clientSocket, shopInterface);
                Thread t = new Thread(c);
                t.start();
            }
        } catch (IOException e) {
            fail(e, "Fehler während des Lauschens auf Verbindungen");
        }
    }


    /**
     * main()-Methode zum Starten des Servers
     *
     * @param args kann optional Portnummer enthalten, auf der Verbindungen entgegengenommen werden sollen
     * @throws MitarbeiterExistiertBereitsException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws MitarbeiterExistiertBereitsException, ClassNotFoundException {
        int port = 0;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                port = 0;
            }
        }
        try {
            new ShopServer(port);
        } catch (IOException e) {
            e.printStackTrace();
            fail(e, " - ShopServer-Erzeugung");
        }
    }

    // Standard-Exit im Fehlerfall:
    private static void fail(Exception e, String msg) {
        System.err.println(msg + ": " + e);
        System.exit(1);
    }
}

