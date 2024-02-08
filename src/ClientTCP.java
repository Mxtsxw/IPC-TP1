import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTCP {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 1234);

            // Créer un flux d'entrée pour lire les données envoyées par le serveur
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Lire et afficher la réponse du serveur
            String serverResponse = in.readLine();
            System.out.println("Réponse du serveur : " + serverResponse);

            // Fermer la connexion avec le serveur
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
