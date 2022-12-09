package Client;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import javax.naming.PartialResultException;

import Console.Colors;
import Game.Game;

public class MessageHandler implements Runnable {
    
   
    public String[] helpCommandList = 
    {"\n",
        "-  /start : Começa o jogo depois que todos os jogadores estiverem prontos",
        "-  /bother : Escolha um jogador para cochichar",
        "-  /rules : Saiba as regras do jogo Vinte e Um!",
        "-  /credits : Saiba quem são os criadores!",
    "\n"};

    private boolean hasSentStart = false;
    private boolean questionMade = false;
    

    protected Socket socket;
    protected BufferedReader bufferedReader;
    protected BufferedWriter bufferedWriter;
    protected String clientUsername;
    protected ArrayList<String> myDeck = new ArrayList<>();

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = bufferedReader.readLine();
                receiveEspecialMessages(messageFromClient);
            }
            catch(Exception e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }
    public void concealMessage (String messageToSend) {
        for (ClientHandler clientHandler : ClientManager.clientHandlers) {
            try {
                    if (clientHandler.clientUsername.equals(clientUsername)) 
                    {
                        clientHandler.bufferedWriter.write(messageToSend);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    }
                }
            catch( Exception e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    }
    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : ClientManager.clientHandlers) {
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)) 
                {
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }
            catch( IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
            }
        }
    } 
    // ZONA DE PERIGO E CONSERTOS!!
    public void receiveEspecialMessages(String messageToSend) {
        try {
                ClientManager.playerAmount = ClientManager.clientList.size();

                if (messageToSend.contains("/start") && !hasSentStart && (!ClientManager.partidaIniciada || ClientManager.partidaEncerrada)) 
                {
                    hasSentStart = true;
                    myDeck  = new ArrayList<>();
                    
                    if (ClientManager.startCount < ClientManager.playerAmount) 
                    {
                        ClientManager.startCount ++;
                        concealMessage(Colors.WHITE_BOLD + String.format("/start : Aguardando jogadores(%s/%s)", ClientManager.startCount, ClientManager.playerAmount) + Colors.RESET);
                        broadcastMessage(Colors.WHITE_BOLD + String.format("/start : Aguardando jogadores(%s/%s)", ClientManager.startCount, ClientManager.playerAmount) + Colors.RESET);
                        
                    }
                    if (ClientManager.startCount == ClientManager.playerAmount) // every one has already sent '/start'
                    {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                        
                        concealMessage(Colors.BLUE_BOLD + "\nServidor: A partida foi iniciada\n" + Colors.RESET);
                        broadcastMessage(Colors.BLUE_BOLD + "\nServidor: A partida foi iniciada\n" + Colors.RESET);
                        
                        ClientManager.startMatch(); // Partida é iniciada 
                        ClientManager.endCount = 0;
                        ClientManager.whosTurn = 0;

                    }
                }
                else if(messageToSend.contains("/start") && hasSentStart == true) 
                    concealMessage(Colors.WHITE_BOLD + "Você já digitou '/start'" + Colors.RESET);
                

                else if (messageToSend.contains("/deck")) {
                    String message = "Suas cartas são: ";
                    for (int i = 0; i < myDeck.size(); i++) {
                        message += myDeck.get(i) + ", ";
                    }
                    concealMessage(Colors.WHITE_BOLD + message + Colors.RESET);
                } 
                else if (messageToSend.contains("/endGame")) {
                    ClientManager.endMatch();
                    concealMessage(Colors.MAGENTA_BOLD + "\nServidor: A partida foi encerrada\n" + Colors.RESET);
                    broadcastMessage(Colors.MAGENTA_BOLD + "\nServidor: A partida foi encerrada\n" + Colors.RESET);
                }
                else if (messageToSend.contains("/help"))
                {
                    for (int i = 0; i < helpCommandList.length; i++) 
                    {
                        concealMessage(helpCommandList[i]);
                    }
                    concealMessage("");
                }
                else if (messageToSend.contains("hash"))
                {
                    String message = "";
                    for (int i = 0; i<ClientManager.playersInMatch.size(); i++)
                        message += ClientManager.playersInMatch.get(i) + " ";
                    concealMessage("whosTurn: " + ClientManager.whosTurn);
                    concealMessage("playersInMatch.size(): " + message);
                    concealMessage("clientList.size(): " + ClientManager.clientList.size()); 
                }
                else 
                    broadcastMessage(messageToSend); // envia a mensagem normalmente caso não seja um comando especial.            

                if (ClientManager.playersInMatch.get(ClientManager.whosTurn) != "@out@" && ClientManager.whosTurn != ClientManager.playersInMatch.size()) 
                {
                    if (ClientManager.partidaIniciada==true && clientUsername==ClientManager.playersInMatch.get(ClientManager.whosTurn)) { 
                        String palavra;
                        int indexDoisPontos = messageToSend.indexOf(":");
                        palavra = messageToSend.substring(indexDoisPontos+2, indexDoisPontos+4);

                        if (!questionMade) {
                            concealMessage(Colors.CYAN_BOLD + "É a sua vez!\nDeseja pegar uma carta? (SS/NN):" + Colors.RESET);
                            broadcastMessage(Colors.CYAN + "Aguardando a jogada de " + ClientManager.playersInMatch.get(ClientManager.whosTurn) + "..." + Colors.RESET);
                            questionMade = true;
                        }
                        if(palavra.toUpperCase().equals("SS")) {
                            myDeck.add(ClientManager.getNewCard());
                            concealMessage(Colors.WHITE_BOLD + "Você pegou uma nova carta: " + myDeck.get(myDeck.size()-1) + Colors.RESET); // mostra a última carta adiquirida
                            broadcastMessage(Colors.WHITE_BOLD + "\n" + clientUsername + " pegou uma carta \n" + Colors.RESET);
                            questionMade = false;

                            getNextPlayer();
                        }
        
                        else if (palavra.toUpperCase().equals("NN")) {
                            concealMessage(Colors.WHITE_BOLD + "\n Você encerrou sua jogada. Digite \"/deck\" para ver seu baralho. \n" + Colors.RESET);
                            broadcastMessage(Colors.WHITE_BOLD + "\n" + clientUsername + " não pegou a carta e encerrou sua jogada. \n" + Colors.RESET);
                            questionMade = false;
                            int myScore = 0;
                            for(int c = 0; c < myDeck.size(); c++) {
                                myScore += ClientManager.jogo.getCoverted(myDeck.get(c));
                            }

                            ClientManager.playersInMatch.remove(ClientManager.whosTurn); // A variável 'whosTurn' equivale a posição do jogador da vez, logo a posição que será removida.
                            ClientManager.playersScoreList.set(ClientManager.clientList.indexOf(clientUsername), myScore); // Armazena a pontuação do jogador que encerrou sua jogada.
                            hasSentStart = false; 
                            
                            if(ClientManager.whosTurn < ClientManager.playersInMatch.size()-1) {
                                ClientManager.whosTurn = ClientManager.whosTurn;
                            }
                            else 
                                getNextPlayer();

                            
                            // FUNCIONA, MAS INALCANSÁVEL NO MOMENTO
                            if (ClientManager.endCount < ClientManager.playerAmount) {
                                ClientManager.endCount ++;
                                concealMessage(Colors.WHITE_BOLD + String.format("/endCount : Aguardando jogadores(%s/%s)", ClientManager.endCount, ClientManager.playerAmount) + Colors.RESET);
                                broadcastMessage(Colors.WHITE_BOLD + String.format("/endCount : Aguardando jogadores(%s/%s)", ClientManager.endCount, ClientManager.playerAmount) + Colors.RESET);
                            }
                                           
                            if (ClientManager.endCount == ClientManager.playerAmount) {
                                ClientManager.endMatch();
                                concealMessage(Colors.MAGENTA_BOLD + "\nServidor: A partida foi encerrada\n" + Colors.RESET);
                                broadcastMessage(Colors.MAGENTA_BOLD + "\nServidor: A partida foi encerrada\n" + Colors.RESET);   
                                showMatchResult();
                            } 
                        }
                    }
                }
                else {   
                    getNextPlayer();
                }
            }
            catch (Exception e) {
        }
    }

    public void getNextPlayer() {
        if (ClientManager.whosTurn == ClientManager.playersInMatch.size() -1) {
            ClientManager.whosTurn = -1; // se for o último, voltar para o primeiro

        }
        ClientManager.whosTurn++;
    }
    public void showMatchResult() {
        concealMessage(Colors.WHITE_BOLD + "Exibindo os resultados da partida: \n" + Colors.RESET);
        broadcastMessage(Colors.WHITE_BOLD + "Exibindo os resultados da partida: \n" + Colors.RESET);
        for (int p = 0; p < ClientManager.clientList.size(); p++) {
            concealMessage(Colors.WHITE_BOLD + "- " + ClientManager.clientList.get(p) + ": "+ ClientManager.playersScoreList.get(p) +"\n" + Colors.RESET);
            broadcastMessage(Colors.WHITE_BOLD + "- " + ClientManager.clientList.get(p) + ": "+ ClientManager.playersScoreList.get(p) +"\n" + Colors.RESET);
        } 
    
    }
    public void removeClientHandler() 
        {
            ClientManager.clientHandlers.remove(this);
            ClientManager.clientList.remove(ClientManager.clientList.indexOf(clientUsername));
    
            broadcastMessage(Colors.MAGENTA_BOLD + "\nServidor: " + clientUsername + " saiu do chat" + Colors.RESET);
            String message = "Jogadores ("+ ClientManager.clientHandlers.size() +") : ";
    
            for (int i = 0; i < ClientManager.clientList.size(); i++) 
            {
                if (i != ClientManager.clientList.size() - 1)
                    message += ClientManager.clientList.get(i) + ", ";
                else 
                    message += ClientManager.clientList.get(i) + ". ";
            }
            broadcastMessage(message); // Auto digitar NÃO na vez desse jogador 
        }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandler();
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
