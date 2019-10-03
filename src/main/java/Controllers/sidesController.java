package Controllers;
import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class sidesController {
    public static void listSides(int sideID, String sideName,char sideSize, int sidePrice) {
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT sideID, sideName, sideSize, sidePrice FROM Sides");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                sideID = results.getInt(1);
                sideName = results.getString(2);
                sideSize = (char) results.getObject(3);
                sidePrice = results.getInt(4);

                System.out.println(sideID + " " + sideName + " " + sideSize + " " + sidePrice);
            }
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertSides(String sideName, char sideSize, int sidePrice) {
        //this block of code allows you to insert a drink into the database
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Sides (sideName, sideSize, sidePrice) VALUES ( ?, ?, ?)");

            ps.setString(1, sideName);
            ps.setObject(2, sideSize, java.sql.Types.CHAR);
            ps.setInt(3, sidePrice);

            ps.executeUpdate();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
        }
    }

    public static void updateSides(Integer sideID, String sideName, char sideSize, Integer sidePrice){
        //this allows you to update a drink in the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Sides SET sideName = ?, sideSize = ? , sidePrice = ? WHERE sideID = ?");
            ps.setInt(1, sideID);
            ps.setString(2, sideName);
            ps.setObject(3, sideSize, java.sql.Types.CHAR);
            ps.setInt(4, sidePrice);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteSides(int sideID){
        //this allows you to delete a drink from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Sides WHERE sideID = ?");
            ps.setInt(1, sideID);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
