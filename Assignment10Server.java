/*Write a program using UDP Sockets to enable file transfer (Script, Text, 
Audio and Video one file each) between two machines. 
 */
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Assignment10Server {
    public static void main(String[] args) {
        try {
            DatagramSocket socket = new DatagramSocket(9876); // Server listens on port 9876

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            System.out.println("Server waiting for incoming file...");

            socket.receive(receivePacket); // Receive file data

            // Extract received file data
            byte[] fileData = receivePacket.getData();
            int bytesRead = receivePacket.getLength();

            // Save received file to disk
            String fileName = "received_file.txt"; // Change the file name based on file type
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            fileOutputStream.write(fileData, 0, bytesRead);
            fileOutputStream.close();

            System.out.println("File received and saved: " + fileName);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
