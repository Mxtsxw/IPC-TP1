import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalTime;

public class ServeurUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(1234);

            byte[] receiveData = new byte[1024];
            byte[] sendData;

            String receivedMessage;

            LocalTime receptionTime;

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                receptionTime = LocalTime.now();

                InetAddress clientIP = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());

                StringBuilder responseBuilder = new StringBuilder();
                responseBuilder.append("Heure d'envoi : ").append(receivedMessage).append(System.lineSeparator());
                responseBuilder.append("Réception du message : ").append(receptionTime.toString()).append(System.lineSeparator());
                responseBuilder.append("Envoi de la réponse : ").append(LocalTime.now()).append(System.lineSeparator());
                sendData = responseBuilder.toString().getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}