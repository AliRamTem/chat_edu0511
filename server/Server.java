package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList<Socket> users = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(8188);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("client connect");
                users.add(socket);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                            DataInputStream input = new DataInputStream(socket.getInputStream());
                            out.writeUTF("Welcome to server");
                            while (true) {
                                String request = input.readUTF();
                                for (Socket user : users) {
                                    DataOutputStream userOut = new DataOutputStream(user.getOutputStream());
                                    userOut.writeUTF(request.toUpperCase());
                                }
                            }
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
