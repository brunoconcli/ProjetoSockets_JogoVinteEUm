package Client;
import java.io.*;
import java.net.Socket;
import Console.Colors;


public class ClientHandler extends MessageHandler {
    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); //Byte Strings wnd with 'Stream', Character String ends with 'Writer'
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();

            ClientManager.clientList.add(this.clientUsername);
            ClientManager.clientHandlers.add(this);

            broadcastMessage(Colors.MAGENTA + "\nServidor: " + clientUsername + " entrou no chat\n" + Colors.RESET); // START COMEÇA DO ZERO PRA CADA USUARIO
            ClientManager.startCount = 0;
        } 
        catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
}
