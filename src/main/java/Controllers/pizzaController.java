package Controllers;
import Server.Main;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Path("Pizza/")

public class pizzaController {



    /*
    The API request handler for /Pizza/list
        FormDataParams: none
        Cookies: none
 */

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listPizza() {
        //this block of code allows you to read the database so that the data can be used
        System.out.println("Pizza/list");
        JSONArray list = new JSONArray();
        try {

            PreparedStatement ps = Main.db.prepareStatement("SELECT pizzaID, pizzaName, pizzaTopping, pizzaBase, pizzaCrust FROM Pizzas");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("pizzaID", results.getInt(1));
                item.put("pizzaName", results.getString(2));
                item.put("pizzaTopping", results.getString(3));
                item.put("pizzaBase", results.getString(4));
                item.put("pizzaCrust", results.getString(5));
                list.add(item);
                //System.out.println(pizzaID + " " + pizzaName + " " + pizzaTopping + " " + pizzaBase + " " + pizzaCrust);
            }
            return list.toString();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
            return "{\"error\": \"Unable to list pizzas, please see server console for more info.\"}";
        }
    }

    /*
   The API request handler for /Pizza/add
       FormDataParams: none
       Cookies: none
*/

    @POST
    @Path("create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public  String insertPizza(@FormDataParam("pizzaName")String pizzaName,
                               @FormDataParam("pizzaSize")Character pizzaSize,
                               @FormDataParam("pizzaTopping")String pizzaTopping,
                               @FormDataParam("pizzaName")String pizzaBase,
                               @FormDataParam("pizzaBase")String pizzaCrust,
                               @FormDataParam("pizzaPrice")Integer pizzaPrice) {
        //this block of code allows you to insert a pizza into the database
        try {
            if(pizzaName == null ||
                    pizzaSize == null ||
                    pizzaTopping == null ||
                    pizzaBase == null ||
                    pizzaCrust == null ||
                    pizzaPrice == null ){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Pizza/create pizzaName = " + pizzaName);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Pizzas (pizzaName, pizzaSize, pizzaTopping, pizzaBase, pizzaCrust, pizzaPrice) VALUES ( ?, ?, ?, ?, ?, ?)");

            ps.setString(1, pizzaName);
            ps.setObject(2, pizzaSize, java.sql.Types.CHAR);
            ps.setString(3, pizzaTopping);
            ps.setString(4, pizzaBase);
            ps.setString(5, pizzaCrust);
            ps.setInt(6, pizzaPrice);


            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new pizza, please see server console for more info.\"}";
        }
    }

/*
   The API request handler for /Pizza/update
       FormDataParams: none
       Cookies: none
*/

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
