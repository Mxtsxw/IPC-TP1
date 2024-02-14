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

    private void close() throws IOException {
        this.clientSocket.close();
    }

    /**
     * Méthode contenant toutes les instructions du serveur
     */
    private void handle() throws IOException {
        while (in.readLine() != null) {
            // Envoyer la réponse au client
            out.write("PONG\n".getBytes());
            out.flush();
        }
    }
}
