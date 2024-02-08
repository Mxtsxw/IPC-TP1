import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;

public class ServeurTCP {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            while (true){
                Socket clientSocket = serverSocket.accept();

                // Créer un flux de sortie vers le client
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Envoyer l'heure actuelle au client
                out.println("Heure actuelle du serveur : " + LocalTime.now());
                out.flush();

                // Fermer la connexion avec le client
                clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
