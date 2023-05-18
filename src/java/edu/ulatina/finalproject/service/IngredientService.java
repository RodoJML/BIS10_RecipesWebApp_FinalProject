/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.service;

import edu.ulatina.finalproject.model.IngredientTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.*;

/**
 *
 * @author rodo
 */
public class IngredientService extends Service{
    
    public static final Logger logger = Logger.getLogger(IngredientService.class);
    RollingFileAppender fileAppend;
    
    public IngredientService() {

        this.fileAppend = new RollingFileAppender();
        this.fileAppend.setFile("log/InventarioIngredientes.log");
        this.fileAppend.setAppend(true);
        this.fileAppend.setMaxFileSize("5MB");
        this.fileAppend.setThreshold(Level.ALL);
        this.fileAppend.setLayout(new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss}] [ %-5p] [%c{1}:%L] %m%n"));
        this.fileAppend.activateOptions();
        Logger.getRootLogger().addAppender(fileAppend);

    }
    
    public void addIngredient(String name, int userID) {
        
        PreparedStatement pstmt = null;

        String querySQL = "INSERT INTO ingredients(name) VALUES (?)";
       
        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);
            
            pstmt.setString(1, name);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(pstmt);
            disconnect();
        }
        
        
        logger.info("Usuario: " + userID + " | " + "agrega ingrediente " + name);
    }
    
    
    public void deleteIngredient(int idIngredient, int userID){
        
        PreparedStatement pstmt = null;
        String querySQL = "DELETE FROM ingredients WHERE idingredient = ?";
        
        try {
            connect();
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setInt(1, idIngredient);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(pstmt);
            disconnect();
        }
        
        logger.info("Usuario: " + userID + " | " + "elimina ingrediente " + idIngredient);
    }
    
    public void deleteAllIngredients(int userID){
        
        PreparedStatement pstmt0 = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        
        String querySQL0 = "TRUNCATE TABLE recipeIngredients";
        String querySQL2 = "COMMIT";
        String querySQL1 = "DELETE FROM ingredients";

        
        try {
            connect();
            pstmt0 = connection.prepareStatement(querySQL0);
            pstmt0.executeUpdate();
            
            pstmt2 = connection.prepareStatement(querySQL2);
            pstmt2.executeUpdate();
            
            pstmt1 = connection.prepareStatement(querySQL1);
            pstmt1.executeUpdate();
            
            pstmt2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(pstmt0);
            closeStatement(pstmt1);
            closeStatement(pstmt2);
            disconnect();
        }
        
        logger.info("Usuario: " + userID + " | " + "elimina todos los ingredientes.");
        
    }
    
    
    public List<IngredientTO> list() {

        List<IngredientTO> listIngredients = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String querySQL = "SELECT idingredient, name FROM ingredients";

        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("idingredient");
                String name = rs.getString("name");

                IngredientTO ingredientTO = new IngredientTO(id, name);
                // Create an Object "UserTO" using the results from the DB

                listIngredients.add(ingredientTO);
                // Add each ingredient found into the array list each time the while loops
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
            // Step 4 - Close Connections
        }
        return listIngredients;
    }
    
    
    public List<String> listString() {

        List<String> listIngredients = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String querySQL = "SELECT idingredient, name FROM ingredients";

        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                listIngredients.add(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
            // Step 4 - Close Connections
        }
        return listIngredients;
    }
    
    
    public List<Integer> listIDs() {

        List<Integer> listIngredients = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String querySQL = "SELECT idingredient, name FROM ingredients";

        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                listIngredients.add(rs.getInt("idingredient"));
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
            // Step 4 - Close Connections
        }
        return listIngredients;
    }
    
    public String getIngredientName(int idIngredient){
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String ingredientName = ""; 

        String querySQL = "SELECT name "
                        + "FROM ingredients "
                        + "WHERE idingredient = ?";      
        
        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);
            pstmt.setInt(1, idIngredient);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ingredientName = rs.getString("name");
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
            // Step 4 - Close Connections
        }
        
        return ingredientName;
    }
  
    public int ingredientsCount(){
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        String querySQL = "SELECT COUNT(*) AS \"count\" FROM ingredients;";

        try {
            connect();
            pstmt = connection.prepareStatement(querySQL);

            rs = pstmt.executeQuery();
            
            while (rs.next()){
                count = rs.getInt("count");
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeStatement(pstmt);
            disconnect();

        }
        
        return count;
    }
   
}
