package Server;

import Controllers.customerController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;


public class Main {
    public static Connection db = null; //this will be representing a global variable

    public static void main(String[] args){
        openDatabase("Pizza Delivery System Database.db"); //opens the database

//______________________________________________________________________________________________________________________
        Scanner input = new Scanner(System.in);

        ResourceConfig config = new ResourceConfig();
        config.packages("Controllers");
        config.register(MultiPartFeature.class);
        ServletHolder servlet = new ServletHolder(new ServletContainer(config));

        Server server = new Server(8081);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(servlet, "/*");

        try {
            server.start();
            System.out.println("Server successfully started.");
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
//______________________________________________________________________________________________________________________

        int menuloop = 0;
        while(menuloop < 1 || menuloop > 8) {
            System.out.println("Please enter the number to choose the option: ");
            System.out.println("1 - SIGN UP");
            System.out.println("2 - LOG IN");
            System.out.println("3 - LATEST OFFERS");
            System.out.println("4 - FAVOURITES");
            System.out.println("5 - PIZZA");
            System.out.println("6 - DRINKS");
            System.out.println("7 - SIDES");
            System.out.println("8 - BASKET");
            menuloop = input.nextInt();
        }

        if(menuloop == 1){
            boolean done = false;

            System.out.println("Please enter your first name: ");
            String customerFirst = input.nextLine();
            System.out.println("Please enter your last name: ");
            String customerLast = input.nextLine();
            System.out.println("Please enter your chosen username: ");
            String customerUser = input.nextLine();
            System.out.println("Please enter your password: ");
            String customerPass = input.nextLine();
            System.out.println("Please enter your street name: ");
            String customerStreet = input.nextLine();
            System.out.println("Please enter your town name: ");
            String customerTown = input.nextLine();
            System.out.println("Please enter your postcode: ");
            String customerPostcode = input.nextLine();
            System.out.println("Please enter your bank number: ");
            String customerBank = input.nextLine();
            System.out.println("Please enter your allergies: ");
            String customerAllergies = input.nextLine();
            customerController.insertCustomer(customerFirst, customerLast, customerUser, customerPass, customerStreet, customerTown, customerPostcode, customerBank, customerAllergies);
        }




        closeDatabase(); //closes the database
    }


    private static void openDatabase(String dbFile)
    {
        try{
            Class.forName("org.sqlite.JDBC"); //loads the database driver
            SQLiteConfig config = new SQLiteConfig(); //database settings
            config.enforceForeignKeys(true);
            db = DriverManager.getConnection("jdbc:sqlite:resources/" + dbFile, config.toProperties()); //opens the database file
            System.out.println("Database connection successfully established");

        }

        catch(Exception exception){ //this will catch any errors
            System.out.println("Database connection error: " + exception.getMessage());
        }
    }

    private static void closeDatabase(){
        try{
            db.close(); //closes the database file
            System.out.println("Disconnected from database.");

        }
        catch(Exception exception){ //this will catch any errors
            System.out.println("Database disconnection error: "+ exception.getMessage());

        }

    }

    public static String firstName(boolean done){
        while(done == false) {
            Scanner input = new Scanner(System.in);
            System.out.println("Please enter your first name: ");
            String customerFirst = input.nextLine();
            if( customerFirst.length() < 46 ){
                done = true;
                return customerFirst;
            }
        }
        return null;
    }



}
