import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serveurIP = InetAddress.getByName("127.0.0.1");
            int serveurPort = 1234;

            byte[] sendData;
            byte[] receiveData = new byte[1024];

            String message = "Demande d'heure";
            sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serveurIP, serveurPort);
            socket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String heure = new String(receivePacket.getData());
            System.out.println("Heure actuelle reçue du serveur : " + heure.trim());

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
