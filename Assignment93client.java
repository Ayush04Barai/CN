import java.io.*;
import java.net.*;

public class Assignment93client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999); // Connect to server running on localhost:9999

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                // Get user input
                System.out.print("Enter operation (e.g., 10 + 5): ");
                String input = userInput.readLine();

                // Send the input to server
                out.println(input);

                // Receive server response
                String response = in.readLine();
                System.out.println("Server: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
