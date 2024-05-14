/*Write a program using UDP Sockets to enable file transfer (Script, Text, 
Audio and Video one file each) between two machines. 
 */
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Assignment10Client {
    public static void main(String[] args) {
        String serverAddress = "127.0.0.1"; // Server IP address
        int serverPort = 9876; // Server port

        try {
            DatagramSocket socket = new DatagramSocket();

            // Specify the file to send
            String filePath = "C:\\Users\\Lenovo\\Desktop\\Computer Network Code-20240514T153059Z-001\\ayush.txt"; // Change this to the file you want to send
            FileInputStream fileInputStream = new FileInputStream(filePath);

            byte[] fileData = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(fileData)) != -1) {
                InetAddress serverIP = InetAddress.getByName(serverAddress);
                DatagramPacket packet = new DatagramPacket(fileData, bytesRead, serverIP, serverPort);
                socket.send(packet);
            }

            fileInputStream.close();

            System.out.println("File sent successfully.");

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}