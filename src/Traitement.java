import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Classe de traitement | opérations du serveur
 */
public class Traitement implements Runnable {

    ServerSocket serverSocket;
    Socket clientSocket;
    BufferedReader in;
    OutputStream out;


    public Traitement(ServerSocket serverSocket, Socket clientSocket){
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        try {
            // Créer un flux d'entrée et de sortie
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = clientSocket.getOutputStream();

            // Gestion de la requête serveur
            this.handle();

            // Fermer la connexion avec le client
            this.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getResponse(String request){
        return switch (request) {
            case "DATE" -> String.valueOf(LocalDate.now());
            case "HOUR" -> String.valueOf(LocalTime.now().getHour());
            case "FULL" -> String.valueOf(LocalDateTime.now());
            default -> "Requête inconnue";
        };
    }

    private void close() throws IOException {
        this.clientSocket.close();
    }

    /**
     * Méthode contenant toutes les instructions du serveur
     */
    private void handle() throws IOException {
        String request;
        while ((request = in.readLine()) != null) {
            if (request.equals("CLOSE")) {
                System.out.println("Connexion avec " + clientSocket.getInetAddress() + " terminée");
                return;
            }

            // Envoyer la réponse au client
            out.write((getResponse(request) + "\n").getBytes());
            out.flush();
        }
    }
}
