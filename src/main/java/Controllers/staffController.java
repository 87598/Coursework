package Controllers;
import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Path("Staff/")

public class staffController {

    /*
    The API request handler for /Pizza/list
        FormDataParams: none
        Cookies: none
 */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    public String listStaff() {
        System.out.println("Staff/list");
        JSONArray list = new JSONArray();
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT staffID, staffFirst, staffLast, staffPass FROM Staff");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("staffID", results.getInt(1));
                item.put("staffFirst", results.getString(2));
                item.put("staffLast", results.getString(3));
                item.put("staffPass", results.getString(4));
                list.add(item);

                //System.out.println(staffID + " " + staffFirst + " " + staffLast + " " + staffPass);
            }
            return list.toString();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list staff, please see server console for more info.\"}";
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

    public String insertStaff(@FormDataParam("staffFirst")String staffFirst,
                              @FormDataParam("staffLast")String staffLast,
                              @FormDataParam("staffPass")String staffPass) {
        //this block of code allows you to insert a staff member into the database
        try {
            if(staffFirst == null ||
                    staffLast == null ||
                    staffPass == null){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Staff/create staffFirst = " + staffFirst);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Staff (staffFirst, staffLast, staffPass) VALUES ( ?, ?, ?)");

            ps.setString(1, staffFirst);
            ps.setString(2, staffLast);
            ps.setString(3, staffPass);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new staff, please see server console for more info.\"}";
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

    public String updateStaff(@FormDataParam("staffFirst")String staffFirst,
                              @FormDataParam("staffLast")String staffLast,
                              @FormDataParam("staffPass")String staffPass,
                              @FormDataParam("staffID")Integer staffID){
        //this allows you to update a staff member from the database
        try{
            if (staffFirst == null || staffLast == null || staffPass == null || staffID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Staff/update staffID = " + staffID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Staff SET staffFirst = ?, staffLast = ?, staffPass = ? WHERE staffID = ?");
            ps.setInt(1, staffID);
            ps.setString(2, staffFirst);
            ps.setString(3, staffLast);
            ps.setString(4, staffPass);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to update staff, please see server console for more info.\"}";
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

    public String deleteStaff(@FormDataParam("restaurantID")Integer staffID){
        //this allows you to delete a staff member from the database
        try{
            if (staffID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Staff/delete staffID = " +  staffID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Staff WHERE staffID = ?");
            ps.setInt(1, staffID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to delete staff, please see server console for more info.\"}";
        }
    }


/*
    The API request handler for /Customer/delete
        FormDataParams: none
        Cookies: need to be the user's (user that's updating the account) token or an admin
 */

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginCustomer(@FormDataParam("staffUser") String staffUser,
                                @FormDataParam("staffPass") String staffPass
    ) {

        try {

            if (staffUser == null ||
                    staffPass == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            System.out.println("Customer/login customerUser = " + staffUser);

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT staffPass FROM Staff WHERE staffUser = ?");
            ps1.setString(1, staffUser);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {

                String correctPassword = loginResults.getString(1);

                if (staffPass.equals(correctPassword)) {

                    String token = UUID.randomUUID().toString();

                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Staff SET token = ? WHERE staffUser = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, staffUser);
                    ps2.executeUpdate();

                    JSONObject response = new JSONObject();
                    response.put("staffUser", staffUser);
                    response.put("token", token);
                    return response.toString();

                } else {
                    return "{\"error\": \"Incorrect password!\"}";
                }

            } else {
                return "{\"error\": \"Unknown staff member!\"}";
            }

        } catch (Exception exception){
            System.out.println("Database error during /Staff/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }

    /*
    The API request handler for /Customer/logout
        FormDataParams: none
        Cookies: need to be the user's (user that's updating the account) token or an admin
 */

    @POST
    @Path("logout")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String logoutUser(@CookieParam("token") String token) {

        try {

            System.out.println("Staff/logout");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT staffID FROM Staff WHERE token = ?");
            ps1.setString(1, token);
            ResultSet logoutResults = ps1.executeQuery();
            if (logoutResults.next()) {

                int id = logoutResults.getInt(1);

                PreparedStatement ps2 = Main.db.prepareStatement("UPDATE Staff SET token = NULL WHERE staffUser = ?");
                ps2.setInt(1, id);
                ps2.executeUpdate();

                return "{\"status\": \"OK\"}";
            } else {

                return "{\"error\": \"Invalid token!\"}";

            }

        } catch (Exception exception){
            System.out.println("Database error during /Staff/logout: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }

    }

}
