package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Order/")


public class orderController {

    /*
    The API request handler for /Pizza/list
        FormDataParams: none
        Cookies: none
 */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listOrder() {
        //this block of code allows you to read the database so that the data can be used
        System.out.println("Order/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT orderID, customerID, restaurantID FROM Order");
            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("orderID", results.getInt(1));
                item.put("customerID", results.getInt(2));
                item.put("restaurantID", results.getInt(3));
                list.add(item);
                //System.out.println(orderID + " " + customerID + " " + restaurantID);
            }
            return list.toString();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list orders, please see server console for more info.\"}";
        }
    }

    /*
    The API request handler for /Pizza/list
        FormDataParams: none
        Cookies: none
 */

    @POST
    @Path("create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String insertOrder(@FormDataParam("customerID")Integer customerID,
                              @FormDataParam("restaurantID")Integer restaurantID) {
        //this block of code allows you to insert a basket into the database
        try {

            if(customerID == null ||
                    restaurantID == null){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Order/create customerID = " + customerID);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Basket (customerID, restaurantID) VALUES ( ?, ?)");

            ps.setInt(1, customerID);
            ps.setInt(2, restaurantID);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new order, please see server console for more info.\"}";
        }
    }

    /*
    The API request handler for /Pizza/list
        FormDataParams: none
        Cookies: none
 */
    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    public String updateOrder(@FormDataParam("orderID")Integer orderID,
                              @FormDataParam("customerID")Integer customerID,
                              @FormDataParam("restaurantID")Integer restaurantID){
        //this allows you to update a basket for new contents to the database
        try{
            if(orderID == null || customerID == null || restaurantID == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Order/update orderID = "+ orderID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Order SET customerID = ?, restaurantID = ? WHERE orderID = ?");
            ps.setInt(1, orderID);
            ps.setInt(2, customerID);
            ps.setInt(3, restaurantID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to update order, please see server console for more info.\"}";
        }
    }

    /*
   The API request handler for /Pizza/list
       FormDataParams: none
       Cookies: none
*/
    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    public String deleteOrder(@FormDataParam("orderID")Integer orderID){
        //this allows you to delete a basket from the database
        try{
            if (orderID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Order/delete orderID = " +  orderID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Order WHERE orderID = ?");
            ps.setInt(1, orderID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to delete order, please see server console for more info.\"}";
        }
    }
}