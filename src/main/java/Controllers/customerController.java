package Controllers;
import Server.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
    The API request handler for /Customer/list/
        FormDataParams: none
        Cookies: none
 */


public class customerController {

    @GET
    @Path("list/{customerID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listCustomer(@PathParam("customerID") int customerID , @FormDataParam("customerUser") String customerUser, @FormDataParam("customerPass") String customerPass) {
        //this block of code allows you to read the database so that the data can be used
        System.out.println("Customer/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT customerID, customerUser, customerPass FROM Customer");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("customerID", results.getInt(1));
                item.put("customerUser", results.getString(2));
                item.put("customerPass", results.getString(3));
                list.add(item);
                //System.out.println(customerID + " " + customerUser + " " + customerPass);
            }
            return list.toString();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list customers, please see server console for more info.\"}";
        }
    }

    /*
    The API request handler for /Customer/add/
        FormDataParams: none
        Cookies: none
 */

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
