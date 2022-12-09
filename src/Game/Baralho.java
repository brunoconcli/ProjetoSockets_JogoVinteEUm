package Game;

public class Baralho implements Cloneable
{
    public Carta[] carta;
    private int ultimo;

    public Baralho() throws Exception
    {
        this.carta = new Carta [52];

        String[] naipe = {"C","O","E","P"};
        String[] valor = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};

        int c=0;

        for (int n=0; n<naipe.length; n++)
            for (int v=0; v<valor.length; v++)
            {
                try
                {
                    this.carta[c] = new Carta (valor[v],naipe[n]);
                    // System.out.println(carta[c]);
                    c++;
                }
                catch (Exception erro)
                {}
           }
       this.ultimo=51;
    }

    public Carta getCard () throws Exception
    {
        if (this.ultimo==-1) throw new Exception ("Baralho sem cartas");

        int posicao = (int) (Math.random()*(this.ultimo+1));
        Carta ret = this.carta [posicao];

        this.carta[posicao] = this.carta[this.ultimo];
        this.carta[this.ultimo] = null;
        this.ultimo--;

        return ret;
    }
    private boolean tem (Carta carta)
    {
        for (int c=0; c<=this.ultimo; c++)
            if (carta.equals(this.carta[c])) return true;

        return false;
    }

    @Override
    public String toString ()
    {
        String ret = "";

        for (int c=0; c<this.ultimo; c++)
            ret += this.carta[c]+",";

        if (this.ultimo>-1)
            ret += this.carta[this.ultimo];

        return ret;
    }


    @Override
    public boolean equals (Object obj)
    {
        if (obj==this) return true;
        if (obj==null) return false;

        if (this.getClass()!=obj.getClass()) return false;

        Baralho b = (Baralho)obj;

        if (this.ultimo!=b.ultimo) return false;

        for (int c=0; c<=this.ultimo; c++)
            if (!this.tem(b.carta[c])) return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;

        ret = 13*ret + Integer.valueOf(this.ultimo).hashCode();

        for (int c=0; c<=this.ultimo; c++)
            ret = 13*ret + this.carta[c].hashCode();

        if (ret<0) ret=-ret;

        return ret;
    }

    public Baralho (Baralho modelo) throws Exception
    {
        if (modelo==null) throw new Exception ("Modelo ausente");

        this.ultimo = modelo.ultimo;

        this.carta = new Carta [54];

        for (int c=0; c<=this.ultimo; c++)
            this.carta[c] = modelo.carta[c];
    }

    public Object clone ()
    {
        Baralho ret = null;

        try
        {
            ret = new Baralho (this);
        }
        catch (Exception erro)
        {} 
        return ret;
    }
}