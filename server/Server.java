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
                            out.writeUTF("Server: Welcome to server");
                            while (true) {
                                String request = input.readUTF();
                                for (Socket user : users) {
                                    DataOutputStream userOut = new DataOutputStream(user.getOutputStream());
                                    if (!user.equals(socket))
                                        userOut.writeUTF(request.split(": ")[0] + ": " + request.split(": ")[1].toUpperCase());
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

/*
1) Спросить у подключившегося пользователя его имя.
2) Рассылая сообщения необходимо писать имя того, от кого сообщение.
3) Не присылать сообщение клиенту который его отправил
*/