package Controllers;
import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("Side/")

public class sidesController {

    /*
    The API request handler for /Pizza/list
        FormDataParams: none
        Cookies: none
 */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public String listSides() {
        System.out.println("Side/list");
        JSONArray list = new JSONArray();
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT sideID, sideName, sideSize, sidePrice FROM Sides");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("sideID", results.getInt(1));
                item.put("sideName", results.getString(2));
                item.put("sideSize", results.getObject(3));
                item.put("sidePrice", results.getString(4));
                list.add(item);

                //System.out.println(sideID + " " + sideName + " " + sideSize + " " + sidePrice);
            }
            return list.toString();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list sides, please see server console for more info.\"}";
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

    public String insertSides(@FormDataParam("sideName")String sideName,
                              @FormDataParam("sideSize")Character sideSize,
                              @FormDataParam("sidePrice")Integer sidePrice) {
        //this block of code allows you to insert a drink into the database
        try {
            if(sideName == null ||
                    sideSize == null ||
                    sidePrice == null){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Side/create sideName = " + sideName);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Sides (sideName, sideSize, sidePrice) VALUES ( ?, ?, ?)");

            ps.setString(1, sideName);
            ps.setObject(2, sideSize, java.sql.Types.CHAR);
            ps.setInt(3, sidePrice);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new side, please see server console for more info.\"}";
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

    public String updateSides(@FormDataParam("sideID")Integer sideID,
                              @FormDataParam("sideName") String sideName,
                              @FormDataParam("sideSize")Character sideSize,
                              @FormDataParam("sidePrice") Integer sidePrice){
        //this allows you to update a drink in the database
        try{
            if (sideID == null || sideName == null || sideSize == null || sidePrice == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Side/update sideID = " + sideID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Sides SET sideName = ?, sideSize = ? , sidePrice = ? WHERE sideID = ?");
            ps.setInt(1, sideID);
            ps.setString(2, sideName);
            ps.setObject(3, sideSize, java.sql.Types.CHAR);
            ps.setInt(4, sidePrice);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to update side, please see server console for more info.\"}";
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
    public String deleteSides(@FormDataParam("restaurantID")Integer sideID){
        //this allows you to delete a drink from the database
        try{
            if (sideID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Side/delete sideID = " +  sideID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Sides WHERE sideID = ?");
            ps.setInt(1, sideID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to delete side, please see server console for more info.\"}";
        }
    }
}