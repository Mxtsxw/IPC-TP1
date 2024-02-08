import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalTime;

public class ClientUDP {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serveurIP = InetAddress.getByName("127.0.0.1");
            int serveurPort = 1234;

            byte[] sendData;
            byte[] receiveData = new byte[1024];

            String message = LocalTime.now().toString();
            sendData = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serveurIP, serveurPort);
            socket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData());
            System.out.println("Réponse du serveur : \n" + response);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
