package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket;
        DataInputStream input;
        DataOutputStream out;
        Scanner scanner;
        String name;
        try {
            System.out.println("Enter your name: ");
            scanner = new Scanner(System.in);
            name = scanner.nextLine();
            socket = new Socket("192.168.1.67", 8188);
            input = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            /*String response = input.readUTF();
            System.out.println(response);*/

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String response = input.readUTF();
                            System.out.println(response);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
            });
            thread.start();

            while (true) {
                String request = scanner.nextLine();
                if (request.equals("exit"))
                    break;
                out.writeUTF(name + ": " + request);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
