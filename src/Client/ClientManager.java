package Client;
import java.util.ArrayList;
import java.util.Collections;
import Game.Game;

public class ClientManager {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public static ArrayList<String> clientList = new ArrayList<>();

    public static ArrayList<String> playersInMatch = new ArrayList<>();
    public static ArrayList<Integer> playersScoreList = new ArrayList<>();

    public static Game jogo;
    public static boolean partidaIniciada = false;
    public static boolean partidaEncerrada = true;

    public static int playerAmount;
    public static int startCount;
    public static int endCount;
    public static int whosTurn;
    public static int defineScore;


    public static void startMatch() {
        jogo = new Game();
        partidaIniciada = true;
        partidaEncerrada = false;
        endCount = 0;
        
        for (int i = 0; playersInMatch.size() < clientList.size(); i++) {
            playersInMatch.add("");
            playersScoreList.add(0);
        }
        for (int i = 0; i < clientList.size(); i++) {
            playersInMatch.set(i, clientList.get(i));
            playersScoreList.set(i, 0);
        }
    }
    public static String getNewCard() {
        return jogo.getCardBaralho();
    }
    public static void endMatch() {
        partidaEncerrada = true;
        partidaIniciada = false;
        startCount = 0;
        endCount = 0;
        Collections.reverse(clientList);
        Collections.reverse(playersScoreList);

    }
}
