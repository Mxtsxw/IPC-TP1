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

                String request;
                while ((request = in.readLine()) != null) {
                    if (request.equals("CLOSE")) {
                        System.out.println("Connexion avec " + clientSocket.getInetAddress() + " terminée");
                        break;
                    }

                    // Créer un flux de sortie vers le client
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    // Envoyer la réponse au client
                    out.println(getResponse(request));
                    out.flush();
                }

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