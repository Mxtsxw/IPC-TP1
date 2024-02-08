import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientTCP {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 1234);

            // Créer un flux de sortie pour envoyer des commandes au serveur
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Créer un flux d'entrée pour lire les données envoyées par le serveur
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Envoyer les commandes au serveur
            out.println("DATE");
            System.out.println("Réponse du serveur : " + in.readLine());

            out.println("HOUR");
            System.out.println("Réponse du serveur : " + in.readLine());

            out.println("FULL");
            System.out.println("Réponse du serveur : " + in.readLine());

            out.println("CLOSE");
            System.out.println("Connexion fermée.");

            // Fermer la connexion avec le serveur
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
