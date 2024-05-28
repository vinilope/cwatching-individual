package com.cw.conexao;

import com.cw.models.Usuario;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Node {
    public static final Integer PORTA = 37373;

    public static Usuario autenticar() {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Aguardando login...");
            Socket socket = serverSocket.accept();

            Scanner data = new Scanner(socket.getInputStream());
            String json = (String) data.nextLine();

            new Thread(logout).start();
            return new Gson().fromJson(json, Usuario.class);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static final Runnable logout = new Runnable() {
        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(PORTA+1)) {
                System.out.println("a");
                Socket socket = serverSocket.accept();

                new Scanner(socket.getInputStream());
                System.exit(0);
                //TODO: ao invés de sair do programa fazer com que volte a aguardar login

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    };
}
