package Client;

import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.util.Scanner;
import java.io.*;
import Console.Colors;
// import javafx.scene.paint.Color;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter; 
    private String username;
    private String[] colorList = {""+Colors.RED, ""+Colors.GREEN, ""+Colors.YELLOW, ""+Colors.BLUE, ""+Colors.MAGENTA, ""+Colors.CYAN};
    private String nameColor = colorList[(int) (Math.random()*6)];
    ArrayList<String> messagesSent = new ArrayList<>();
    
    
    public static void main(String[] args) {
        Scanner scannerUsername = new Scanner(System.in);
        
        System.out.print("\033[H\033[2J"); 
        System.out.flush();  
        
        System.out.print("Digite seu username: ");
        String username = scannerUsername.nextLine().trim();
        try {
            System.out.print("Digite o IP: ");
            Scanner askIP = new Scanner(System.in);
            Socket socket = new Socket(askIP.nextLine(), 2222);
            // Socket socket = new Socket("localhost", 2222);

            Client client = new Client(socket, username);
            System.out.println(Colors.MAGENTA + "Você logou no Servidor 2222");
            System.out.print(Colors.BLUE + "Tente digitar algo!: " + Colors.RESET);

            client.listenForMessage();
            client.sendMessage();
            
        }
        catch (IOException e ) {
            e.printStackTrace();
            main (args);
        }
        
    }
    public Client(Socket socket, String username) {
        try {
            
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter( new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;
            
        }
        catch(IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                // System.out.print(nameColor + username + ": " + Colors.RESET);
                String messageToSend = scanner.nextLine();

                bufferedWriter.write(nameColor + username + Colors.RESET + ": " + messageToSend);
                messagesSent.add(nameColor + username + ": " + Colors.RESET + messageToSend);
                    
                handleNewMessages();
            
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }
        catch(IOException e) { 
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
    public void listenForMessage() {
        new Thread(new Runnable() {
        @Override
        public void run() {
            String messageFromGroupChat;

            while (socket.isConnected()) {
                try {
                    messageFromGroupChat = bufferedReader.readLine();
                    
                    
                    messagesSent.add(messageFromGroupChat);

                    handleNewMessages();

                    // System.out.println(messageFromGroupChat);
                }
                catch(IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);

                }
            }
        }
        }).start();
    }

    public void handleNewMessages() {

        System.out.print("\033[H\033[2J");
        System.out.flush();

        for (int i = 0; i < messagesSent.size(); i++)
            System.out.println(messagesSent.get(i));
        System.out.print(nameColor + username + ": " + Colors.RESET);
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    
}
