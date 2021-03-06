package Controllers;
import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Path("Drink/")

public class drinkController {

     /*
    The API request handler for /Pizza/list
        FormDataParams: none
        Cookies: none
 */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public String listDrink() {
        System.out.println("Drink/list");
        JSONArray list = new JSONArray();
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT drinkID, drinkName, drinkPrice FROM Drinks");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("drinkID", results.getInt(1));
                item.put("drinkName", results.getString(2));
                item.put("drinkPrice", results.getInt(3));
                list.add(item);
                //System.out.println(drinkID + " " + drinkName + " " + drinkPrice);
            }
            return list.toString();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list drinks, please see server console for more info.\"}";
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

    public String insertDrink(@FormDataParam("drinkName")String drinkName,
                              @FormDataParam("drinkPrice")Integer drinkPrice) {
        //this block of code allows you to insert a drink into the database
        try {
            if(drinkName == null ||
                    drinkPrice == null){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Drink/create drinkName = " + drinkName);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Drinks (drinkName, drinkPrice) VALUES ( ?, ?)");

            ps.setString(1, drinkName);
            ps.setInt(2, drinkPrice);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new drink, please see server console for more info.\"}";
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

    public String updateDrink(@FormDataParam("drinkID")Integer drinkID,
                              @FormDataParam("drinkName")String drinkName,
                              @FormDataParam("drinkPrice")Integer drinkPrice){
        //this allows you to update a drink in the database
        try{
            if(drinkID == null || drinkName == null || drinkPrice == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Drink/update drinkID = "+ drinkID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Drinks SET drinkName = ?, drinkPrice = ? WHERE drinkID = ?");
            ps.setInt(1, drinkID);
            ps.setString(2, drinkName);
            ps.setInt(3, drinkPrice);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to update drink, please see server console for more info.\"}";
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
    public String deleteDrink(@FormDataParam("drinkID")Integer drinkID){
        //this allows you to delete a drink from the database
        try{
            if (drinkID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Drink/delete drinkID = " +  drinkID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Drinks WHERE drinkID = ?");
            ps.setInt(1, drinkID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to delete drink, please see server console for more info.\"}";
        }
    }
}