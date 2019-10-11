package Controllers;

import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



@Path("Customer/")
public class customerController {

    /*
    The API request handler for /Customer/list
        FormDataParams: none
        Cookies: none
 */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listCustomer() {
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
    The API request handler for /Customer/signup
        FormDataParams: customerFirst, customerLast, customerUser, customerPass, customerStreet, customerTown, customerPostcode, customerBank, customerAllergies
        Cookies: cookie for the customer
 */

    @POST
    @Path("signup")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertCustomer(
            @FormDataParam("customerFirst") String customerFirst,
            @FormDataParam("customerLast")String customerLast,
            @FormDataParam("customerUser")String customerUser,
            @FormDataParam("customerPass")String customerPass,
            @FormDataParam("customerStreet")String customerStreet,
            @FormDataParam("customerTown")String customerTown,
            @FormDataParam("customerPostcode")String customerPostcode,
            @FormDataParam("customerBank")String customerBank,
            @FormDataParam("customerAllergies")String customerAllergies
      ){
        //this block of code allows you to insert a user into the database
        try {
            if( customerFirst == null || customerLast == null ||customerUser == null ||customerPass == null ||customerStreet == null ||customerTown == null ||customerPostcode == null ||customerBank == null){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Customer/signup customerUser = " + customerUser);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Customer (customerFirst, customerLast, customerUser, customerPass, customerStreet, customerTown, customerPostcode, customerBank, customerAllergies) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )");

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
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new customer, please see server console for more info.\"}";
        }
    }

    /*
    The API request handler for /Customer/update
        FormDataParams: customerID, customerUser, customerPass
        Cookies: need to be the user's (user that's updating the account) token
 */

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String updateCustomer(
            @FormDataParam("customerUser") String customerUser,
            @FormDataParam("customerPass")String customerPass,
            @FormDataParam("customerID") Integer customerID
    ){
    //this allows you to update a user from the database
        try{
            if(customerUser == null || customerPass == null || customerID == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Customer/update customerID = "+ customerID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Customer SET customerUser = ?, customerPass = ? WHERE customerID = ?");
            ps.setInt(1, customerID);
            ps.setString(2, customerUser);
            ps.setString(3, customerPass);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to update customer, please see server console for more info.\"}";
        }
    }

     /*
    The API request handler for /Customer/delete
        FormDataParams: none
        Cookies: need to be the user's (user that's updating the account) token or an admin
 */

     @POST
     @Path("delete")
     @Consumes(MediaType.MULTIPART_FORM_DATA)
     @Produces(MediaType.APPLICATION_JSON)
    public String deleteCustomer(@FormDataParam("customerID")Integer customerID){
        //this allows you to delete a user from the database
        try{
            if (customerID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Customer/delete customerID = " +  customerID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Customer WHERE customerID = ?");
            ps.setInt(1, customerID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println("Database error: " + e.getMessage());
            return "{\"error\": \"Unable to delete customer, please see server console for more info.\"}";
        }
    }

}
