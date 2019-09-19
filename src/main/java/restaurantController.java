import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class restaurantController {
    public static void listRestaurant(int restaurantID, String restaurantName,  String restaurantLocation, String restaurantTime) {
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT restaurantID, restaurantName, restaurantLocation, restaurantTime FROM Restaurant");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                restaurantID = results.getInt(1);
                restaurantName = results.getString(2);
                restaurantLocation = results.getString(3);
                restaurantTime = results.getString(4);
                System.out.println(restaurantID + " " + restaurantName + " " + restaurantLocation + " " + restaurantTime);
            }
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertRestaurant(String restaurantName, String restaurantLocation, String restaurantTime) {
        //this block of code allows you to insert a restaurant into the database
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Restaurant (restaurantName, restaurantLocation, restaurantTime) VALUES ( ?, ?, ?)");

            ps.setString(1, restaurantName);
            ps.setString(2, restaurantLocation);
            ps.setString(3, restaurantTime);

            ps.executeUpdate();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
        }
    }

    public static void updateRestaurant(String restaurantName, String restaurantLocation, String restaurantTime, int restaurantID){
        //this allows you to update a restaurant from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Staff SET restaurantName = ?, restaurantLocation = ?, restaurantTime = ? WHERE restaurantID = ?");
            ps.setInt(1, restaurantID);
            ps.setString(2, restaurantName);
            ps.setString(3, restaurantLocation);
            ps.setString(4, restaurantTime);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteRestaurant(int restaurantID){
        //this allows you to delete a restaurant from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Restaurant WHERE restaurantID = ?");
            ps.setInt(1, restaurantID);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
