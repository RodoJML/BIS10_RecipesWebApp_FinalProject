/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.service;

import edu.ulatina.finalproject.model.RecipeIngredientsTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 *
 * @author rodo
 */
public class RecipeIngredientService extends Service {
    
    public static final Logger logger = Logger.getLogger(IngredientService.class);
    RollingFileAppender fileAppend;

    public RecipeIngredientService() {
        
        this.fileAppend = new RollingFileAppender();
        this.fileAppend.setFile("log/Ingredientes_de_Recetas.log");
        this.fileAppend.setAppend(true);
        this.fileAppend.setMaxFileSize("5MB");
        this.fileAppend.setThreshold(Level.ALL);
        this.fileAppend.setLayout(new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss}] [ %-5p] [%c{1}:%L] %m%n"));
        this.fileAppend.activateOptions();
        Logger.getRootLogger().addAppender(fileAppend);
    }

    public List<RecipeIngredientsTO> list(int recipeID) {

        List<RecipeIngredientsTO> listRecipeIngredients = new ArrayList<RecipeIngredientsTO>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String querySQL = "SELECT id_recipess_FK, id_userss_FK, id_ingredientss_FK, quantity "
                + "FROM recipeIngredients "
                + "WHERE id_recipess_FK = ?";

        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);
            pstmt.setInt(1, recipeID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int idRecipeFK = rs.getInt("id_recipess_FK");
                int idUserFK = rs.getInt("id_userss_FK");
                int idIngredientFK = rs.getInt("id_ingredientss_FK");
                int quantity = rs.getInt("quantity");

                RecipeIngredientsTO recipeWithIngredients = new RecipeIngredientsTO(idRecipeFK, idUserFK, idIngredientFK, quantity);

                listRecipeIngredients.add(recipeWithIngredients);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
        }
        return listRecipeIngredients;
    }

    public void addIngredientsRecipe(int idRecipe, int idUser, int idIngredient, int quantity) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String querySQL = "INSERT INTO recipeIngredients(id_recipess_FK, id_userss_FK, id_ingredientss_FK, quantity) "
                + "VALUES (?,?,?,?)";

        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);

            pstmt.setInt(1, idRecipe);
            pstmt.setInt(2, idUser);
            pstmt.setInt(3, idIngredient);
            pstmt.setInt(4, quantity);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
        }
        
        logger.info("Usuario: " + idUser + " | " + "agrega ingrediente " + idIngredient + " a receta " + idRecipe);
    }

    public void updateIngredientsRecipe(int idRecipe, int idUser, int idIngredient, int quantity) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String querySQL = "UPDATE recipeIngredients "
                        + "SET quantity = ? "
                        + "WHERE id_recipess_FK = ? "
                        + "AND id_userss_FK = ? "
                        + "AND id_ingredientss_FK = ?";

        try {
            connect();

            pstmt = connection.prepareStatement(querySQL);

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, idRecipe);
            pstmt.setInt(3, idUser);
            pstmt.setInt(4, idIngredient);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs);
            closeStatement(pstmt);
            disconnect();
        }
        
        logger.info("Usuario: " + idUser + " | " + "actualiza ingrediente " + idIngredient + " a receta " + idRecipe + " con cantidad de " + quantity);
    }
    
    
    public void clearIngredients(int idRecipe){
        
        PreparedStatement pstmt1 = null;

        String querySQL1 = "DELETE FROM recipeIngredients "
                         + "WHERE id_recipess_FK = ?";

        try {
            connect();
            pstmt1 = connection.prepareStatement(querySQL1);
            pstmt1.setInt(1, idRecipe);
            pstmt1.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(pstmt1);
            disconnect();
        }
        
        logger.info("Usuario actualiza todos los ingredientes de la receta" + idRecipe);
        
    }
    
    public void clearSingleIngredient(int idIngredient){
        
        PreparedStatement pstmt1 = null;

        String querySQL1 = "DELETE FROM recipeIngredients "
                         + "WHERE id_ingredientss_FK = ?";

        try {
            connect();
            pstmt1 = connection.prepareStatement(querySQL1);
            pstmt1.setInt(1, idIngredient);
            pstmt1.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStatement(pstmt1);
            disconnect();
        }
        
        logger.info("Usuario: actualiza ingrediente " + idIngredient + " a receta ");
        
    }
}


