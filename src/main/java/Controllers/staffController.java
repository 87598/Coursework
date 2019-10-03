package Controllers;
import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class staffController {
    public static void listStaff(int staffID, String staffFirst,  String staffLast, String staffPass) {
        //this block of code allows you to read the database so that the data can be used
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT staffID, staffFirst, staffLast, staffPass FROM Staff");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                staffID = results.getInt(1);
                staffFirst = results.getString(2);
                staffLast = results.getString(3);
                staffPass = results.getString(4);
                System.out.println(staffID + " " + staffFirst + " " + staffLast + " " + staffPass);
            }
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error: " + exception.getMessage());
        }
    }

    public static void insertStaff(String staffFirst, String staffLast, String staffPass) {
        //this block of code allows you to insert a staff member into the database
        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT INTO Staff (staffFirst, staffLast, staffPass) VALUES ( ?, ?, ?)");

            ps.setString(1, staffFirst);
            ps.setString(2, staffLast);
            ps.setString(3, staffPass);

            ps.executeUpdate();
        } catch (Exception exception) { //this will catch the errors
            System.out.println("Database error" + exception.getMessage());
        }
    }

    public static void updateStaff(String staffFirst, String staffLast, String staffPass, int staffID){
        //this allows you to update a staff member from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("UPDATE Staff SET staffFirst = ?, staffLast = ?, staffPass = ? WHERE staffID = ?");
            ps.setInt(1, staffID);
            ps.setString(2, staffFirst);
            ps.setString(3, staffLast);
            ps.setString(4, staffPass);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void deleteStaff(int staffID){
        //this allows you to delete a staff member from the database
        try{
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM Staff WHERE staffID = ?");
            ps.setInt(1, staffID);
            ps.executeUpdate();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

