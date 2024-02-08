import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ServeurTCP {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Serveur en attente de connexion...");

            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion établie avec " + clientSocket.getInetAddress());

                // Créer un flux d'entrée pour lire les demandes du client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String request = "";
                try {
                    request = in.readLine();
                } catch (SocketException e) {
                    clientSocket.close();
                    System.out.println("Connexion interrompue");
                }

                // Si le client demande la fermeture de la connexion
                if (request.equals("CLOSE")){
                    clientSocket.close();
                    break;
                }

                // Créer un flux de sortie vers le client
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Envoyer l'heure actuelle au client
                out.println("Heure actuelle du serveur : " + getResponse(request));
                out.flush();

                // Fermer la connexion avec le client
                clientSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getResponse(String request){
        return switch (request) {
            case "DATE" -> String.valueOf(LocalDate.now());
            case "HOUR" -> String.valueOf(LocalTime.now().getHour());
            case "FULL" -> String.valueOf(LocalDateTime.now());
            default -> "Requête inconnue";
        };
    }
}
