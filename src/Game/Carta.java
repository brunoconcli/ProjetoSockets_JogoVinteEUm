package Game;
public class Carta {

    //VALORES
    public static final String AS = "A";
    public static final String DOIS = "2";
    public static final String TRES = "3";
    public static final String QUATRO = "4";
    public static final String CINCO = "5";
    public static final String SEIS = "6";
    public static final String SETE = "7";
    public static final String OITO = "8";
    public static final String NOVE = "9";
    public static final String DEZ = "10";
    public static final String VALETE = "J";
    public static final String DAMA = "Q";
    public static final String REI = "K";

    //NAIPES
    public static final String COPAS = "C";
    public static final String OUROS = "O";
    public static final String ESPADAS = "E";
    public static final String PAUS = "P";

    private String valor, naipe;

    private static boolean valorValido(String valor) throws Exception
    {
        try
        {
            int val = Integer.parseInt(valor);

            if(val<2 || val>10) return false;
        }
        catch (Exception erro)
        {
            valor = valor.toUpperCase();
            if(!valor.equals(Carta.AS) && //as
                !valor.equals(Carta.VALETE) && // valete
                !valor.equals(Carta.DAMA) && // dama
                !valor.equals(Carta.REI)) // rei
                return false;
        }
        return true;
    }

    private static boolean naipeValido (String naipe)
    {
        if(!naipe.equals(Carta.COPAS) && // copas
            !naipe.equals(Carta.OUROS) && // ouros
            !naipe.equals(Carta.ESPADAS) && // espadas
            !naipe.equals(Carta.PAUS)) // paus
            return false;
        return true;    
    }

    public Carta(String valor, String naipe) 
    throws Exception
    {
        if(valor == null)
            throw new Exception ("Valor ausente");

        valor = valor.toUpperCase();

        if(!Carta.valorValido(valor))
            throw new Exception ("Valor inválido");

        else
        {
            if(naipe == null)
                throw new Exception ("Naipe ausente");

            if(!Carta.naipeValido(naipe))
                throw new Exception ("Naipe inválido");
        }

        this.valor = valor; this.naipe = naipe;
    }

    public Carta (String valor) throws Exception
    {
        if(valor == null)
            throw new Exception ("Valor ausente");

            valor = valor.toUpperCase();

        this.valor = valor;
        this.naipe = null;
    }

    public String getValor()
    {
        return this.valor;
    }
    
    public String getNaipe() throws Exception
    {
        return this.naipe;
    }

    @Override
    public String toString ()
    {
        String ret = "";
            ret += valor+naipe;

        return ret;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj==this) return true;
        if (obj==null) return false;

        if (this.getClass()!=obj.getClass()) return false;

        if(this.valor != ((Carta)obj).valor) return false;
        if(this.naipe != ((Carta)obj).naipe) return false;

        return true;
    }

    @Override
    public int hashCode ()
    {
        int ret = 1;
        ret = 17*ret + Integer.valueOf(this.valor).hashCode();

        if (ret<0) ret=-ret;
        return ret;
    }
}
