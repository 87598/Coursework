import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class basketController {
    public static void listBasket(int basketID, int pizzaTotal,  int sideTotal, int drinkTotal) {
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT basketID, pizzaTotal, sideTotal, drinkTotal FROM Basket");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                basketID = results.getInt(1);
                pizzaTotal = results.getInt(2);
                sideTotal = results.getInt(3);
                drinkTotal = results.getInt(4);
                System.out.println(basketID + " " + pizzaTotal + " " + sideTotal + " " + drinkTotal);
            }
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertBasket(int pizzaTotal, int sideTotal, int drinkTotal) {
        //this block of code allows you to insert a basket into the database
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Basket (pizzaTotal, sideTotal, drinkTotal) VALUES ( ?, ?, ?)");

            ps.setInt(1, pizzaTotal);
            ps.setInt(2, sideTotal);
            ps.setInt(3, drinkTotal);

            ps.executeUpdate();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
        }
    }

    public static void updateBasket(String pizzaTotal, String restaurantLocation, String restaurantTime, int restaurantID){
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

    public static void deleteStaff(int restaurantID){
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