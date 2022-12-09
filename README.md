# Documentation

## Baralho 

*public Carta getCard() throws Exception*
{
    > checks if the Deck has still got cards
    >
    > shuffles out and grabs a random card in the Deck
    >> Then sets this card as the last position and erases it out
    >
    > returns the piked Carta
}

## Game
> Will be responsible for initializing the Deck and cards once all the players type "/start"

## Player
> Created once the game is started with "/start"
>> Handles the players that log in and their respective cards

## ClientHandler

*public void receiveEspecialMessages(String messageToSend)* 
{
    > verifys which message has been sent and executes their respectives functions.
}

## MessageHandler
### Mensagens Especiais Extras
else if (messageToSend.contains("/bother")) 
    {
        concealMessage("Você irá conversar com um usuário de sua escolha.");
    }

else if (messageToSend.contains("/endGame"))
    removeClientHandler();

# Situação atual 
> No momento, preciso fazer o início, decorrer e fim do jogo respectivamente, tornando-o algo palpável e sólido que poderá
> ser executado diversas vezes conforme a requisição da partida, sem que haja problemas
>
>> Inicializar
>
>>> O inicio deve ser como em ClientHandler: Criado um objeto da classe Game e executado através da Thread .
>>> Sistema para receber a mesnagem apenas uma vez de cada jogador
>
>> Decorrer
>
>>> 
>
>> Encerrar

# PROBLEMAS
> Preciso que START seja uma só variável igual para todos os jogadores da partida.
>
>> Será adicionado 1 (e mostrado a todos) quando um jogador enviar "/start".
>> Uma vez que a partida terminar START passa a ser zero novamente, esperando ser chamado outra vez para iniciar a contagem.
>
> O método usado para essa contagem deve ser eficiente e não repetitivo pra não possibilitar erros
>>NÃO SEI EM QUE CLASSE COLOCAR ISSO!!!! 

### Main de Game
public static void main (String[] args) 
    {
        try {
            baralho = new Baralho();
            System.out.println("\n\nCARTA SELECIONADA: " + getCardBaralho() + "\n\n");
        }
        catch( Exception e) {}
    }

### Mostrar todo o Baralho

for (int n=0; n<naipe.length; n++)
            for (int v=0; v<valor.length; v++)
            {
                try
                {
                    this.carta[c] = new Carta (valor[v],naipe[n]);
                    System.out.println(carta[c]);
                    c++;
                }
                catch (Exception erro)
                {}
           }

## nextPlayer()
    
public void getNextPlayer() {
        for (int i = ClientManager.whosTurn + 1; i < ClientManager.playersInMatch.size(); i++) {
            if (i == ClientManager.playersInMatch.size())
                i = 0;
            if (i == ClientManager.playersInMatch.size() - 1 && ClientManager.playersInMatch.get(i).equals("@out@"))
                i = 0;
            if (ClientManager.playersInMatch.get(i).equals("@out@")) 
                ClientManager.whosTurn++;
            else {
                ClientManager.whosTurn = i; break;
            }

        }
        if (ClientManager.whosTurn == ClientManager.playersInMatch.size()-1)
            ClientManager.whosTurn = 0; // Impede que 'whosTurn' alcance 'Index Out Of Bounds'

        else if (ClientManager.playersInMatch.get(ClientManager.whosTurn + 1).equals("@out@")) {
            ClientManager.whosTurn ++;
        }
        else if(ClientManager.playersInMatch.get(ClientManager.whosTurn).equals("@out@"))
            ClientManager.whosTurn++;
        
    }
## LeadBoard
     public void leadBoard(){
         String maiorPontuador = "", menorPontuador = "", random = "";
            int maior = 0, menor = 0;
        for(int i = 0; i < ClientManager.clientList.size(); i++)
        {
            if(ClientManager.playersScoreList.get(i) > 21)
            {
                broadcastMessage(Colors.WHITE_BOLD + "- " + ClientManager.clientList.get(i) + "excedeu a pontuação");
                concealMessage(Colors.WHITE_BOLD + "- " + ClientManager.clientList.get(i) + "excedeu a pontuação");
            }
            if(ClientManager.playersScoreList. == 21){
                
            }
        }   
    }