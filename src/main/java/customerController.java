import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class customerController {

    public static void listCustomer(int customerID, String customerUser, String customerPass) {
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT customerID, customerUser, customerPass FROM Customer");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                customerID = results.getInt(1);
                customerUser = results.getString(2);
                customerPass = results.getString(3);
                System.out.println(customerID + " " + customerUser + " " + customerPass);
            }
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertCustomer(String customerFirst, String customerLast, String customerUser, String customerPass, String customerStreet, String customerTown, String customerPostcode, String customerBank, String customerAllergies) {
        //this block of code allows you to insert a user into the database
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Customer (customerFirst, customerLast, customerUser, customerPass, customerStreet, customerTown, customerPostcode, customerBank, customerAllergies) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, customerFirst);
            ps.setString(2, customerLast);
            ps.setString(3, customerUser);
            ps.setString(4, customerPass);
            ps.setString(5, customerStreet);
            ps.setString(6, customerTown);
            ps.setString(7, customerPostcode);
            ps.setString(8, customerBank);
            ps.setString(9, customerAllergies);

            ps.executeUpdate();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
        }
    }

    public static void updateCustomer(String customerUser, String customerPass, int customerID){
    //this allows you to update a user from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Customer SET customerUser = ?, customerPass = ? WHERE customerID = ?");
            ps.setInt(1, customerID);
            ps.setString(2, customerUser);
            ps.setString(3, customerPass);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteCustomer(int customerID){
        //this allows you to delete a user from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Customer WHERE customerID = ?");
            ps.setInt(1, customerID);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

}
