import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserController {

    public static void listUsers(int userID, String userName, String userPassword) {
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT userID, userName, userPassword FROM Users");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                userID = results.getInt(1);
                userName = results.getString(2);
                userPassword = results.getString(3);
                System.out.println(userID + " " + userName + " " + userPassword);
            }
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertUser(int userID, String userName, String userPassword) {
        //this block of code allows you to insert a user into the database
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Users (userID, userName, userPassword) VALUES (?, ?, ?)");

            ps.setInt(1, userID);
            ps.setString(2, userName);
            ps.setString(3, userPassword);

            ps.executeUpdate();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
        }
    }

    public static void updateUser(String userName, String userPassword, int userID){
    //this allows you to update a user from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Users SET userName = ?, userPassword = ? WHERE userID = ?");
            ps.setInt(1, userID);
            ps.setString(2, userName);
            ps.setString(3, userPassword);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteUser(int userID){
        //this allows you to delete a user from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Users WHERE userID = ?");
            ps.setInt(1, userID);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
