/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.ulatina.finalproject.service;

import edu.ulatina.finalproject.model.RecipeIngredientsTO;
import edu.ulatina.finalproject.model.RecipeTO;
import edu.ulatina.finalproject.model.StepsTO;
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
public class RecipeService extends Service {

    RecipeIngredientService recipeIngredientsService = new RecipeIngredientService();
    StepsService stepsService = new StepsService();
    public static final Logger logger = Logger.getLogger(IngredientService.class);
    RollingFileAppender fileAppend;
    

    public RecipeService() {
        
        this.fileAppend = new RollingFileAppender();
        this.fileAppend.setFile("log/RecetasGeneral.log");
        this.fileAppend.setAppend(true);
        this.fileAppend.setMaxFileSize("5MB");
        this.fileAppend.setThreshold(Level.ALL);
        this.fileAppend.setLayout(new PatternLayout("[%d{yyyy-MM-dd HH:mm:ss}] [ %-5p] [%c{1}:%L] %m%n"));
        this.fileAppend.activateOptions();
        Logger.getRootLogger().addAppender(fileAppend);
        
    }

    public void addRecipeInfo(int idUser, String name, int duration, String description) {
        PreparedStatement pstmt = null;

        String querySQL = "INSERT INTO recipes(id_user_FK, name, duration, description, status, image) "
                + "VALUES (?, ?, ?, ?, 1, 'default.png')";

        try {
            connect();
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setInt(1, idUser);
            pstmt.setString(2, name);
            pstmt.setInt(3, duration);
            pstmt.setString(4, description);
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeStatement(pstmt);
            disconnect();

        }
        
        logger.info("Usuario: " + idUser + " | " + "crea nueva receta " + name);
    }

    public List<RecipeTO> list() {

        List<RecipeTO> listRecipes = new ArrayList<RecipeTO>();

        PreparedStatement pstmt0 = null;
        ResultSet rs0 = null;

        String querySQL0 = "SELECT idrecipe, id_user_FK, name, duration, description, status, image "
                + "FROM recipes";

        try {
            connect();

            pstmt0 = connection.prepareStatement(querySQL0);
            rs0 = pstmt0.executeQuery();

            while (rs0.next()) {

                int idRecipe = rs0.getInt("idrecipe");
                int idUser = rs0.getInt("id_user_FK");
                String name = rs0.getString("name");
                int duration = rs0.getInt("duration");
                String description = rs0.getString("description");
                int status = rs0.getInt("status");
                String fileName = rs0.getString("image");

                List<RecipeIngredientsTO> listRecipeIngredients = new ArrayList<RecipeIngredientsTO>();
                List<StepsTO> listSteps = new ArrayList<StepsTO>();

                listRecipeIngredients = this.recipeIngredientsService.list(idRecipe);
                listSteps = this.stepsService.list(idRecipe);

                RecipeTO completeRecipe = new RecipeTO(idRecipe, idUser, name, duration, description, listRecipeIngredients, listSteps, status, fileName);
                listRecipes.add(completeRecipe);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs0);
            closeStatement(pstmt0);
            disconnect();
            // Step 4 - Close Connections
        }
        return listRecipes;
    }

    public List<RecipeTO> list_with_Status() {

        List<RecipeTO> listRecipes = new ArrayList<RecipeTO>();

        PreparedStatement pstmt0 = null;
        ResultSet rs0 = null;

        String querySQL0 = "SELECT idrecipe, id_user_FK, name, duration, description, status, image "
                + "FROM recipes "
                + "WHERE status = 1";

        try {
            connect();

            pstmt0 = connection.prepareStatement(querySQL0);
            rs0 = pstmt0.executeQuery();

            while (rs0.next()) {

                int idRecipe = rs0.getInt("idrecipe");
                int idUser = rs0.getInt("id_user_FK");
                String name = rs0.getString("name");
                int duration = rs0.getInt("duration");
                String description = rs0.getString("description");
                int status = rs0.getInt("status");
                String fileName = rs0.getString("image");

                List<RecipeIngredientsTO> listRecipeIngredients = new ArrayList<RecipeIngredientsTO>();
                List<StepsTO> listSteps = new ArrayList<StepsTO>();

                listRecipeIngredients = this.recipeIngredientsService.list(idRecipe);
                listSteps = this.stepsService.list(idRecipe);

                RecipeTO completeRecipe = new RecipeTO(idRecipe, idUser, name, duration, description, listRecipeIngredients, listSteps, status, fileName);
                listRecipes.add(completeRecipe);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs0);
            closeStatement(pstmt0);
            disconnect();
            // Step 4 - Close Connections
        }
        return listRecipes;
    }

    public RecipeTO pullSelectedRecipe(int recipeID) {

        PreparedStatement pstmt0 = null;
        ResultSet rs0 = null;
        RecipeTO recipe = null;

        String querySQL0 = "SELECT idrecipe, id_user_FK, name, duration, description, status, image "
                + "FROM recipes "
                + "WHERE idrecipe = ?";

        try {
            connect();

            pstmt0 = connection.prepareStatement(querySQL0);
            pstmt0.setInt(1, recipeID);
            rs0 = pstmt0.executeQuery();

            while (rs0.next()) {

                int idRecipe = rs0.getInt("idrecipe");
                int idUser = rs0.getInt("id_user_FK");
                String name = rs0.getString("name");
                int duration = rs0.getInt("duration");
                String description = rs0.getString("description");
                int status = rs0.getInt("status");
                String fileName = rs0.getString("image");

                List<RecipeIngredientsTO> listRecipeIngredients = new ArrayList<RecipeIngredientsTO>();
                List<StepsTO> listSteps = new ArrayList<StepsTO>();

                listRecipeIngredients = this.recipeIngredientsService.list(recipeID);
                listSteps = this.stepsService.list(recipeID);

                recipe = new RecipeTO(idRecipe, idUser, name, duration, description, listRecipeIngredients, listSteps, status, fileName);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs0);
            closeStatement(pstmt0);
            disconnect();
            // Step 4 - Close Connections
        }

        return recipe;
    }

    public int getRecentRecipeID() {

        PreparedStatement pstmt0 = null;
        ResultSet rs0 = null;
        int idRecipe = -1;

        String querySQL0 = "SELECT idrecipe FROM recipes ORDER BY idrecipe DESC LIMIT 1";

        try {
            connect();

            pstmt0 = connection.prepareStatement(querySQL0);
            rs0 = pstmt0.executeQuery();

            while (rs0.next()) {
                int idRecipe0 = rs0.getInt("idrecipe");
                idRecipe = idRecipe0;
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs0);
            closeStatement(pstmt0);
            disconnect();

        }

        return idRecipe;
    }

    public void updateRecipeInfo(int idRecipe, int idUser, String name, int duration, String description, int status) {

        PreparedStatement pstmt = null;

        String querySQL = "UPDATE recipes "
                + "SET name = ?, duration = ?, description = ?, status = ? "
                + "WHERE idrecipe = ? "
                + "AND id_user_FK = ?";

        try {
            connect();
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setString(1, name);
            pstmt.setInt(2, duration);
            pstmt.setString(3, description);
            pstmt.setInt(4, status);
            pstmt.setInt(5, idRecipe);
            pstmt.setInt(6, idUser);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeStatement(pstmt);
            disconnect();

        }
    }

    public List<RecipeTO> list_by_user(int idUser1) {

        List<RecipeTO> listRecipes = new ArrayList<RecipeTO>();

        PreparedStatement pstmt0 = null;
        ResultSet rs0 = null;

        String querySQL0 = "SELECT idrecipe, id_user_FK, name, duration, description, status, image "
                + "FROM recipes "
                + "WHERE id_user_FK = ?";

        try {
            connect();

            pstmt0 = connection.prepareStatement(querySQL0);
            pstmt0.setInt(1, idUser1);
            rs0 = pstmt0.executeQuery();

            while (rs0.next()) {

                int idRecipe = rs0.getInt("idrecipe");
                int idUser = rs0.getInt("id_user_FK");
                String name = rs0.getString("name");
                int duration = rs0.getInt("duration");
                String description = rs0.getString("description");
                int status = rs0.getInt("status");
                String fileName = rs0.getString("image");

                List<RecipeIngredientsTO> listRecipeIngredients = new ArrayList<RecipeIngredientsTO>();
                List<StepsTO> listSteps = new ArrayList<StepsTO>();

                listRecipeIngredients = this.recipeIngredientsService.list(idRecipe);
                listSteps = this.stepsService.list(idRecipe);

                RecipeTO completeRecipe = new RecipeTO(idRecipe, idUser, name, duration, description, listRecipeIngredients, listSteps, status, fileName);
                listRecipes.add(completeRecipe);
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeResultSet(rs0);
            closeStatement(pstmt0);
            disconnect();
            // Step 4 - Close Connections
        }
        return listRecipes;
    }

    public void updateStatus(int idRecipe, int idUser, int stat) {

        PreparedStatement pstmt = null;

        String querySQL = "UPDATE recipes "
                + "SET status = ? "
                + "WHERE idrecipe = ? "
                + "AND id_user_FK = ?";

        try {
            connect();
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setInt(1, stat);
            pstmt.setInt(2, idRecipe);
            pstmt.setInt(3, idUser);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeStatement(pstmt);
            disconnect();

        }
        
        logger.info("Usuario: " + idUser + " | " + "actualiza el estado de la recenta " + idRecipe + "a " + stat);

    }
    
    public int countRecipes(){
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;

        String querySQL = "SELECT COUNT(*) AS \"count\" FROM recipes WHERE status = 1;";

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
    
    
    public void addFileName(int recipeId, String fileName){
        
        PreparedStatement pstmt = null;

        String querySQL = "UPDATE recipes "
                + "SET image = ? "
                + "WHERE idrecipe = ?";

        try {
            connect();
            pstmt = connection.prepareStatement(querySQL);
            pstmt.setString(1, fileName);
            pstmt.setInt(2, recipeId);
            
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            closeStatement(pstmt);
            disconnect();

        }
        
        logger.info("Se actualiza la imagen de la receta " + recipeId + "con " + fileName);
        
    }

    
}
