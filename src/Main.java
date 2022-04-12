import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Main {
    private static String[] startupMethods = {"Login", "Create Account"};
    private static ArrayList<String> files;
    private static Scanner scanner = new Scanner(System.in);
    private static String currentPath = System.getProperty("user.dir");
    private static String documentsPath;
    private static File documentsFolder;
    private static String password;

    public static void main(String[] args)
    {
        setupDir();
        int index = getLoginMethod();
        openAccount(index);
    }


    public static void setupDir()
    {
        documentsPath = System.getProperty("user.home") + "\\Documents";
        documentsFolder = new File(documentsPath);
        File[] filesInDocs = documentsFolder.listFiles();
        for(int i =0; i< filesInDocs.length; i++)
        {
            if(filesInDocs[i].getName().equals("BankingSystem"))
            {
                return;
            }
        }

        new File(documentsPath+"\\BankingSystem").mkdir();
        new File(documentsPath+"\\BankingSystem\\Accounts").mkdir();
    }
    public static void createFile(String filename, String fileExtension, String path)
    {
        try
        {
            File obj = new File(path+filename+fileExtension);
            obj.createNewFile();
        }
        catch (Exception exception)
        {
            System.out.println("An error occurred.");
            exception.printStackTrace();
        }
    }
    public static void writeToFile(String path, String text)
    {
        try
        {
            FileWriter filewriter = new FileWriter(path);
            filewriter.write(text);
            filewriter.close();
        }
        catch(Exception exception)
        {
            System.out.println("An error occurred.");
            exception.printStackTrace();
        }

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
            File obj = new File(currentPath+"\\" + username);

            if(!obj.exists())
            {
                System.out.println("This username doesn't exist! \nWould you like to create an account? Y/N");
                String choice = scanner.nextLine();
                choice = choice.toUpperCase();
                if(choice.equals("Y"))
                {
                    isLoggingIn = false;
                    createAccount();
                    return;
                }
                else
                {
                    login();
                }
            }
            else
            {
                System.out.println("Logging in...");
                isLoggingIn=false;
                return;
            }
        }



    }
    public static void createAccount()
    {
        System.out.println("What would you like your username to be?");
        String username = scanner.nextLine();

        boolean isPassword = true;

        while(isPassword)
        {
            System.out.println("What would you like your password to be?");
            password = scanner.nextLine();

            System.out.println("Please re-type your password.");
            String password2 = scanner.nextLine();

            if(password.equals(password2))
            {
                isPassword = false;
            }
            else
            {
                System.out.println("Password Mismatch!");
            }
        }
        new File(documentsPath+"\\BankingSystem\\Accounts\\"+username).mkdir();
        createFile("Password",".txt",documentsPath+"\\BankingSystem\\Accounts\\"+username+"\\");
        writeToFile(documentsPath+"\\BankingSystem\\Accounts\\"+username+"\\Password.txt",password);
        createFile("data",".txt",documentsPath+"\\BankingSystem\\Accounts\\"+username+"\\");
    }
}
