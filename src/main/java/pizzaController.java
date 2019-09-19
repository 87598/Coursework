import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class pizzaController {
    public static void listPizza(int pizzaID, String pizzaName, String pizzaTopping, String pizzaBase, String pizzaCrust) {
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT pizzaID, pizzaName, pizzaTopping, pizzaBase, pizzaCrust FROM Pizzas");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                pizzaID = results.getInt(1);
                pizzaName = results.getString(2);
                pizzaTopping = results.getString(3);
                pizzaBase = results.getString(4);
                pizzaCrust = results.getString(5);
                System.out.println(pizzaID + " " + pizzaName + " " + pizzaTopping + " " + pizzaBase + " " + pizzaCrust);
            }
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertPizza(String pizzaName, Character pizzaSize, String pizzaTopping, String pizzaBase, String pizzaCrust, Integer pizzaPrice) {
        //this block of code allows you to insert a pizza into the database
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Pizzas (pizzaName, pizzaSize, pizzaTopping, pizzaBase, pizzaCrust, pizzaPrice) VALUES ( ?, ?, ?, ?, ?, ?)");

            ps.setString(1, pizzaName);
            ps.setObject(2, pizzaSize, java.sql.Types.CHAR);
            ps.setString(3, pizzaTopping);
            ps.setString(4, pizzaBase);
            ps.setString(5, pizzaCrust);
            ps.setInt(6, pizzaPrice);


            ps.executeUpdate();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
        }
    }

    public static void updatePizza(Integer pizzaID, String pizzaName, Character pizzaSize, String pizzaTopping, String pizzaBase, String pizzaCrust, Integer pizzaPrice){
        //this allows you to update a pizza in the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Pizzas SET pizzaName = ?, pizzaSize = ?, pizzaTopping = ?, pizzaBase = ?, pizzaCrust = ?, pizzaPrice = ? WHERE pizzaID = ?");
            ps.setInt(1, pizzaID);
            ps.setString(2, pizzaName);
            ps.setObject(3, pizzaSize, java.sql.Types.CHAR);
            ps.setString(4, pizzaTopping);
            ps.setString(5, pizzaBase);
            ps.setString(6, pizzaCrust);
            ps.setInt(7, pizzaPrice);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteCustomer(int customerID){
        //this allows you to delete a pizza from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Pizzas WHERE pizzaID = ?");
            ps.setInt(1, customerID);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
