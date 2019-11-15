package Controllers;
import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Restaurant/")

public class restaurantController {

    /*
    The API request handler for /Pizza/list
        FormDataParams: none
        Cookies: none
 */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listRestaurant() {
        System.out.println("Restaurant/list");
        JSONArray list = new JSONArray();
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT restaurantID, restaurantName, restaurantLocation, restaurantTime FROM Restaurant");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("restaurantID", results.getInt(1));
                item.put("restaurantName", results.getString(2));
                item.put("restaurantLocation", results.getString(3));
                item.put("restaurantTime", results.getString(3));
                list.add(item);
                //System.out.println(restaurantID + " " + restaurantName + " " + restaurantLocation + " " + restaurantTime);
            }
            return list.toString();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list restaurants, please see server console for more info.\"}";
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

    public String insertRestaurant(@FormDataParam("restaurantName")String restaurantName,
                                   @FormDataParam("restaurantLocation")String restaurantLocation,
                                   @FormDataParam("restaurantTime")String restaurantTime) {
        //this block of code allows you to insert a restaurant into the database
        try {
            if(restaurantName == null ||
                    restaurantLocation == null ||
                    restaurantTime == null){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Restaurant/create restaurantName = " + restaurantName);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Restaurant (restaurantName, restaurantLocation, restaurantTime) VALUES ( ?, ?, ?)");

            ps.setString(1, restaurantName);
            ps.setString(2, restaurantLocation);
            ps.setString(3, restaurantTime);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new restaurant, please see server console for more info.\"}";
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

    public String updateRestaurant(@FormDataParam("restaurantName")String restaurantName,
                                   @FormDataParam("restaurantLocation")String restaurantLocation,
                                   @FormDataParam("restaurantTime")String restaurantTime,
                                   @FormDataParam("restaurantID")Integer restaurantID) {
        //this allows you to update a restaurant from the database
        try {
            if (restaurantName == null || restaurantLocation == null || restaurantTime == null || restaurantID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Restaurant/update restaurantName = " + restaurantName);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Staff SET restaurantName = ?, restaurantLocation = ?, restaurantTime = ? WHERE restaurantID = ?");
            ps.setInt(1, restaurantID);
            ps.setString(2, restaurantName);
            ps.setString(3, restaurantLocation);
            ps.setString(4, restaurantTime);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to update restaurant, please see server console for more info.\"}";
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
    public String deleteRestaurant(@FormDataParam("restaurantID")Integer restaurantID){
        //this allows you to delete a restaurant from the database
        try{
            if (restaurantID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Restaurant/delete restaurantID = " +  restaurantID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Restaurant WHERE restaurantID = ?");
            ps.setInt(1, restaurantID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to delete restaurant, please see server console for more info.\"}";
        }
    }
}