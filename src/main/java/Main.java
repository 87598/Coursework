import org.sqlite.SQLiteConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Main {
    public static Connection db = null; //this will be representing a global variable

    public static void main(String[] args){
        openDatabase("Pizza Delivery System Database.db"); //opens the database

        //this block of code allows you to read the database so that the data can be used
        try{
            PreparedStatement ps = db.prepareStatement("SELECT userID, userName, userPassword FROM User");

            ResultSet results = ps.executeQuery();
            while(results.next()){
                int userID = results.getInt(1);
                String userName = results.getString(2);
                String userPassword = results.getString(3);
                System.out.println(userID + " "+ userName+" "+ userPassword);
            }
        }
        catch(Exception exception){ //this will catch the errors
            System.out.println("Database error: "+ exception.getMessage());
        }

        //this block of code allows you to write to the database
        try{
            PreparedStatement ps = db.prepareStatement("INSERT INTO User (userID, userName, userPassword) VALUES (?, ?, ?)");

            ps.setInt(1,5);
            ps.setString(2, "Bob");
            ps.setString(3, "DeathisHere");

            ps.executeUpdate();
        }
        catch (Exception exception){ //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
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

}
