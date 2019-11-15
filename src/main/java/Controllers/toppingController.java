package Controllers;
import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Topping/")

public class toppingController {

  /*
    The API request handler for /Pizza/list
        FormDataParams: none
        Cookies: none
 */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public static String listTopping() {
        System.out.println("Topping/list");
        JSONArray list = new JSONArray();
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT toppingID, toppingName, toppingPrice FROM Topping");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("toppingID", results.getInt(1));
                item.put("toppingName", results.getString(2));
                item.put("toppingPrice", results.getInt(3));
                list.add(item);

            }
            return list.toString();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list toppings, please see server console for more info.\"}";
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

    public String insertTopping(@FormDataParam("toppingName")String toppingName,
                                @FormDataParam("toppingPrice")Integer toppingPrice) {
        //this block of code allows you to insert a drink into the database
        try {
            if(toppingName == null ||
                    toppingPrice == null){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Topping/create toppingName = " + toppingName);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Topping (toppingName, toppingPrice) VALUES ( ?, ?)");

            ps.setString(1, toppingName);
            ps.setInt(2, toppingPrice);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new topping, please see server console for more info.\"}";
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

    public String updateTopping(@FormDataParam("toppingID")Integer toppingID,
                                @FormDataParam("toppingName")String toppingName,
                                @FormDataParam("toppingPrice")Integer toppingPrice){
        //this allows you to update a drink in the database
        try{
            if(toppingID == null || toppingName == null || toppingPrice == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Topping/update toppingID = "+ toppingID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Topping SET toppingName = ?, toppingPrice = ? WHERE toppingID = ?");
            ps.setInt(1, toppingID);
            ps.setString(2, toppingName);
            ps.setInt(3, toppingPrice);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to update topping, please see server console for more info.\"}";
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
    public String deleteTopping(@FormDataParam("toppingID")Integer toppingID){
        //this allows you to delete a drink from the database
        try{
            if (toppingID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Topping/delete toppingID = " +  toppingID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Topping WHERE toppingID = ?");
            ps.setInt(1, toppingID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to delete topping, please see server console for more info.\"}";
        }
    }
}