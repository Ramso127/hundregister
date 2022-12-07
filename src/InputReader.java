import java.io.InputStream;
import java.util.Scanner;

public class InputReader {

    public InputReader(InputStream in) {
        
        if(in != null)
            throw new IllegalStateException("An instance of the scanner is already open.");

            Scanner input = new Scanner(System.in);
        }

    public InputReader() {

        InputReader(System.in);
    }

    public int intReader(String promptMessage) {
        Scanner input = new Scanner(System.in);
        System.out.println(promptMessage + "?>");
        int inputtedInteger = input.nextInt();

        input.close();
        return inputtedInteger;
    }

    public double decimalReader(String promptMessage) {
        Scanner input = new Scanner(System.in);
        System.out.println(promptMessage + "?>");
        double inputtedDouble = input.nextDouble();

        input.close();
        return inputtedDouble;
    }

    public String stringReader(String promptMessage) {
        Scanner input = new Scanner(System.in);
        System.out.println(promptMessage + "?>");
        String inputtedString = input.nextLine();

        input.close();
        return inputtedString;
    }

}
