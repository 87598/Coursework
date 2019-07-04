import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Main {
    public static Connection db = null; //this will be representing a global variable

    public static void main(String[] args){
        openDatabase("Pizza Delivery System Database.db"); //opens the database


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

}
