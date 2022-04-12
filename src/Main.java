import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private static String[] startupMethods = {"Login", "Create Account"};
    private static ArrayList<String> files;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        int index = getLoginMethod();
        openAccount(index);
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

    public static void openAccount(int index)
    {
        if(startupMethods[index].equals("Create Account"))
        {
            createAccount();
        }
        else
        {
            login();
        }
    }

    public static void login()
    {
        System.out.println("Please enter your name: ");
        String username = scanner.nextLine();
        boolean isLoggingIn = true;

        while(isLoggingIn)
        {
            try
            {
                //openfile
            }
            catch(Exception exception)
            {
                System.out.println("This username doesn't exist! \n Would you like to create an account? Y/N");
                String choice = scanner.nextLine();
                choice = choice.toUpperCase();
                if(choice.equals("Y"))
                {
                    isLoggingIn = false;
                    createAccount();
                }
            }
        }



    }
    public static void createAccount()
    {
        login();
    }
}
