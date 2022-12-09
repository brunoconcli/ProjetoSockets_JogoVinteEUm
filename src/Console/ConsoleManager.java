package Console;

public class ConsoleManager {
    public static void clear() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void resetColor() { System.out.print(Colors.RESET); }

    public static void print(Colors color, String text) { System.out.print(color+""+text+""+Colors.RESET); }
    public static void print(Colors color, Colors bg, String text) { System.out.print(color+""+bg+""+text+""+Colors.RESET); }
    public static void print(String text) { System.out.print(text); }
    public static void print() { print(" "); }

    public static void println(Colors color, String text) { System.out.println(color+""+text+""+Colors.RESET); }
    public static void println(Colors color, Colors bg, String text) { System.out.println(color+""+bg+""+text+""+Colors.RESET); }
    public static void println(String text) { System.out.println(text); }
    public static void println() { print(" "); }
}
