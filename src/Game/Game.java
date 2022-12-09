package Game;
import java.io.*;
import java.net.Socket;

import Client.ClientManager;

public class Game implements Runnable {
    private Socket socket;
    private static Baralho baralho;
    private static int card;
    @Override
    public void run() {
        System.out.println("O jogo está sendo rodado");       
        
    }
    public Game() 
    {
        try {
            baralho = new Baralho();
            // System.out.println("\n\nCARTA SELECIONADA: " + getCardBaralho() + "\n\n");
        }
        catch( Exception e) {}
    }
    public static void main (String[] args) 
    {
        try {
            baralho = new Baralho();
            String cartaRodada = getCardBaralho();
            System.out.println("\n\nCARTA SELECIONADA: " + cartaRodada + "\n");
            System.out.println("CARTA CONVERTIDA: " + getCoverted(cartaRodada) + "\n\n");

        }
        catch( Exception e) {}
    }

    public static String getCardBaralho(){
        String cartaSelecionada = "";
        try {
            cartaSelecionada = baralho.getCard().toString();
        } catch (Exception e) {}
        return cartaSelecionada;
    }

    public static int getCoverted(String cartaSelecionada) {
        try{
            
            if(cartaSelecionada.contains("A"))
            card = 1;
            else if(
            cartaSelecionada.contains("K")
            ||cartaSelecionada.contains("Q")
            ||cartaSelecionada.contains("J"))
            card = 10;
            
            else{
                cartaSelecionada.charAt(1);
                cartaSelecionada = cartaSelecionada.replaceAll("O", "");
                cartaSelecionada = cartaSelecionada.replaceAll("E", "");
                cartaSelecionada = cartaSelecionada.replaceAll("C", "");
                cartaSelecionada = cartaSelecionada.replaceAll("P", "");
                card = Integer.valueOf(cartaSelecionada);
            }
        }
    catch (Exception e) {}
    return card;
    }
    public static boolean isOutGamed (int cardSum) {
        if (cardSum > 21) return true;
        
        return false;
    }
    public static boolean isMatchOver() {
        return ClientManager.partidaEncerrada;
    }
}
