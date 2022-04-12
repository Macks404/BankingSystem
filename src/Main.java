import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static String[] startupMethods = {"Login", "Create Account"};
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        System.out.println(startupMethods[getLoginMethod()]);
    }

    public static int getLoginMethod()
    {
        System.out.println("What Would you like to do?");
        for(int i = 0; i < startupMethods.length; i++)
        {
            System.out.println(i+1 + " " + startupMethods[i]);
        }
        System.out.println("Choice: ");

        boolean isChoosing = true;
        int index=0;

        while(isChoosing)
        {
            try
            {
                index = Integer.parseInt(scanner.nextLine());
                index--;
                isChoosing = false;
            }
            catch (Exception exception)
            {
                System.out.println("Please enter a number lower than or equal to: "+ startupMethods.length);
                System.out.println("Choice: ");
            }
        }
        return(index);
    }
}
