import java.io.FileWriter;
import java.time.Instant;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.*;


public class Main {
    private static String[] startupOptions = {"Login", "Create Account"};
    private static String[] loggedInOptions = {"View Account Details","Deposit","Withdraw","Exit"};
    private static String loggedInAccountName;
    private static ArrayList<String> files;
    private static Scanner scanner = new Scanner(System.in);
    private static String currentPath = System.getProperty("user.dir");
    private static String documentsPath;
    private static String password;
    private static String realPswrd;


    public static void main(String[] args)
    {
        setupDir();
        boolean gettingIndex = true;
        while(gettingIndex)
        {
            int index = getOption(startupOptions);
            if(index == 1)
            {
                createAccount();
                gettingIndex = false;
            }
            else if(index == 0)
            {
                login();
                gettingIndex = false;
            }
            else
            {
                System.out.println("Enter a valid number!");
            }
        }

        gettingIndex = true;
        while(gettingIndex)
        {
            int index = getOption(loggedInOptions);
            if(index == 0)
            {
                viewAccountDetails();
                gettingIndex = false;
            }
            else if(index == 1)
            {
                deposit();
                gettingIndex = false;
            }
            else if(index == 2)
            {
                withdraw();
                gettingIndex = false;
            }
            else if(index == 3)
            {
                gettingIndex = false;
                System.exit(0);
            }
        }
    }


    public static void setupDir()
    {
        documentsPath = System.getProperty("user.home") + "\\Documents";
        File documentsFolder = new File(documentsPath);
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
    public static String[] readFile(String path)
    {
        String[] text = null;
        try
        {
            long lines = 0;
            File obj = new File(path);
            Scanner scan = new Scanner(obj);
            Path path1 = Path.of(path);
            lines = Files.lines(path1).count();
            text = new String[(int) lines];
            int i = 0;
            while(scan.hasNextLine())
            {
                text[i] = scan.nextLine();
                i++;
            }

            scan.close();
        }
        catch(Exception exception)
        {
            System.out.println("Error Occured When Opening File");
            exception.printStackTrace();
        }
        return text;
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
    public static int getOption(String[] options)
    {
        System.out.println("What Would you like to do?");
        for(int i = 0; i < options.length; i++)
        {
            System.out.println(i+1 + " " + options[i]);
        }
        System.out.println("Choice: ");

        boolean isChoosing = true;
        int index=0;

        while(isChoosing) {
            try {
                index = Integer.parseInt(scanner.nextLine());
                index--;
                isChoosing = false;
            } catch (Exception exception) {
                System.out.println("Please enter a number lower than or equal to: " + options.length);
                System.out.println("Choice: ");
            }
        }
        return index;
    }
    public static void login()
    {
        System.out.println("Please enter your name: ");
        String username = scanner.nextLine();
        boolean isLoggingIn = true;

        while(isLoggingIn)
        {
            File obj = new File(documentsPath+"\\BankingSystem\\Accounts\\" + username);

            if(!obj.exists())
            {
                obj = null;
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
                String[] text = readFile(documentsPath+"\\BankingSystem\\Accounts\\" + username + "\\Password.txt");
                realPswrd = text[0];

                int passwordAttempts = 4;
                for(int i = 0; i < passwordAttempts; i++) {
                    System.out.println("Please enter your password: ");
                    String password = scanner.nextLine();
                    if (password.equals(realPswrd)) {
                        isLoggingIn = false;
                        System.out.println("Logging in...");
                        loggedInAccountName = username;
                        return;
                    } else {
                        System.out.println("Wrong Password!");
                    }
                }
                System.out.println("Suspicious Activity Detected, Closing...");
                System.exit(0);
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
        Instant currentDate = Instant.now();
        String currentDateStr = currentDate.toString();
        writeToFile(documentsPath+"\\BankingSystem\\Accounts\\"+username+"\\data.txt","Account created on: " + currentDateStr.substring(0,10) + " UTC+0" + "\nType of account: {not implemented}\nCurrent balance: " + 0);

        login();
    }
    public static void viewAccountDetails()
    {
        String[] text = readFile(documentsPath+"\\BankingSystem\\Accounts\\"+loggedInAccountName+"\\data.txt");
        for(int i = 0; i < text.length; i++)
        {
            System.out.println(text[i]);
        }
    }
    public static void deposit()
    {

    }
    public static void withdraw()
    {

    }
}
