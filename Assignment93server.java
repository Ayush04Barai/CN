/*Write a client-server program using TCP socket for wired network to - 
c. Calculator  */
import java.io.*;
import java.net.*;

public class Assignment93server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999); // Create server socket on port 9999
            System.out.println("Calculator Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Wait for client connection
                System.out.println("Client connected: " + clientSocket);

                // Create input and output streams
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // Read client input
                String input = in.readLine();
                System.out.println("Received: " + input);

                // Process the input (simple calculator operation)
                String[] tokens = input.split(" ");
                if (tokens.length != 3) {
                    out.println("Invalid input. Please provide operation in format: operand1 operator operand2");
                } else {
                    try {
                        double operand1 = Double.parseDouble(tokens[0]);
                        double operand2 = Double.parseDouble(tokens[2]);
                        char operator = tokens[1].charAt(0);

                        double result = 0;
                        switch (operator) {
                            case '+':
                                result = operand1 + operand2;
                                break;
                            case '-':
                                result = operand1 - operand2;
                                break;
                            case '*':
                                result = operand1 * operand2;
                                break;
                            case '/':
                                if (operand2 != 0) {
                                    result = operand1 / operand2;
                                } else {
                                    out.println("Division by zero is not allowed.");
                                    continue;
                                }
                                break;
                            default:
                                out.println("Invalid operator. Supported operators are +, -, *, /");
                                continue;
                        }

                        // Send the result back to client
                        out.println("Result: " + result);
                    } catch (NumberFormatException e) {
                        out.println("Invalid input. Please provide numeric operands.");
                    }
                }

                // Close streams and socket
                in.close();
                out.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
