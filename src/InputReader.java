import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

// @author Omar Almassri omal7554

public class InputReader {

    private static ArrayList<InputStream> inputList = new ArrayList<>();
    private Scanner input;

    public InputReader(InputStream in) {

        if (inputList.contains(in))
            throw new IllegalStateException("Error: An instance of the scanner is already open.");

        this.input = new Scanner(in);
        inputList.add(in);
    }

    public InputReader() {

        this(System.in);
    }

    public int intReader(String promptMessage) {

        System.out.print(promptMessage + "?>");
        int inputtedInteger = input.nextInt();
        input.nextLine();

        return inputtedInteger;
    }

    public double decimalReader(String promptMessage) {

        System.out.print(promptMessage + "?>");
        double inputtedDouble = input.nextDouble();
        input.nextLine();

        return inputtedDouble;
    }

    public String stringReader(String promptMessage) {

        System.out.print(promptMessage + "?>");
        String inputtedString = input.nextLine();

        return inputtedString;
    }

    public void close() {
        input.close();
    }

}
