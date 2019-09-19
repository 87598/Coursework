import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class drinkController {
    public static void listDrink(int drinkID, String drinkName, int drinkPrice) {
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT drinkID, drinkName, drinkPrice FROM Drinks");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                drinkID = results.getInt(1);
                drinkName = results.getString(2);
                drinkPrice = results.getInt(3);

                System.out.println(drinkID + " " + drinkName + " " + drinkPrice);
            }
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertDrink(String drinkName, int drinkPrice) {
        //this block of code allows you to insert a drink into the database
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Drinks (drinkName, drinkPrice) VALUES ( ?, ?)");

            ps.setString(1, drinkName);
            ps.setInt(2, drinkPrice);

            ps.executeUpdate();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
        }
    }

    public static void updateDrink(Integer drinkID, String drinkName, Integer drinkPrice){
        //this allows you to update a drink in the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Drinks SET drinkName = ?, drinkPrice = ? WHERE drinkID = ?");
            ps.setInt(1, drinkID);
            ps.setString(2, drinkName);
            ps.setInt(3, drinkPrice);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteDrink(int drinkID){
        //this allows you to delete a drink from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Drinks WHERE drinkID = ?");
            ps.setInt(1, drinkID);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
