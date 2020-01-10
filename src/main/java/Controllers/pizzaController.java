package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

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

            PreparedStatement ps = Main.db.prepareStatement("SELECT pizzaID, pizzaName, pizzaImage, pizzaSize, pizzaBase, pizzaCrust, pizzaPrice FROM Pizzas");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("pizzaID", results.getInt(1));
                item.put("pizzaName", results.getString(2));
                item.put("pizzaImage", results.getString(3));
                item.put("pizzaSize", results.getObject(4));
                item.put("pizzaBase", results.getString(5));
                item.put("pizzaCrust", results.getString(6));
                item.put("pizzaPrice", results.getInt(7));
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
                               @FormDataParam("pizzaName")String pizzaBase,
                               @FormDataParam("pizzaBase")String pizzaCrust,
                               @FormDataParam("pizzaPrice")Integer pizzaPrice,
                               @FormDataParam("pizzaSet")boolean pizzaSet) {
        //this block of code allows you to insert a pizza into the database
        try {
            if(pizzaName == null ||
                    pizzaSize == null ||
                    pizzaBase == null ||
                    pizzaCrust == null ||
                    pizzaPrice == null ){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Pizza/create pizzaName = " + pizzaName);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Pizzas (pizzaName, pizzaSize, pizzaBase, pizzaCrust, pizzaPrice, pizzaSet) VALUES ( ?, ?, ?, ?, ?)");

            ps.setString(1, pizzaName);
            ps.setObject(2, pizzaSize, java.sql.Types.CHAR);
            ps.setString(3, pizzaBase);
            ps.setString(4, pizzaCrust);
            ps.setInt(5, pizzaPrice);


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

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)


    public String updatePizza( @FormDataParam("pizzaID")Integer pizzaID,
                               @FormDataParam("pizzaName")String pizzaName,
                               @FormDataParam("pizzaSize")Character pizzaSize,
                               @FormDataParam("pizzaBase")String pizzaBase,
                               @FormDataParam("pizzaCrust")String pizzaCrust,
                               @FormDataParam("pizzaPrice")Integer pizzaPrice,
                               @FormDataParam("pizzaImage")String pizzaImage,
                               @FormDataParam("pizzaSet")boolean pizzaSet) {
        //this allows you to update a pizza in the database
        try{
            if(pizzaID == null || pizzaName == null || pizzaSize == null || pizzaBase == null || pizzaCrust == null ||pizzaPrice == null || pizzaImage == null){
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Pizza/update pizzaID = "+ pizzaID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE Pizzas SET pizzaName = ?, pizzaSize = ?, pizzaBase = ?, pizzaCrust = ?, pizzaPrice = ?, pizzaSet = true, pizzaImage = ? WHERE pizzaID = ?");
            ps.setString(1, pizzaName);
            ps.setObject(2, pizzaSize, java.sql.Types.CHAR);
            ps.setString(3, pizzaBase);
            ps.setString(4, pizzaCrust);
            ps.setInt(5, pizzaPrice);
            ps.setString(6, pizzaImage);
            ps.setInt(7, pizzaID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to update pizza, please see server console for more info.\"}";
        }
    }

    /*
   The API request handler for /Pizza/update
       FormDataParams: none
       Cookies: none
*/
    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    public String deleteCustomer(@FormDataParam("pizzaID")Integer pizzaID){
        //this allows you to delete a pizza from the database
        try{
            if (pizzaID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Pizza/delete pizzaID = " +  pizzaID);
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Pizzas WHERE pizzaID = ?");
            ps.setInt(1, pizzaID);
            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "{\"error\": \"Unable to delete pizza, please see server console for more info.\"}";
        }
    }

    /*
   The API request handler for /Pizza/update
       FormDataParams: none
       Cookies: none
*/

    @POST
    @Path("custom")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public  String customPizza(@FormDataParam("pizzaName")String pizzaName,
                               @FormDataParam("pizzaSize")Character pizzaSize,
                               @FormDataParam("pizzaName")String pizzaBase,
                               @FormDataParam("pizzaBase")String pizzaCrust,
                               @FormDataParam("pizzaPrice")Integer pizzaPrice,
                               @FormDataParam("toppingID")Integer toppingID,
                               @FormDataParam("toppingName")String toppingName,
                               @FormDataParam("toppingPrice")Integer toppingPrice) {
        //this block of code allows you to insert a pizza into the database
        try {

            Scanner input = new Scanner(System.in);

            System.out.println("What size pizza would you like? S ,M or L");
            String Size = input.nextLine();
            int Price = 0;
            if(Size.equals("S")) Price = 1099;
            else if(Size.equals("M")) Price = 1399;
            else if(Size.equals("L")) Price = 1799;

            System.out.println("What crust pizza would you like? Plain, Sausage or Cheese");
            String Crust = input.nextLine();
            if(Crust.equals("Sausage")) Price = Price + 199;
            else if(Crust.equals("Cheese")) Price = Price + 99;

            toppingController.listTopping();
            System.out.println("Which topping would you like?");




            pizzaPrice = Price;

            if(pizzaName == null ||
                    pizzaSize == null ||
                    pizzaBase == null ||
                    pizzaCrust == null ||
                    toppingID == null ||
                    toppingName == null ||
                    toppingPrice == null){
                throw new Exception(" One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("Pizza/custom pizzaName = " + pizzaName);
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Pizzas (pizzaName, pizzaSize, pizzaBase, pizzaCrust, pizzaPrice) VALUES ( ?, ?, ?, ?, ?)");

            ps.setString(1, pizzaName);
            ps.setObject(2, pizzaSize, java.sql.Types.CHAR);
            ps.setString(3, pizzaBase);
            ps.setString(4, pizzaCrust);
            ps.setInt(5, pizzaPrice);




            ps.executeUpdate();
            return "{\"status\": \"OK\"}";




        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new pizza, please see server console for more info.\"}";
        }
    }

    @GET
    @Path("get/{pizzaID}")
    @Produces({"application/json"})
    public String getFruit(@PathParam("pizzaID") Integer pizzaID) {
        System.out.println("pizza/get/" + pizzaID);
        JSONObject item = new JSONObject();

        try {
            if (pizzaID == null) {
                throw new Exception("Pizza's 'id' is missing in the HTTP request's URL.");
            } else {
                PreparedStatement ps = Main.db.prepareStatement("SELECT pizzaID, pizzaName, pizzaSize, pizzaBase, pizzaCrust, pizzaPrice, pizzaImage FROM Pizzas WHERE pizzaID = ?");
                ps.setInt(1, pizzaID);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    item.put("pizzaID", results.getInt(1));
                    item.put("pizzaName", results.getString(2));
                    item.put("pizzaSize", results.getObject(3));
                    item.put("pizzaBase", results.getObject(4));
                    item.put("pizzaCrust", results.getObject(5));
                    item.put("pizzaPrice", results.getInt(6));
                    item.put("pizzaImage", results.getString(7));
                }

                return item.toString();
            }
        } catch (Exception var5) {
            System.out.println("Database error: " + var5.getMessage());
            return "{\"error\": \"Unable to get pizza, please see server console for more info.\"}";
        }
    }
}

